package com.goodsign.sangkghanews.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Машка on 17.01.2017.
 */

public class PhotoModel implements Serializable {
    private int id_photos;
    private int album;
    private String image;
    @SerializedName("short")
    private String description;

    public int getId() {
        return id_photos;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public int getAlbum() {
        return album;
    }
}
