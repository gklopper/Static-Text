package com.gu.upload.tools;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.gu.upload.model.StaticFile;

@SuppressWarnings("serial")
public class StaticFileListServlet extends HttpServlet{
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		List<StaticFile> files = StaticFile.all().order("name").fetch();
		request.setAttribute("files", files);
		getServletContext().getRequestDispatcher("/WEB-INF/views/tools/staticfilelist.jsp").forward(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long id = Long.parseLong(request.getParameter("fileToDelete"));
		
		StaticFile file = StaticFile.findById(id);
		
		BlobKey blobKey = new BlobKey(file.getBlobKey());
		BlobstoreServiceFactory.getBlobstoreService().delete(blobKey);
		file.delete();
		response.sendRedirect("/admin/staticfile/list");
	}

}
