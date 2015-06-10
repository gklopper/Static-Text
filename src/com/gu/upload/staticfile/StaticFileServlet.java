package com.gu.upload.staticfile;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.gu.upload.model.StaticFile;

@SuppressWarnings("serial")
public class StaticFileServlet extends HttpServlet {
    public static final String BUCKETNAME = "static-host-hrd.appspot.com";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String path = request.getPathInfo();

		path = removeLeadingSlash(path);

		StaticFile staticFile = StaticFile.all().filter("path", path).get();

		if (staticFile == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

		response.setContentType(staticFile.getType());

		// cache for a day
		response.setHeader("Cache-Control", "public, max-age=86400");

        BlobKey blobKey = blobstoreService.createGsBlobKey("/gs/" + BUCKETNAME + "/" + path);

        blobstoreService.serve(blobKey, response);
	}

	private String removeLeadingSlash(String path) {
		if (path != null && path.length() > 1 && path.startsWith("/")) {
			path = path.substring(1);
		}
		return path;
	}

}
