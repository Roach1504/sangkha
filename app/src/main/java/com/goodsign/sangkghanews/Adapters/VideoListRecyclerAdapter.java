package com.goodsign.sangkghanews.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodsign.sangkghanews.Models.NewsVideo;
import com.goodsign.sangkghanews.R;

import java.util.ArrayList;

/**
 * Created by Машка on 17.12.2016.
 */

public class VideoListRecyclerAdapter extends RecyclerView.Adapter<VideoListRecyclerAdapter.FragmentVideoViewHolder> {
    private Context context;
    private ArrayList<NewsVideo> newsVideoArrayList;
//    private FragmentManager fragmentManager;

    public VideoListRecyclerAdapter(ArrayList<NewsVideo> newsVideoArrayList, Context context) {
        this.context = context;
        this.newsVideoArrayList = newsVideoArrayList;
//        this.fragmentManager = fragmentManager;
    }

    public static class FragmentVideoViewHolder extends RecyclerView.ViewHolder{
        ImageView imgViewVideo;
        TextView txtViewVideo;
        LinearLayout linearLayout;

        public FragmentVideoViewHolder(View itemView) {
            super(itemView);
            imgViewVideo = (ImageView) itemView.findViewById(R.id.imageView1);
            txtViewVideo = (TextView) itemView.findViewById(R.id.textView1);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.layout_video);
        }
    }

    @Override
    public FragmentVideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_video, parent, false);
        VideoListRecyclerAdapter.FragmentVideoViewHolder fvvh=new VideoListRecyclerAdapter.FragmentVideoViewHolder(v);
        return fvvh;
    }

    @Override
    public void onBindViewHolder(FragmentVideoViewHolder holder, final int position) {
        holder.txtViewVideo.setText(newsVideoArrayList.get(position).getHeader());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 /*Fragment fragment = new Video();*/
                //fragmentManager.beginTransaction().replace(R.id.container,fragment).addToBackStack(null).commit();
                Intent browseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                browseIntent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(browseIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return newsVideoArrayList.size();
    }


}
