package com.goodsign.sangkghanews.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodsign.sangkghanews.Models.HistoryModel;
import com.goodsign.sangkghanews.R;

import java.util.ArrayList;

/**
 * Created by Roman on 15.01.2017.
 */

public class HistoriesListRecyclerAdapter extends RecyclerView.Adapter<HistoriesListRecyclerAdapter.HistoriesListViewHolder>
{

    private ArrayList<HistoryModel> historiesList;
    private FragmentManager fragmentManager;

    public static class HistoriesListViewHolder extends RecyclerView.ViewHolder
    {

        private LinearLayout history_layout;
        private TextView text_historyName;
        private TextView text_historyAnnotation;

        public HistoriesListViewHolder(View itemView)
        {
            super(itemView);
            history_layout = (LinearLayout) itemView.findViewById(R.id.layout_recyclerview_history);
            text_historyName = (TextView) itemView.findViewById(R.id.text_history_name);
            text_historyAnnotation = (TextView) itemView.findViewById(R.id.text_history_annotation);
        }
    }

    public HistoriesListRecyclerAdapter(ArrayList<HistoryModel> historiesList, FragmentManager fragmentManager)
    {
        this.fragmentManager = fragmentManager;
        this.historiesList = historiesList;
    }

    @Override
    public HistoriesListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_history, parent, false);
        HistoriesListRecyclerAdapter.HistoriesListViewHolder hlvh = new HistoriesListRecyclerAdapter.HistoriesListViewHolder(v);
        return hlvh;
    }

    @Override
    public void onBindViewHolder(HistoriesListViewHolder holder, int position)
    {
        holder.text_historyName.setText(historiesList.get(position).getName());
        holder.text_historyAnnotation.setText(R.string.test_normal);
        holder.history_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Fragment fragment =
            }
        });
    }

    @Override
    public int getItemCount() {
        return historiesList.size();
    }

}
