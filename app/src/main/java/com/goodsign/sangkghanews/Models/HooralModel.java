package com.goodsign.sangkghanews.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Roman on 19.01.2017.
 */

public class HooralModel implements Serializable
{
    private int id;
    @SerializedName("day")
    private String date;
    @SerializedName("date")
    private String time;
    private String title;
    private String text;

    private String formattedDate;

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getFormattedDate()
    {
        if (formattedDate == null)
        {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            try
            {
                Date date = format.parse(getDate());
                SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM");
                formattedDate = dateFormat.format(date);
            } catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
        return formattedDate;
    }
}
