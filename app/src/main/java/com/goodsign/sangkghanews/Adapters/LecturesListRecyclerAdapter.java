package com.goodsign.sangkghanews.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodsign.sangkghanews.Models.Lecture;
import com.goodsign.sangkghanews.Models.NewsElement;
import com.goodsign.sangkghanews.R;

import java.util.ArrayList;

/**
 * Created by Roman on 10.01.2017.
 */

public class LecturesListRecyclerAdapter extends RecyclerView.Adapter<LecturesListRecyclerAdapter.LecturesListViewHolder>
{
    private ArrayList<Lecture> lecturesList;

    public LecturesListRecyclerAdapter(ArrayList<Lecture> lecturesList)
    {
        this.lecturesList = lecturesList;
    }

    public static class LecturesListViewHolder extends RecyclerView.ViewHolder
    {
        private TextView text_lectureName;
        private TextView text_lectureAnnotation;

        public LecturesListViewHolder(View itemView)
        {
            super(itemView);
            text_lectureName = (TextView) itemView.findViewById(R.id.text_lecture_name);
            text_lectureAnnotation = (TextView) itemView.findViewById(R.id.text_lecture_annotation);
        }
    }

    @Override
    public LecturesListRecyclerAdapter.LecturesListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_lecture, parent, false);
        LecturesListRecyclerAdapter.LecturesListViewHolder llvh = new LecturesListRecyclerAdapter.LecturesListViewHolder(v);
        return  llvh;
    }

    @Override
    public void onBindViewHolder(LecturesListRecyclerAdapter.LecturesListViewHolder holder, int position)
    {
        holder.text_lectureName.setText(lecturesList.get(position).getName());
        holder.text_lectureAnnotation.setText(R.string.test_normal);
    }

    @Override
    public int getItemCount() {
        return lecturesList.size();
    }


}
