package com.gu.upload.model;

import siena.Column;
import siena.Id;
import siena.Model;
import siena.Query;
import siena.Table;

@Table("StaticFile")
public class StaticFile extends Model implements BaseModel{
	
	@Id private Long id;
	@Column("blobKey") private String blobKey;
	@Column("name") private String name;
	@Column("path") private String path;
	@Column("type") private String type = "image/gif";
	@Column("owner") private String owner;

    private String fileData;

	public static Query<StaticFile> all() {
		return Model.all(StaticFile.class);
	}
	
	public static StaticFile findById(Long id) {
		return all().filter("id", id).get();
	}
	
	public boolean isNew() {
		return getId() == null;
	}

	public boolean isPathUnique() {
		StaticFile model = all().filter("path", path).get();
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

	public void setBlobKey(String blobKey) {
		this.blobKey = blobKey;
	}

	public String getBlobKey() {
		return blobKey;
	}

    public void setData(String data) {
        this.fileData = data;
    }

    public String getData() {
        return fileData;
    }

}
