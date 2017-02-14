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
import com.goodsign.sangkghanews.models.HistoryModel;
import com.goodsign.sangkghanews.R;

import org.sufficientlysecure.htmltextview.HtmlTextView;

/**
 * Created by Roman on 16.01.2017.
 */

public class History extends Fragment implements BackStackResumedFragment
{
    private TextView title, annotation, date;
    private HtmlTextView description;
    private HistoryModel historyModel;

    public static History newInstance(HistoryModel historyModel)
    {
        History fragment = new History();
        Bundle bundle = new Bundle();
        bundle.putSerializable("HistoryModel", historyModel);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        title = (TextView) view.findViewById(R.id.text_history_title);
        annotation = (TextView) view.findViewById(R.id.text_history_annotation);
        date = (TextView) view.findViewById(R.id.text_history_date);
        description = (HtmlTextView) view.findViewById(R.id.text_history_description);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        historyModel = (HistoryModel) getArguments().getSerializable("HistoryModel");

        if (historyModel != null)
        {
            title.setText(historyModel.getTitle());
            date.setText(historyModel.getDate());
            annotation.setText(historyModel.getAnnotation());
            description.setHtml(historyModel.getDescription());
        }
    }

    @Override
    public void onFragmentResume() {

    }
}
