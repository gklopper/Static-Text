package com.gu.upload.model;

import java.util.List;

import siena.Column;
import siena.Id;
import siena.Model;
import siena.Query;
import siena.Table;

@Table("Project")
public class Project extends Model {

	@Id private Long id;
	@Column("name") private String name;
	@Column("owner") private String owner;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getOwner() {
		return owner;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isNew() {
		return id == null;
	}
	
	public static Query<Project> all() {
		return Model.all(Project.class);
	}
	
	public static List<Project> getAllInOrder() {
		return all().order("name").fetch();
	}
	
	public static Project findById(Long id) {
		return all().filter("id", id).get();
	}
	
	public boolean isNameUnique() {
		
		Project withSameName = all().filter("name", name).get();
		
		if(isNew()) {
			return withSameName == null;
		}
		
		return withSameName == null ? true : withSameName.getId().equals(getId());
	}
}
