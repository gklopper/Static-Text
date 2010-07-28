package com.gu.upload.tools;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.gu.upload.model.Project;

public class ProjectFilter implements Filter {

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		String projectId = request.getParameter("projectId");
		
		Project currentProject = new Project();
		if (projectId != null && !projectId.isEmpty()) {
			currentProject = Project.findById(Long.parseLong(projectId));
		}
		request.setAttribute("currentProject", currentProject);
		
		request.setAttribute("projects", Project.getAllInOrder());
		
		chain.doFilter(request, response);
		
	}

	@Override
	public void init(FilterConfig config) throws ServletException {}

}
