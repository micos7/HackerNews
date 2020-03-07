package com.example.hackernews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfileResponse {

    @SerializedName("created")
    @Expose
    private Integer created;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("karma")
    @Expose
    private Integer karma;
    @SerializedName("submitted")
    @Expose
    private List<Integer> submitted = null;

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getKarma() {
        return karma;
    }

    public void setKarma(Integer karma) {
        this.karma = karma;
    }

    public List<Integer> getSubmitted() {
        return submitted;
    }

    public void setSubmitted(List<Integer> submitted) {
        this.submitted = submitted;
    }


}
