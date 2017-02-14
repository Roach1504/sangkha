package com.goodsign.sangkghanews.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Машка on 10.12.2016.
 */

public class NewsModel implements Serializable {
    int id;
    @SerializedName("image")
    String imagepath;
    String date;
    String title;
    @SerializedName("short")
    String annotation;
    String text;

    public int getId() {
        return id;
    }

    public String getImagepath() {
        return imagepath;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getAnnotation() {
        return annotation;
    }

    public String getText() {
        return text;
    }
}
