package com.gu.upload.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonResponse {

    @SerializedName("files")
    private List<StaticFile> files;

    public JsonResponse() {
        // no-args constructor
    }

    public JsonResponse(List<StaticFile> staticFiles) {
        this.files = staticFiles;
    }

    public final List<StaticFile> getFiles() {
        return this.files;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
