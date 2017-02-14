package com.goodsign.sangkghanews.handlers;

import com.goodsign.sangkghanews.models.AlbumModel;
import com.goodsign.sangkghanews.models.DatsanModel;
import com.goodsign.sangkghanews.models.HistoryModel;
import com.goodsign.sangkghanews.models.HooralModel;
import com.goodsign.sangkghanews.models.LectureModel;
import com.goodsign.sangkghanews.models.NewsModel;
import com.goodsign.sangkghanews.models.PhotoModel;
import com.goodsign.sangkghanews.models.VideoModel;
import com.goodsign.sangkghanews.models.ZurkhayModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Roman on 01.02.2017.
 */

public class JsonParser
{
    private static Gson gson = new Gson();

    public static ArrayList getNewsArrayFromJson(String json)
    {
        Type listType = new TypeToken<ArrayList<NewsModel>>(){}.getType();
        ArrayList<NewsModel> newsModelArrayList = gson.fromJson(json, listType);
        return newsModelArrayList;
    }

    public static ArrayList getVideosArrayFromJson(String json)
    {
        Type listType = new TypeToken<ArrayList<VideoModel>>(){}.getType();
        ArrayList<VideoModel> videoModelArrayList = gson.fromJson(json, listType);
        return  videoModelArrayList;
    }

    public static ArrayList getAlbumsArrayFromJson(String json)
    {
        Type listType = new TypeToken<ArrayList<AlbumModel>>(){}.getType();
        ArrayList<AlbumModel> albumModelArrayList = gson.fromJson(json, listType);
        return albumModelArrayList;
    }

    public static ArrayList getDatsansArrayFromJson(String json)
    {
        Type listType = new TypeToken<ArrayList<DatsanModel>>(){}.getType();
        ArrayList<DatsanModel> datsanModelArrayList = gson.fromJson(json, listType);
        return datsanModelArrayList;
    }

    public static ArrayList getZurkhaisArrayFromJson(String json)
    {
        Type listType = new TypeToken<ArrayList<ZurkhayModel>>(){}.getType();
        ArrayList<ZurkhayModel> zurkhayModelArrayList = gson.fromJson(json, listType);
        return zurkhayModelArrayList;
    }

    public static ArrayList getHooralsArrayFromJson(String json)
    {
        Type listType = new TypeToken<ArrayList<HooralModel>>(){}.getType();
        ArrayList<HooralModel> hooralModelArrayList = gson.fromJson(json, listType);
        return hooralModelArrayList;
    }

    public static ArrayList getLecturesArrayFromJson(String json)
    {
        Type listType = new TypeToken<ArrayList<LectureModel>>(){}.getType();
        ArrayList<LectureModel> lectureModelArrayList = gson.fromJson(json, listType);
        return lectureModelArrayList;
    }

    public static ArrayList getHistoriesArrayFromJson(String json)
    {
        Type listType = new TypeToken<ArrayList<HistoryModel>>(){}.getType();
        ArrayList<HistoryModel> historyModelArrayList = gson.fromJson(json, listType);
        return historyModelArrayList;
    }

    public static ArrayList getPhotosArrayFromJson(String json)
    {
        Type listType = new TypeToken<ArrayList<PhotoModel>>(){}.getType();
        ArrayList<PhotoModel> photoModelArrayList = gson.fromJson(json, listType);
        return photoModelArrayList;
    }

}
