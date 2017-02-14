package com.goodsign.sangkghanews.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodsign.sangkghanews.adapters.HistoriesListRecyclerAdapter;
import com.goodsign.sangkghanews.handlers.HttpRequestHandler;
import com.goodsign.sangkghanews.handlers.JsonParser;
import com.goodsign.sangkghanews.models.HistoryModel;
import com.goodsign.sangkghanews.R;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Roman on 15.01.2017.
 */

public class HistoryList extends Fragment implements BackStackResumedFragment
{

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private HistoriesListRecyclerAdapter historiesListRecyclerAdapter;

    private ArrayList<HistoryModel> historyList;
    private ArrayList<HistoryModel> responseList;

    public static HistoryList newInstance()
    {
        return new HistoryList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_histories, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_histories);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        historyList = new ArrayList<>();
        historiesListRecyclerAdapter = new HistoriesListRecyclerAdapter(historyList, getFragmentManager());
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(historiesListRecyclerAdapter);

        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                responseList = JsonParser.getHistoriesArrayFromJson(response.body().string());
                if (responseList != null && responseList.size()>0)
                {
                    historyList.addAll(responseList);
                    getActivity().runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run() {
                            historiesListRecyclerAdapter.updateList(historyList);
                        }
                    });
                }
            }
        };
        HttpRequestHandler.getInstance().setCallback(callback);
        HttpRequestHandler.getInstance().post("/api/history");

    }

    @Override
    public void onFragmentResume() {

    }
}
