package com.goodsign.sangkghanews.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodsign.sangkghanews.Adapters.HooralsListRecyclerAdapter;
import com.goodsign.sangkghanews.R;

/**
 * Created by Roman on 19.01.2017.
 */

public class HooralsList extends Fragment
{

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private HooralsListRecyclerAdapter recyclerAdapter;

    private FragmentManager fragmentManager;

    public static HooralsList newInstance(FragmentManager fragmentManager)
    {
        HooralsList hooralsList = new HooralsList();
        hooralsList.setParentManager(fragmentManager);
        return hooralsList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hoorals, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_hoorals);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerAdapter = new HooralsListRecyclerAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void setParentManager(FragmentManager fragmentManager)
    {
        this.fragmentManager = fragmentManager;
    }
}
