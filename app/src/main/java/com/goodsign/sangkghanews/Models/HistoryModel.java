package com.goodsign.sangkghanews.Models;

import java.util.ArrayList;

/**
 * Created by Roman on 15.01.2017.
 */

public class HistoryModel
{
    private String name;
    private String annotation;
    private String description;

    private ArrayList<String> image_urls;
    private ArrayList<String> video_urls;

    public HistoryModel(int i)
    {
        name = "Заголовок" + i;
        image_urls = new ArrayList<>();
        image_urls.add("image_url1");
        video_urls = new ArrayList<>();
        video_urls.add("video_url1");

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

    public ArrayList<String> getImage_urls() {
        return image_urls;
    }

    public ArrayList<String> getVideo_urls() {
        return video_urls;
    }
}
