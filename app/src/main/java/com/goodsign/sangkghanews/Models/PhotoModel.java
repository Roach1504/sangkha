package com.goodsign.sangkghanews.Models;

import android.media.Image;

import java.util.ArrayList;

/**
 * Created by Машка on 17.01.2017.
 */

public class PhotoModel {
    private String image_url;
    private String header;
    private String body;

    public PhotoModel() {

    }

    public PhotoModel(String image_url,String header,String body){
        this.image_url = image_url;
        this.header = header;
        this.body = body;
    }
    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
