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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodsign.sangkghanews.Adapters.VideoListRecyclerAdapter;
import com.goodsign.sangkghanews.Models.LectureModel;
import com.goodsign.sangkghanews.R;

/**
 * Created by Roman on 11.01.2017.
 */

public class Lecture extends Fragment
{

    private LinearLayout layout_pictures, layout_videos;
    private TextView text_name, text_description;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private VideoListRecyclerAdapter recyclerAdapter;
    private LectureModel lectureModel;

    public static Lecture newInstance(LectureModel lectureModel)
    {
        Lecture fragment = new Lecture();
        Bundle bundle = new Bundle();
        bundle.putSerializable("LectureModel", lectureModel);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_lecture, container, false);
        layout_pictures = (LinearLayout) view.findViewById(R.id.layout_lecture_pictures);
        layout_videos = (LinearLayout) view.findViewById(R.id.layout_lecture_videos);
        text_name = (TextView) view.findViewById(R.id.text_lecture_name);
        text_description = (TextView) view.findViewById(R.id.text_lecture_description);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_lecture_videos);
        layoutManager = new LinearLayoutManager(getContext());

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        lectureModel = (LectureModel) getArguments().getSerializable("LectureModel");
        recyclerAdapter = new VideoListRecyclerAdapter(lectureModel.getVideos(), getContext());
        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        text_name.setText(lectureModel.getName());
        if (lectureModel.getImages().size() == 0)
        {
            layout_pictures.setVisibility(View.GONE);
        }
        else
        {

        }
        if (lectureModel.getVideos().size() == 0)
        {
            layout_videos.setVisibility(View.GONE);
        }

    }
}
