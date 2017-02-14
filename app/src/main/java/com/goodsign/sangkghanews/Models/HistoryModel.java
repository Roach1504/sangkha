package com.goodsign.sangkghanews.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Roman on 15.01.2017.
 */

public class HistoryModel implements Serializable
{
        int id;
        private String date;
        private String image;
        private String title;
        @SerializedName("short")
        private String annotation;
        @SerializedName("text")
        private String description;

        public int getId() { return id; }

        public String getImage()
        {
                return image;
        }

        public String getDate() { return date; }

        public String getTitle()
        {
            return title;
        }

        public String getAnnotation() {
            return annotation;
        }

        public String getDescription() {
            return description;
        }
}
