package com.goodsign.sangkghanews.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.goodsign.sangkghanews.adapters.HooralsListRecyclerAdapter;
import com.goodsign.sangkghanews.R;
import com.goodsign.sangkghanews.handlers.JsonParser;
import com.goodsign.sangkghanews.models.HooralModel;
import com.goodsign.sangkghanews.handlers.HttpRequestHandler;
import com.rey.material.widget.ProgressView;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Roman on 19.01.2017.
 */

public class HooralsList extends Fragment
{

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private HooralsListRecyclerAdapter recyclerAdapter;


    private FragmentManager fragmentManager;
    private ArrayList<HooralModel> hooralList;
    private ArrayList<HooralModel> responseList;
    private Callback lastNewsCallback;

    public static HooralsList newInstance(FragmentManager fragmentManager, ArrayList<HooralModel> hooralList)
    {
        HooralsList hooralsList = new HooralsList();
        Bundle bundle = new Bundle();
        bundle.putSerializable("hooralList", hooralList);
        hooralsList.setArguments(bundle);
        hooralsList.setParentManager(fragmentManager);
        return hooralsList;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hoorals, container, false);
        hooralList = (ArrayList<HooralModel>) getArguments().getSerializable("hooralList");

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_hoorals);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerAdapter = new HooralsListRecyclerAdapter(hooralList, fragmentManager);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }

    public void setParentManager(FragmentManager fragmentManager)
    {
        this.fragmentManager = fragmentManager;
    }

    public String getDateFromModel()
    {
        if (hooralList != null)
        {
            return hooralList.get(0).getFormattedDate();
        }
        else
        {
            return null;
        }
    }


}
