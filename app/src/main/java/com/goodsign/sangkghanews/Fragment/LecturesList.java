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

import com.goodsign.sangkghanews.Adapters.LecturesListRecyclerAdapter;
import com.goodsign.sangkghanews.Models.LectureModel;
import com.goodsign.sangkghanews.R;

import java.util.ArrayList;

/**
 * Created by Roman on 10.01.2017.
 */

public class LecturesList extends Fragment
{

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private LecturesListRecyclerAdapter recyclerAdapter;

    public static LecturesList newInstance()
    {
        return new LecturesList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_list_lectures, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_lectures);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<LectureModel> lecturesList = new ArrayList<>();
        for (int i = 0; i < 20; i++)
        {
            lecturesList.add(new LectureModel(i));
        }
        recyclerAdapter = new LecturesListRecyclerAdapter(lecturesList, getFragmentManager());
        layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());

        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);
    }
}
