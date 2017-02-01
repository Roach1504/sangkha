package com.goodsign.sangkghanews.Models;

/**
 * Created by Машка on 17.01.2017.
 */

public class DatsansModel {
    private String header;
    private String body;

    public DatsansModel(String header,String body){
        this.header = header;
        this.body = body;
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
