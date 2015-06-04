package com.gu.upload.tools;

import static com.gu.upload.tools.util.Validation.anyCommonFieldIsEmpty;
import static com.gu.upload.tools.util.Validation.empty;
import static com.gu.upload.tools.util.Validation.pathIsNotUnique;
import static com.gu.upload.tools.util.Validation.pathNotUrlSafe;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.gu.upload.model.StaticFile;
import com.gu.upload.tools.util.User;

@SuppressWarnings("serial")
public class StaticFileCreateAndEditServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uploadUrl = BlobstoreServiceFactory.getBlobstoreService().createUploadUrl("/admin/staticfile/upload");
		request.setAttribute("uploadUrl", uploadUrl);
		StaticFile file = new StaticFile();
		file.setOwner(User.currentUserEmail());
		request.setAttribute("file", file);
		getServletContext().getRequestDispatcher("/WEB-INF/views/tools/staticfile.jsp").forward(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		BlobKey blobKey = BlobstoreServiceFactory.getBlobstoreService().getUploads(request).get("uploadedFile").get(0);
		StaticFile file = new StaticFile();
		
		if (blobKey != null) {
			file.setBlobKey(blobKey.getKeyString());
		}
		file.setName(request.getParameter("name"));
		file.setPath(request.getParameter("path"));
		file.setType(request.getParameter("type"));
		file.setOwner(User.currentUserEmail());
		
		List<String> errors = validate(file);
		
		if (errors.size() > 0) {
			if (blobKey != null) {
				BlobstoreServiceFactory.getBlobstoreService().delete(blobKey);
			}
			
			String error = "";
			for (String e : errors) {
				error += e + "<br/>";
			}
			response.sendRedirect("/error/fileerror.jsp?error=" + URLEncoder.encode(error, "UTF-8"));
			
		} else {
			file.insert();
			response.sendRedirect("/admin/staticfile/list");
		}
	}
	
	private List<String> validate(StaticFile file) {
		List<String> errors = new ArrayList<String>();
		if (anyCommonFieldIsEmpty(file) || empty(file.getBlobKey())) {
			errors.add("All fields are required, none may be empty.");
		}
		if (pathNotUrlSafe(file)) {
			errors.add("Only letters, numbers, slash '/', hypen '-' and dot '.' are allowed in the path.");
		}
		if (pathIsNotUnique(file)) {
			errors.add(String.format("The path [%s] is already in use, please choose another", file.getPath()));
		}
		return errors;
	}
}
