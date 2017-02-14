package com.goodsign.sangkghanews.models;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Машка on 18.01.2017.
 */

public class AlbumModel implements Serializable
{
    private int id;
    @SerializedName("image")
    private String image_url;
    @SerializedName("name")
    private String title;
    @SerializedName("short")
    private String decription;

    public AlbumModel(String image_url,String title,String decription){
        this.image_url = image_url;
        this.title = title;
        this.decription = decription;
    }
    public String getImage_url() {
        return image_url;
    }

    public String getTitle() {
        return title;
    }

    public String getDecription() {
        return decription;
    }

    public int getId() {
        return id;
    }
}
