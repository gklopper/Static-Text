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
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.nio.channels.Channels;
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
                log("Response was: " + json);

                Type responseType = new TypeToken<JsonResponse>(){}.getType();
                JsonResponse jsonResponse = gson.fromJson(json, responseType);
                List<StaticFile> remoteFiles = jsonResponse.getFiles();

                for (StaticFile remoteFile : remoteFiles) {
                    log("Checking remote file: " + remoteFile.getPath());
                    if (remoteFile.getPath().equals(localFile.getPath())) {
                        GcsFilename gcsFileName = new GcsFilename(BUCKETNAME, localFile.getPath());
                        log("Processing file: " + localFile.getPath());
                        log("  gcsService.createOrReplace");
                        GcsFileOptions gcsFileOptions = new GcsFileOptions.Builder().mimeType(localFile.getType()).build();
                        GcsOutputChannel outputChannel = gcsService.createOrReplace(gcsFileName, gcsFileOptions);
                        log("  create ObjectOutputStream");
                        @SuppressWarnings("resource")
                        ObjectOutputStream oout = new ObjectOutputStream(Channels.newOutputStream(outputChannel));
                        // Write the blob data to the output stream directly
                        log("  decode");
                        String fileContentBase64Enc = remoteFile.getData();
                        byte[] fileContentDecoded = Base64.decodeBase64(fileContentBase64Enc);
                        if (!Base64.encodeBase64URLSafeString(fileContentDecoded).equals(fileContentBase64Enc)) {
                            log("  !! decoding error !!\n"
                                    + new String(fileContentDecoded) + "\n"
                                    + " was not re-encoded to\n"
                                    + fileContentBase64Enc);
                        } else {
                            log ("  decoding successful");
                        }

                        log("  write");
                        oout.write(fileContentDecoded);
                        // And finalize
                        log("  close");
                        oout.close();
                        // Get the new file's BlobKey
                        log("  blobstoreService.createGsBlobKey");
                        BlobKey blobKey = blobstoreService.createGsBlobKey("/gs/" +
                                gcsFileName.getBucketName() + "/" +
                                gcsFileName.getObjectName());
                        // Update the local StaticFile's blobkey
                        log(" localFile.setBlobKey");
                        localFile.setBlobKey(blobKey.getKeyString());
                        log("  localFile.update");
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
