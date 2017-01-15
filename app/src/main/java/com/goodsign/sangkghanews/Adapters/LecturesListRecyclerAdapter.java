package com.goodsign.sangkghanews.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodsign.sangkghanews.Fragment.Lecture;
import com.goodsign.sangkghanews.Models.LectureModel;
import com.goodsign.sangkghanews.R;

import java.util.ArrayList;

/**
 * Created by Roman on 10.01.2017.
 */

public class LecturesListRecyclerAdapter extends RecyclerView.Adapter<LecturesListRecyclerAdapter.LecturesListViewHolder>
{
    private FragmentManager fragmentManager;
    private ArrayList<LectureModel> lecturesList;

    public LecturesListRecyclerAdapter(ArrayList<LectureModel> lecturesList, FragmentManager fragmentManager)
    {
        this.fragmentManager = fragmentManager;
        this.lecturesList = lecturesList;
    }

    public static class LecturesListViewHolder extends RecyclerView.ViewHolder
    {
        private LinearLayout lecture_layout;
        private TextView text_lectureName;
        private TextView text_lectureAnnotation;

        public LecturesListViewHolder(View itemView)
        {
            super(itemView);
            lecture_layout = (LinearLayout) itemView.findViewById(R.id.layout_recyclerview_lecture);
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
        holder.lecture_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Lecture();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lecturesList.size();
    }


}
