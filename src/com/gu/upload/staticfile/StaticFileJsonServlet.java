package com.gu.upload.staticfile;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.repackaged.org.apache.commons.codec.binary.Base64;
import com.gu.upload.model.JsonResponse;
import com.gu.upload.model.StaticFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class StaticFileJsonServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String path = request.getPathInfo();
        List<StaticFile> staticFiles = new ArrayList<StaticFile>();

        if(path.endsWith("/all")) {
            staticFiles.addAll(StaticFile.all().order("name").fetch());
        } else {
            StaticFile file = StaticFile.all().filter("path", removeLeadingSlash(path)).get();
            if (file != null) {
                staticFiles.add(file);
            }
        }

		// no caching of json

        for (StaticFile file : staticFiles) {
            byte[] fileData = BlobstoreServiceFactory
                    .getBlobstoreService()
                    .fetchData(new BlobKey(file.getBlobKey()), 0, BlobstoreService.MAX_BLOB_FETCH_SIZE - 1);
            String base64fileData = Base64.encodeBase64URLSafeString(fileData); // UTF-8, JSON-safe
            file.setData(base64fileData);
        }

        response.setContentType("application/json");
        response.setStatus(200);
        PrintWriter out = response.getWriter();
        out.print(new JsonResponse(staticFiles).toJson());

	}

	private String removeLeadingSlash(String path) {
		if (path != null && path.length() > 1 && path.startsWith("/")) {
			path = path.substring(1);
		}
		return path;
	}

}
