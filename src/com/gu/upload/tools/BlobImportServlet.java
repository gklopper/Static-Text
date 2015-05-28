package com.gu.upload.tools;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.gu.upload.model.StaticFile;
import com.gu.upload.tools.util.HttpRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlobImportServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)  throws IOException, ServletException {
        String path = request.getPathInfo();
        BlobstoreService bs = BlobstoreServiceFactory.getBlobstoreService();
        List<StaticFile> files = new ArrayList<StaticFile>();

        if(path.equals("/all")) {
            files.addAll(StaticFile.all().order("name").fetch());
        } else {
            StaticFile file = StaticFile.findByPath(path.substring(1));
            if (file != null) {
                files.add(file);
            }
        }

        for (StaticFile file : files) {
            log("importing " + file.getPath());
            try {
                String url = "http://static-host.appspot.com/staticfile/json/" + file.getPath();
                String json = HttpRequest.get(url).contentType("application/json").body();
                // TODO: convert JSON to blob
                log("Response was: " + json);
                // TODO: write blob data
                // TODO: update meta
                //file.insertOrUpdate();
            } catch(Exception ex) {
                log("Failed to import " + file.getPath() + ": " + ex.getMessage(), ex.getCause());
            }
        }
    }

}
