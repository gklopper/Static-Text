package com.gu.upload.tools.util;

import java.util.regex.Pattern;

import com.gu.upload.model.BaseModel;

public class Validation {
	private final static String lowerCase = "a-z";
	private final static String upperCase = "A-Z";
	private final static String numbers = "0-9";
	private final static String forwardSlash = "/";
	private final static String hyphen = "\\-";
	private final static String dot = "\\.";
	// a-zA-Z0-9/\\-\\.
	private static final Pattern URL_SAFE_REGEX = Pattern.compile("[" + lowerCase + upperCase + numbers + forwardSlash + hyphen + dot + "]*");
	
	private Validation(){}
	
	public static boolean pathIsNotUnique(BaseModel model) {
		return !empty(model.getPath()) && !model.isPathUnique();
	}
	
	public static boolean empty(String text) {
		return text == null || text.trim().length() == 0;
	}

	public static boolean pathNotUrlSafe(BaseModel model) {
		return !empty(model.getPath()) && !URL_SAFE_REGEX.matcher(model.getPath()).matches();
	}

	public static boolean anyCommonFieldIsEmpty(BaseModel model) {
		return empty(model.getName()) || empty(model.getPath()) || empty(model.getType());
	}
}
