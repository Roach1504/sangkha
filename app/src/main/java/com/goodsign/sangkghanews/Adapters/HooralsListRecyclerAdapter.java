package com.goodsign.sangkghanews.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodsign.sangkghanews.Models.HooralModel;
import com.goodsign.sangkghanews.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Roman on 19.01.2017.
 */

public class HooralsListRecyclerAdapter extends RecyclerView.Adapter<HooralsListRecyclerAdapter.HooralsListViewHolder>
{
    private ArrayList<HooralModel> hooralsList;

    public static class HooralsListViewHolder extends RecyclerView.ViewHolder
    {
        private TextView hooral_name, hooral_time, hooral_description;

        public HooralsListViewHolder(View itemView)
        {
            super(itemView);
            hooral_name = (TextView) itemView.findViewById(R.id.text_hooral_name);
            hooral_time = (TextView) itemView.findViewById(R.id.text_hooral_time);
            hooral_description = (TextView) itemView.findViewById(R.id.text_hooral_description);
        }
    }

    public HooralsListRecyclerAdapter(/*ArrayList<HooralModel> hooralsList*/)
    {
//        this.hooralsList = hooralsList;
    }

    @Override
    public HooralsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_hooral, parent, false);
        HooralsListRecyclerAdapter.HooralsListViewHolder hlvh = new HooralsListRecyclerAdapter.HooralsListViewHolder(view);
        return hlvh;
    }

    @Override
    public void onBindViewHolder(HooralsListViewHolder holder, int position)
    {

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
