package com.goodsign.sangkghanews.Models;

import com.goodsign.sangkghanews.R;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Roman on 10.01.2017.
 */

public class LectureModel implements Serializable {
    private String name;
    private String annotation;
    private String description;

    private ArrayList<String> imageList;
    private ArrayList<NewsVideo> videosList;

    public LectureModel(int i)
    {
        name = "Заголовок" + i;
        imageList = new ArrayList<>();
        imageList.add("image_url1");
        videosList = new ArrayList<>();
        videosList.add(new NewsVideo("video_url1"));
        videosList.add(new NewsVideo("video_url2"));

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

    public ArrayList<NewsVideo> getVideos()
    {
        return videosList;
    }
}
