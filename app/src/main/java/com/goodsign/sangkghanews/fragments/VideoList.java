package com.goodsign.sangkghanews.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodsign.sangkghanews.adapters.VideoListRecyclerAdapter;
import com.goodsign.sangkghanews.handlers.HttpRequestHandler;
import com.goodsign.sangkghanews.handlers.JsonParser;
import com.goodsign.sangkghanews.models.VideoModel;
import com.goodsign.sangkghanews.R;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Машка on 17.12.2016.
 */
/* Вид списка видео*/
public class VideoList extends Fragment implements BackStackResumedFragment {
    private VideoListRecyclerAdapter videoListRecyclerAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private ArrayList<VideoModel> videoModelArrayList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_list_video, container,false);
        videoModelArrayList = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.frag_video);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        videoListRecyclerAdapter = new VideoListRecyclerAdapter(videoModelArrayList, getActivity().getApplicationContext());
        recyclerView.setAdapter(videoListRecyclerAdapter);

        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                videoModelArrayList = JsonParser.getVideosArrayFromJson(response.body().string());
                if (videoModelArrayList != null)
                {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            videoListRecyclerAdapter.updateList(videoModelArrayList);
                        }
                    });
                }
            }
        };
        HttpRequestHandler.getInstance().setCallback(callback);
        HttpRequestHandler.getInstance().post("/api/videos");

        return view;
    }


    @Override
    public void onFragmentResume() {

    }
}
