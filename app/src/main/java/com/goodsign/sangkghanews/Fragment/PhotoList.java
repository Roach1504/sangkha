package com.goodsign.sangkghanews.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodsign.sangkghanews.Adapters.AlbumListRecyclerAdapter;
import com.goodsign.sangkghanews.Models.PhotoModel;
import com.goodsign.sangkghanews.R;

import java.util.ArrayList;

/**
 * Created by Машка on 18.01.2017.
 */

public class PhotoList extends Fragment{
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private AlbumListRecyclerAdapter albumListRecyclerAdapter;

    public static PhotoList newInstance()
    {
        return new PhotoList();
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
        recyclerView = (RecyclerView) view.findViewById(R.id.frag_photo);
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<PhotoModel> photoList = new ArrayList<>();
        for (int i = 0; i < 20; i++)
        {
            photoList.add(new PhotoModel());
        }

        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), gridLayoutManager.getOrientation());

        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(albumListRecyclerAdapter);
    }
}
