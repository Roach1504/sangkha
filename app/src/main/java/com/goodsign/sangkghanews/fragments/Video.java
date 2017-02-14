package com.goodsign.sangkghanews.fragments;

/**
 * Created by Машка on 11.01.2017.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodsign.sangkghanews.adapters.VideoListRecyclerAdapter;
import com.goodsign.sangkghanews.models.VideoModel;
import com.goodsign.sangkghanews.R;

import java.util.ArrayList;

/**
 * Created by Машка on 17.12.2016.
 */
public class Video extends Fragment {
    private VideoListRecyclerAdapter videoListRecyclerAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_list_video, container,false);
        ArrayList<VideoModel> videoModelArrayList = new ArrayList<VideoModel>();
        recyclerView = (RecyclerView) view.findViewById(R.id.frag_video);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        videoListRecyclerAdapter = new VideoListRecyclerAdapter(videoModelArrayList, getActivity().getApplicationContext());
        recyclerView.setAdapter(videoListRecyclerAdapter);
        return view;
    }

}
