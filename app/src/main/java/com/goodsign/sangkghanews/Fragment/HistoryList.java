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

import com.goodsign.sangkghanews.Adapters.HistoriesListRecyclerAdapter;
import com.goodsign.sangkghanews.Models.HistoryModel;
import com.goodsign.sangkghanews.R;

import java.util.ArrayList;

/**
 * Created by Roman on 15.01.2017.
 */

public class HistoryList extends Fragment
{

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private HistoriesListRecyclerAdapter recyclerAdapter;

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
        ArrayList<HistoryModel> historyList = new ArrayList<>();
        for (int i = 0; i < 20; i++)
        {
            historyList.add(new HistoryModel(i));
        }
        recyclerAdapter = new HistoriesListRecyclerAdapter(historyList, getFragmentManager());
        layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());

        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);
    }
}
