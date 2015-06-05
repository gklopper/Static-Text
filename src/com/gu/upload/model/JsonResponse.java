package com.gu.upload.model;

import com.google.gson.Gson;

import java.util.List;

public class JsonResponse {

    private List<StaticFile> files;

    public JsonResponse(List<StaticFile> staticFiles) {
        this.files = staticFiles;
    }

    public List<StaticFile> getFiles() {
        return files;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
