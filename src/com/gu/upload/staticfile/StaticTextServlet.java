package com.gu.upload.staticfile;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gu.upload.model.StaticText;

@SuppressWarnings("serial")
public class StaticTextServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String path = request.getPathInfo();
		
		path = removeLeadingSlash(path);
		
		StaticText staticText = StaticText.all().filter("path", path).get();

		if (staticText == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		response.setContentType(staticText.getType());
		
		//cache for one hour
		response.setHeader("Cache-Control", "public, max-age=3600");
		response.getWriter().println(staticText.getText());
	}

	private String removeLeadingSlash(String path) {
		if (path !=null && path.length() > 1 && path.startsWith("/")) {
			path = path.substring(1);
		}
		return path;
	}
}
