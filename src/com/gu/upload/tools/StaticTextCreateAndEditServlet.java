package com.gu.upload.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gu.upload.model.StaticText;

@SuppressWarnings("serial")
public class StaticTextCreateAndEditServlet extends BaseCreateAndEditServlet {
	
	private static final Pattern URL_SAFE_REGEX = Pattern.compile("[a-zA-Z0-9\\-\\.]*");
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String path = request.getPathInfo();
		
		StaticText staticText = loadOrCreateNew(path);
		
		request.setAttribute("errors", Collections.emptyList());
		request.setAttribute("statictext", staticText);
		getServletContext().getRequestDispatcher("/WEB-INF/views/tools/statictext.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		StaticText staticText = buildModelFromRequest(request);
		List<String> errors = validate(staticText);
		
		if (errors.isEmpty()) {
			staticText.setOwner(getUserEmail());
			staticText.insertOrUpdate();
		}

		request.setAttribute("errors", errors);
		request.setAttribute("statictext", staticText);
		
		getServletContext().getRequestDispatcher("/WEB-INF/views/tools/statictext.jsp").forward(request, response);
	}

	private List<String> validate(StaticText staticText) {
		List<String> errors = new ArrayList<String>();
		if (anyFieldIsEmpty(staticText)) {
			errors.add("All fields are required, none may be empty.");
		}
		
		if (pathNotUrlSafe(staticText)) {
			errors.add("Only letters, numbers, hypen '-' and dot '.' are allowed in the path.");
		}
		
		if (pathIsNotUnique(staticText)) {
			errors.add(String.format("The path [%s] is already in use, please choose another", staticText.getPath()));
		}
		
		return errors;
	}

	private boolean pathIsNotUnique(StaticText staticText) {
		return !empty(staticText.getPath()) && !staticText.isPathUnique();
	}

	private boolean pathNotUrlSafe(StaticText staticText) {
		return !empty(staticText.getPath()) && !URL_SAFE_REGEX.matcher(staticText.getPath()).matches();
	}

	private boolean anyFieldIsEmpty(StaticText staticText) {
		return empty(staticText.getName()) || empty(staticText.getPath()) || empty(staticText.getText()) || empty(staticText.getType());
	}

	private boolean empty(String text) {
		return text == null || text.trim().length() == 0;
	}

	private StaticText buildModelFromRequest(HttpServletRequest request) {
		String idString = request.getParameter("id");
		StaticText staticText = new StaticText();
		
		if (idString != null && idString.trim().length() > 0) {
			staticText = StaticText.findById(Long.parseLong(idString));
		}
		staticText.setText(request.getParameter("text"));
		staticText.setName(request.getParameter("name"));
		staticText.setPath(request.getParameter("path"));
		staticText.setType(request.getParameter("type"));
		return staticText;
	}
	
	private StaticText loadOrCreateNew(String path) {
		StaticText staticText;
		if (path.equals("/new")) {
			staticText = new StaticText();
			staticText.setOwner(getUserEmail());	
		} else {
			Long id = Long.parseLong(path.replace("/", ""));
			staticText = StaticText.findById(id);
		}
		return staticText;
	}
}
