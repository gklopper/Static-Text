package com.gu.upload.tools;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;
import com.google.appengine.repackaged.org.apache.commons.codec.binary.Base64;
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
import com.google.gson.Gson;

public class BlobImportServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)  throws IOException, ServletException {
        // Get a file service
        FileService fileService = FileServiceFactory.getFileService();

        Gson gson = new Gson();
        String path = request.getPathInfo();
        BlobstoreService bs = BlobstoreServiceFactory.getBlobstoreService();
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
                        log("Processing remote file: " + remoteFile.getPath());
                        // Create a new Blob file with expected mime-type
                        log("  createNewBlobFile");
                        AppEngineFile newLocalFile = fileService.createNewBlobFile(localFile.getType());
                        // Open a channel to write to it
                        boolean lock = true;
                        log("  openWriteChannel");
                        FileWriteChannel writeChannel = fileService.openWriteChannel(newLocalFile, lock);
                        // Write the blob data to the channel directly
                        log("  writeChannel.write");
                        writeChannel.write(ByteBuffer.wrap(Base64.decodeBase64(remoteFile.getData().getBytes())));
                        // And finalize
                        log("  writeChannel.closeFinally");
                        writeChannel.closeFinally();
                        // Get the new file's BlobKey
                        log("  fileService.getBlobKey");
                        BlobKey blobKey = fileService.getBlobKey(newLocalFile);
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
