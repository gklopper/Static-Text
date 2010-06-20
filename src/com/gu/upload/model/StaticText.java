package com.gu.upload.model;

import siena.Column;
import siena.Id;
import siena.Model;
import siena.Query;
import siena.Table;
import siena.gae.Unindexed;

@Table("StaticText")
public class StaticText extends Model implements BaseModel {
	
	@Id private Long id;
	
	@Column("text") @Unindexed 
	private String text;
	
	@Column("name") private String name;
	@Column("path") private String path;
	@Column("type") private String type = "text/html; charset=UTF-8";
	@Column("owner") private String owner;
	
	public static Query<StaticText> all() {
		return Model.all(StaticText.class);
	}
	
	public static StaticText findById(Long id) {
		return all().filter("id", id).get();
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public boolean isNew() {
		return getId() == null;
	}

	public boolean isPathUnique() {
		StaticText model = all().filter("path", path).get();
		if (model == null) {
			return true;
		}
		if (isNew()) {
			return false;
		}
		return getId().equals(model.getId());
	}
	
	public void insertOrUpdate() {
		if (isNew()) {
			insert();
		} else {
			update();
		}
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}