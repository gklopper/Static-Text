package com.gu.upload.tools;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.tools.cloudstorage.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gu.upload.model.JsonResponse;
import com.gu.upload.model.StaticFile;
import com.gu.upload.tools.util.HttpRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class BlobImportServlet extends HttpServlet {

    public static final String BUCKETNAME = "static-host-hrd.appspot.com";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)  throws IOException, ServletException {
        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
        GcsService gcsService = GcsServiceFactory.createGcsService(RetryParams.getDefaultInstance());

        Gson gson = new Gson();
        String path = request.getPathInfo();
        List<StaticFile> localFiles = new ArrayList<StaticFile>();

        if(path.equals("/all")) {
            localFiles.addAll(StaticFile.all().order("name").fetch());
        } else {
            StaticFile file = StaticFile.findByPath(path.substring(1));
            if (file != null) {
                localFiles.add(file);
            }
        }

        for (StaticFile localFile : localFiles) {
            log("importing " + localFile.getPath());
            try {
                String url = "http://static-host.appspot.com/staticfile/json/" + localFile.getPath();
                String json = HttpRequest.get(url).contentType("application/json").body();

                Type responseType = new TypeToken<JsonResponse>(){}.getType();
                JsonResponse jsonResponse = gson.fromJson(json, responseType);
                List<StaticFile> remoteFiles = jsonResponse.getFiles();

                for (StaticFile remoteFile : remoteFiles) {
                    log("Checking remote file: " + remoteFile.getPath());
                    if (remoteFile.getPath().equals(localFile.getPath())) {
                        GcsFilename gcsFileName = new GcsFilename(BUCKETNAME, localFile.getPath());
                        log("Processing file: " + localFile.getPath());
                        // create a GCS output channel
                        GcsFileOptions gcsFileOptions = new GcsFileOptions.Builder().mimeType(localFile.getType()).build();
                        GcsOutputChannel outputChannel = gcsService.createOrReplace(gcsFileName, gcsFileOptions);
                        // decode
                        String fileContentBase64UrlSafeEnc = remoteFile.getData();
                        byte[] fileContentDecoded = Base64.decodeBase64(fileContentBase64UrlSafeEnc);
                        // check decoded
                        if (!Base64.encodeBase64URLSafeString(fileContentDecoded).equals(fileContentBase64UrlSafeEnc)) {
                            log("  !! decode error !!\n"
                                    + " decoded input would not re-encode to\n"
                                    + remoteFile.getData());
                        }
                        outputChannel.write(ByteBuffer.wrap(fileContentDecoded));
                        outputChannel.close();
                        // Update the local StaticFile's blobkey
                        BlobKey blobKey = blobstoreService.createGsBlobKey("/gs/" +
                                gcsFileName.getBucketName() + "/" +
                                gcsFileName.getObjectName());
                        log("  new blobKey is " + blobKey.getKeyString());
                        localFile.setBlobKey(blobKey.getKeyString());
                        localFile.update();
                        log("Done");
                    }
                }

            } catch(Exception ex) {
                log("Failed to import " + localFile.getPath() + ": " + ex.getMessage(), ex.getCause());
            }
        }
    }

}
