package com.gu.upload.tools;

import javax.servlet.http.HttpServlet;

import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public abstract class BaseCreateAndEditServlet extends HttpServlet {
	
    String getUserEmail() {
		return UserServiceFactory.getUserService().getCurrentUser().getEmail();
	}

}
