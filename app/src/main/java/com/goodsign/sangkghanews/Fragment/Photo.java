package com.goodsign.sangkghanews.Fragment;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodsign.sangkghanews.R;

/**
 * Created by Машка on 19.12.2016.
 */

public class Photo extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_photo, container,false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.frag_photo);
//        public class MarginDecoration extends RecyclerView.ItemDecoration {
//            private int margin;
//
//            public MarginDecoration(Context context) {
//                margin = context.getResources().getDimensionPixelSize(R.dimen.cardview_compat_inset_shadow);
//            }
//
//            @Override
//            public void getItemOffsets(
//                    Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                outRect.set(margin, margin, margin, margin);
//            }
//        }
//        recyclerView.addItemDecoration(new MarginDecoration(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        //recyclerView.setAdapter(new NumberedAdapter(30));
        return view;
    }
}
