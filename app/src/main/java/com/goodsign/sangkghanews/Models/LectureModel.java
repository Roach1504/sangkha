package com.goodsign.sangkghanews.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Roman on 10.01.2017.
 */

public class LectureModel implements Serializable {
    int id;
    private String date;
    private String title;
    @SerializedName("short")
    private String annotation;
    @SerializedName("text")
    private String description;
      private String image;

    public int getId() {
        return id;
    }

    public String getTitle() { return title; }

    public String getDate() { return date; }

    public String getAnnotation() {
        return annotation;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() { return image; }
}
