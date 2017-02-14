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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodsign.sangkghanews.adapters.VideoListRecyclerAdapter;
import com.goodsign.sangkghanews.models.LectureModel;
import com.goodsign.sangkghanews.R;

import org.sufficientlysecure.htmltextview.HtmlTextView;

/**
 * Created by Roman on 11.01.2017.
 */

public class Lecture extends Fragment implements BackStackResumedFragment
{
    private TextView title, annotation, date;
    private HtmlTextView description;

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
        title = (TextView) view.findViewById(R.id.text_lecture_title);
        annotation = (TextView) view.findViewById(R.id.text_lecture_annotation);
        date = (TextView) view.findViewById(R.id.text_lecture_date);
        description = (HtmlTextView) view.findViewById(R.id.text_lecture_description);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        lectureModel = (LectureModel) getArguments().getSerializable("LectureModel");

        if (lectureModel != null)
        {
            title.setText(lectureModel.getTitle());
            date.setText(lectureModel.getDate());
            annotation.setText(lectureModel.getAnnotation());
            description.setHtml(lectureModel.getDescription());
        }
    }

    @Override
    public void onFragmentResume() {

    }
}
