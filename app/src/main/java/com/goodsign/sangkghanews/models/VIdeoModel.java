package com.goodsign.sangkghanews.models;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by Машка on 17.12.2016.
 */

public class VideoModel {
    int id;
    String date;
    String title;
    String text;

    String video_url;

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getText()
    {
        return text;
    }

    public String getVideo_url()
    {
        if (video_url == null)
        {
            Document document = Jsoup.parseBodyFragment(text);
            Element element = document.getElementsByTag("iframe").first();
            video_url = "http:" + element.attr("src");
            document = null;
            element = null;
        }
        return video_url;
    }
}
