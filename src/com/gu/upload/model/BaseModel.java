package com.gu.upload.model;

public interface BaseModel {

	public String getName();
	public String getPath();
	public String getType();
	
	public boolean isPathUnique();
	
}
