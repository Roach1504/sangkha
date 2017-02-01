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
import com.goodsign.sangkghanews.Models.AlbumModel;
import com.goodsign.sangkghanews.R;

import java.util.ArrayList;

/**
 * Created by Машка on 19.12.2016.
 */

public class Album extends Fragment {
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private AlbumListRecyclerAdapter albumListRecyclerAdapter;

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
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<AlbumModel> albumList = new ArrayList<>();
        for (int i = 0; i < 20; i++)
        {
            albumList.add(new AlbumModel());
        }
        albumListRecyclerAdapter = new AlbumListRecyclerAdapter(albumList,getFragmentManager());
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), gridLayoutManager.getOrientation());

        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(albumListRecyclerAdapter);
    }
//    public class MarginDecoration extends RecyclerView.ItemDecoration {
//        private int margin;
//
//        public MarginDecoration(Context context) {
//            margin = context.getResources().getDimensionPixelSize(R.dimen.cardview_compat_inset_shadow);
//        }
//
//        @Override
//        public void getItemOffsets(
//                Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//            outRect.set(margin, margin, margin, margin);
//        }
//    }
}

