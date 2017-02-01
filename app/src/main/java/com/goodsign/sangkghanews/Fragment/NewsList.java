package com.goodsign.sangkghanews.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodsign.sangkghanews.Adapters.RecyclerAdapter;
import com.goodsign.sangkghanews.Models.NewsElement;
import com.goodsign.sangkghanews.R;

import java.util.ArrayList;

/**
 * Created by Машка on 10.12.2016.
 */
/* Вид новостного списка */
public class NewsList extends Fragment{
    private RecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_list_news, container,false);
        ArrayList<NewsElement> newsElementArrayList = new ArrayList<NewsElement>();
        for (int i=0; i<20; i++ )
        {
            newsElementArrayList.add(new NewsElement("Заголовок" +i,"Описание"+i));
        }
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerAdapter = new RecyclerAdapter(newsElementArrayList , getContext(), getFragmentManager());
        recyclerView.setAdapter(recyclerAdapter);
        return view;
    }
}
