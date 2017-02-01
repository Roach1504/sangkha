package com.goodsign.sangkghanews.Models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Roman on 15.01.2017.
 */

public class HistoryModel implements Serializable
{
    private String name;
    private String annotation;
    private String description;

    private ArrayList<String> imageList;
    private ArrayList<NewsVideo> videoList;

    public HistoryModel(int i)
    {
        name = "Заголовок" + i;
        imageList = new ArrayList<>();
        //imageList.add("image_url1");
        videoList = new ArrayList<>();
        //videoList.add( new NewsVideo("video_url1"));

        //annotation = String.valueOf(R.string.test_normal);
        //description = String.valueOf(R.string.test_long);
    }

    public String getName() {
        return name;
    }

    public String getAnnotation() {
        return annotation;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getImages() {
        return imageList;
    }

    public ArrayList<NewsVideo> getVideos() {
        return videoList;
    }
}
