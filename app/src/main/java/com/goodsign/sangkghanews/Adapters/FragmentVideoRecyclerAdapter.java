package com.goodsign.sangkghanews.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodsign.sangkghanews.Models.NewsVideo;
import com.goodsign.sangkghanews.R;

import java.util.ArrayList;

/**
 * Created by Машка on 17.12.2016.
 */

public class FragmentVideoRecyclerAdapter extends RecyclerView.Adapter<FragmentVideoRecyclerAdapter.FragmentVideoViewHolder> {
    private Context context;
    private ArrayList<NewsVideo> newsVideoArrayList;

    public FragmentVideoRecyclerAdapter(ArrayList<NewsVideo> newsVideoArrayList, Context context) {
        this.context = context;
        this.newsVideoArrayList = newsVideoArrayList;
    }

    public static class FragmentVideoViewHolder extends RecyclerView.ViewHolder{
        ImageView imgViewVideo;
        TextView txtViewVideo;

        public FragmentVideoViewHolder(View itemView) {
            super(itemView);
            imgViewVideo = (ImageView) itemView.findViewById(R.id.imageView1);
            txtViewVideo = (TextView) itemView.findViewById(R.id.textView1);

        }
    }

    @Override
    public FragmentVideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_video, parent, false);
        FragmentVideoRecyclerAdapter.FragmentVideoViewHolder fvvh=new FragmentVideoRecyclerAdapter.FragmentVideoViewHolder(v);
        return fvvh;
    }

    @Override
    public void onBindViewHolder(FragmentVideoViewHolder holder, final int position) {
        holder.txtViewVideo.setText(newsVideoArrayList.get(position).getHeader());
    }

    @Override
    public int getItemCount() {
        return newsVideoArrayList.size();
    }


}
