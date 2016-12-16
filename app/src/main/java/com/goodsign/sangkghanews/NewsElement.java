package com.goodsign.sangkghanews;

/**
 * Created by Машка on 10.12.2016.
 */

public class NewsElement {
    String header;
    String body;
    public NewsElement(String header, String body)
    {
        this.header=header;
        this.body=body;
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
