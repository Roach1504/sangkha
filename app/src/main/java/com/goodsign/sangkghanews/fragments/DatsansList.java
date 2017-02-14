package com.goodsign.sangkghanews.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodsign.sangkghanews.adapters.DatsanListRecyclerAdapter;
import com.goodsign.sangkghanews.handlers.HttpRequestHandler;
import com.goodsign.sangkghanews.handlers.JsonParser;
import com.goodsign.sangkghanews.models.DatsanModel;
import com.goodsign.sangkghanews.R;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Машка on 16.01.2017.
 */

public class DatsansList extends Fragment implements BackStackResumedFragment {
    private DatsanListRecyclerAdapter datsanListRecyclerAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout refreshLayout;

    private ArrayList<DatsanModel> datsansList;
    private Callback callback;

    public static DatsansList newInstance()
    {
        return new DatsansList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_datsans_list, container,false);
        datsansList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.frag_datsan);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_refresh_datsan);
        layoutManager = new LinearLayoutManager(getActivity());

        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        datsanListRecyclerAdapter = new DatsanListRecyclerAdapter(datsansList);
        recyclerView.setAdapter(datsanListRecyclerAdapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callback = new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                datsansList = JsonParser.getDatsansArrayFromJson(response.body().string());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                        datsanListRecyclerAdapter.updateList(datsansList);
                    }
                });
            }
        };
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                Log.e("REFRESH", "TRIGGERED");
                HttpRequestHandler.getInstance().setCallback(callback);
                HttpRequestHandler.getInstance().post("/api/news");
            }
        });

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                HttpRequestHandler.getInstance().setCallback(callback);
                HttpRequestHandler.getInstance().post("/api/news");
            }
        });
//        HttpRequestHandler.getInstance().setCallback(callback);
//        HttpRequestHandler.getInstance().post("/api/datsans");
    }

    @Override
    public void onFragmentResume() {

    }
}
