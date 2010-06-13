package com.gu.upload.model;

import siena.Column;
import siena.Id;
import siena.Model;
import siena.Query;
import siena.Table;
import siena.gae.Unindexed;

@Table("StaticText")
public class StaticText extends Model{
	
	@Id private Long id;
	@Column("name") private String name;
	@Column("path") private String path;
	@Column("type") private String type = "text/html; charset=UTF-8";
	@Column("text") @Unindexed private String text;
	@Column("owner") private String owner;
	
	public boolean isNew() {
		return id == null;
	}
	
	public static Query<StaticText> all() {
		return Model.all(StaticText.class);
	}
	
	public static StaticText findById(Long id) {
		return all().filter("id", id).get();
	}
	
	public void insertOrUpdate() {
		if (isNew()) {
			insert();
		} else {
			update();
		}
	}
	
	public boolean isPathUnique() {
		StaticText staticText = all().filter("path", path).get();
		if (staticText == null) {
			return true;
		}
		if (isNew()) {
			return false;
		}
		return id.equals(staticText.getId());
	}
	
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
