package com.goodsign.sangkghanews.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodsign.sangkghanews.adapters.AlbumListRecyclerAdapter;
import com.goodsign.sangkghanews.handlers.HttpRequestHandler;
import com.goodsign.sangkghanews.handlers.JsonParser;
import com.goodsign.sangkghanews.models.AlbumModel;
import com.goodsign.sangkghanews.R;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Машка on 19.12.2016.
 */

public class Album extends Fragment implements BackStackResumedFragment {
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private AlbumListRecyclerAdapter albumListRecyclerAdapter;

    private ArrayList<AlbumModel> albumList;

    public static Album newInstance()
    {
        return new Album();
    }

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_album, container,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.frag_album);

        albumList = new ArrayList<>();
        albumListRecyclerAdapter = new AlbumListRecyclerAdapter(albumList,getFragmentManager());
        gridLayoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(albumListRecyclerAdapter);

        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e)
            {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
//                Log.e("ALBUM", response.body().string());
                albumList = JsonParser.getAlbumsArrayFromJson(response.body().string());
                if (albumList != null)
                {
                    if (albumList.size() > 0)
                    {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                albumListRecyclerAdapter.updateList(albumList);
                            }
                        });
                    }
                }
            }
        };
        HttpRequestHandler.getInstance().setCallback(callback);
        HttpRequestHandler.getInstance().post("/api/albums");


        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onFragmentResume() {

    }
}

