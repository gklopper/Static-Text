package com.gu.upload.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gu.upload.model.Project;
import com.gu.upload.tools.util.User;

@SuppressWarnings("serial")
public class ProjectCreateAndEditServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Project project = loadOrCreateNew(request.getPathInfo());
		request.setAttribute("project", project);
		request.setAttribute("errors", Collections.emptyList());
		
		//override current project
		request.setAttribute("currentProject", project);
		
		getServletContext().getRequestDispatcher("/WEB-INF/views/tools/project.jsp").forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		
		Project project;
		if (id == null || id.isEmpty()) {
			project = new Project();
		} else {
			project = Project.findById(Long.parseLong(id));
		}
		
		project.setName(request.getParameter("name"));
		
		List<String> errors = new ArrayList<String>();
		
		if (project.getName() == null || project.getName().trim().isEmpty()) {
			errors.add("Name is a required field");
		} 
	
		
		if (!project.isNameUnique()) {
			errors.add("The name '" + project.getName() + "' is already in use, please choose another");
		}
		
		request.setAttribute("errors", errors);
		
		if (errors.isEmpty()) {
			project.setOwner(User.currentUserEmail());
			if (project.isNew()) {
				project.insert();
			} else {
				project.update();
			}
			getServletContext().getRequestDispatcher("/WEB-INF/views/tools/projectlist.jsp").forward(request, response);
		} else {
			project.setOwner(request.getParameter("owner"));
			request.setAttribute("project", project);
			getServletContext().getRequestDispatcher("/WEB-INF/views/tools/project.jsp").forward(request, response);
		}
	}
	
	private Project loadOrCreateNew(String path) {
		Project project;
		if (path.equals("/new")) {
			project = new Project();
			project.setOwner(User.currentUserEmail());	
		} else {
			Long id = Long.parseLong(path.replace("/", ""));
			project = Project.findById(id);
		}
		return project;
	}
}
