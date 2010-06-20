package com.gu.upload.tools;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gu.upload.model.StaticText;

@SuppressWarnings("serial")
public class StaticTextListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		request.setAttribute("texts", StaticText.all().order("name").fetch(1000));
		getServletContext().getRequestDispatcher("/WEB-INF/views/tools/statictextlist.jsp").forward(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long id = Long.parseLong(request.getParameter("idToDelete"));
		StaticText text = StaticText.findById(id);
		text.delete();
		response.sendRedirect("/admin/statictext/list");
	}
}
