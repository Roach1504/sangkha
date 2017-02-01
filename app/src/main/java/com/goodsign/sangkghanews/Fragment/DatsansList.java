package com.goodsign.sangkghanews.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodsign.sangkghanews.Adapters.DatsanListRecyclerAdapters;
import com.goodsign.sangkghanews.Adapters.VideoListRecyclerAdapter;
import com.goodsign.sangkghanews.Models.DatsansModel;
import com.goodsign.sangkghanews.Models.NewsVideo;
import com.goodsign.sangkghanews.R;

import java.util.ArrayList;

/**
 * Created by Машка on 16.01.2017.
 */

public class DatsansList extends Fragment {
    private DatsanListRecyclerAdapters datsanListRecyclerAdapters;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    public static DatsansList newInstance()
    {
        return new DatsansList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_datsans_list, container,false);
        ArrayList<DatsansModel> Datsan_arrayList = new ArrayList<DatsansModel>();
        for (int i=0; i<20; i++ )
        {
            Datsan_arrayList.add(new DatsansModel("Заголовок " +i,"Кавабанга "+i));
        }
        recyclerView = (RecyclerView) view.findViewById(R.id.frag_datsan);
        layoutManager = new LinearLayoutManager(getActivity());

        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        datsanListRecyclerAdapters = new DatsanListRecyclerAdapters(Datsan_arrayList , getActivity().getApplicationContext());
        recyclerView.setAdapter(datsanListRecyclerAdapters);
        return view;
    }
}
