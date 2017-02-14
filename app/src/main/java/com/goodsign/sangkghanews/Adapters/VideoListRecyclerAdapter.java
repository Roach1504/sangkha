package com.goodsign.sangkghanews.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodsign.sangkghanews.models.VideoModel;
import com.goodsign.sangkghanews.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;

/**
 * Created by Машка on 17.12.2016.
 */

public class VideoListRecyclerAdapter extends RecyclerView.Adapter<VideoListRecyclerAdapter.FragmentVideoViewHolder>
{
    private Context context;
    private ArrayList<VideoModel> videoList;
    private DisplayImageOptions displayImageOptions;

    public VideoListRecyclerAdapter(ArrayList<VideoModel> videoList, Context context)
    {
        this.context = context;
        this.videoList = videoList;
        displayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.video_stub)
                .showImageForEmptyUri(R.drawable.video_stub)
                .showImageOnFail(R.drawable.video_stub)
                .cacheInMemory(true)
                .cacheOnDisk(false)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .build();
    }

    public static class FragmentVideoViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title;
        LinearLayout linearLayout;

        public FragmentVideoViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageView1);
            title = (TextView) itemView.findViewById(R.id.textView1);
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
        holder.title.setText(videoList.get(position).getTitle());
        final String video_url = videoList.get(position).getVideo_url();
        String video_id = video_url.substring(video_url.lastIndexOf("/")+1, video_url.length());
        Log.e("Index", video_id);
        ImageLoader.getInstance().displayImage("http://img.youtube.com/vi/"+video_id+"/default.jpg", holder.image);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent browseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(video_url));
                browseIntent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(browseIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public void updateList(ArrayList<VideoModel> videoList)
    {
        this.videoList = videoList;
        notifyDataSetChanged();
    }
}
