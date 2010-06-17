package com.gu.upload.tools;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.gu.upload.model.StaticFile;

@SuppressWarnings("serial")
public class StaticFileCreateAndEditServlet extends BaseCreateAndEditServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uploadUrl = BlobstoreServiceFactory.getBlobstoreService().createUploadUrl("/admin/staticfile/upload");
		request.setAttribute("uploadUrl", uploadUrl);
		StaticFile file = new StaticFile();
		file.setOwner(getUserEmail());
		request.setAttribute("file", file);
		getServletContext().getRequestDispatcher("/WEB-INF/views/tools/staticfile.jsp").forward(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		BlobKey blobKey = BlobstoreServiceFactory.getBlobstoreService().getUploadedBlobs(request).get("uploadedFile");
		StaticFile file = new StaticFile();
		file.setBlobKey(blobKey.getKeyString());
		file.setName(request.getParameter("name"));
		file.setPath(request.getParameter("path"));
		file.setType(request.getParameter("type"));
		file.setOwner(getUserEmail());
		file.insert();
		response.sendRedirect("/admin/staticfile/list");
	}
}
