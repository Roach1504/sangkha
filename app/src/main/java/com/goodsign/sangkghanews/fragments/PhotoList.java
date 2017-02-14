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
import com.goodsign.sangkghanews.adapters.PhotoListRecyclerAdapter;
import com.goodsign.sangkghanews.handlers.HttpRequestHandler;
import com.goodsign.sangkghanews.handlers.JsonParser;
import com.goodsign.sangkghanews.models.AlbumModel;
import com.goodsign.sangkghanews.models.PhotoModel;
import com.goodsign.sangkghanews.R;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Машка on 18.01.2017.
 */

public class PhotoList extends Fragment implements BackStackResumedFragment{
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private PhotoListRecyclerAdapter photoListRecyclerAdapter;

    private Callback callback;
    private ArrayList<PhotoModel> responseList, previousResponseList;
    private ArrayList<PhotoModel> photoList;
    private int pageNum;

    private AlbumModel album;

    public static PhotoList newInstance(AlbumModel model)
    {
        PhotoList photoList = new PhotoList();
        Bundle bundle = new Bundle();
        bundle.putSerializable("albumModel", model);
        photoList.setArguments(bundle);
        return photoList;
    }

//    public static PhotoList newInstance(PhotoModel photoModel)
//    {
//        PhotoList fragment = new PhotoList();
//        Bundle bundle = new Bundle();
////        bundle.putSerializable("PhotoModel", photoModel);
//        fragment.setArguments(bundle);
//
//        return fragment;
//    }

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view=inflater.inflate(R.layout.fragment_list_photo, container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_photos);

        album = (AlbumModel) getArguments().getSerializable("albumModel");
        photoList = new ArrayList<>();
        pageNum = 0;

        photoListRecyclerAdapter = new PhotoListRecyclerAdapter(photoList, getFragmentManager());
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(photoListRecyclerAdapter);

        callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                responseList = JsonParser.getPhotosArrayFromJson(response.body().string());

                if (previousResponseList == null || !contains(previousResponseList, responseList.get(0)))
                {
                    for (int i = 0; i < responseList.size(); i++)
                    {
                        if (album.getId() == responseList.get(i).getAlbum())
                        {
                            photoList.add(responseList.get(i));
                        }
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run()
                        {
                            photoListRecyclerAdapter.updateList(photoList);
                        }
                    });
                    previousResponseList = responseList;
                    pageNum++;
                    recursivePhotoLoad(pageNum);
                }
            }
        };
        recursivePhotoLoad(pageNum);
//        Boolean empty = false;
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

    private void recursivePhotoLoad(int pageNum)
    {
        HttpRequestHandler.getInstance().setCallback(callback);
        HttpRequestHandler.getInstance().postWithParams("/api/photos", pageNum);
    }

    private boolean contains(ArrayList<PhotoModel> list, PhotoModel model)
    {
        for (PhotoModel m : list)
        {
            if (m.getId() == model.getId())
            {
                Log.e("CONTAINS", "TRUE");
                return true;
            }
        }
        Log.e("CONTAINS", "FALSE");
        return false;
    }
}
