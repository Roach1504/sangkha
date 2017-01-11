package com.goodsign.sangkghanews.Models;

import com.goodsign.sangkghanews.R;

import java.util.ArrayList;

/**
 * Created by Roman on 10.01.2017.
 */

public class Lecture
{
    private String name;
    private String annotation;
    private String description;

    private ArrayList<String> image_urls;
    private ArrayList<String> video_urls;

    public Lecture(int i)
    {
        name = "Заголовок" + i;
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
