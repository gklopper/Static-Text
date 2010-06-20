package com.gu.upload.tools.util;

import com.google.appengine.api.users.UserServiceFactory;

public class User {

	private User(){}
	
	public static String currentUserEmail() {
		return UserServiceFactory.getUserService().getCurrentUser().getEmail();
	}
}
