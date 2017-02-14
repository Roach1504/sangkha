package com.goodsign.sangkghanews.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodsign.sangkghanews.adapters.LecturesListRecyclerAdapter;
import com.goodsign.sangkghanews.handlers.HttpRequestHandler;
import com.goodsign.sangkghanews.handlers.JsonParser;
import com.goodsign.sangkghanews.models.LectureModel;
import com.goodsign.sangkghanews.R;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Roman on 10.01.2017.
 */

public class LecturesList extends Fragment implements BackStackResumedFragment
{

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private LecturesListRecyclerAdapter lecturesListRecyclerAdapter;

    private ArrayList<LectureModel> lecturesList;
    private ArrayList<LectureModel> responseList;

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

        lecturesList = new ArrayList<>();
        lecturesListRecyclerAdapter = new LecturesListRecyclerAdapter(lecturesList, getFragmentManager());
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(lecturesListRecyclerAdapter);

        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                responseList = JsonParser.getLecturesArrayFromJson(response.body().string());
                if (responseList != null && responseList.size()>0)
                {
                    lecturesList.addAll(responseList);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lecturesListRecyclerAdapter.updateList(lecturesList);
                        }
                    });
                }
            }
        };
        HttpRequestHandler.getInstance().setCallback(callback);
        HttpRequestHandler.getInstance().post("/api/lectures");
    }

    @Override
    public void onFragmentResume() {

    }
}
