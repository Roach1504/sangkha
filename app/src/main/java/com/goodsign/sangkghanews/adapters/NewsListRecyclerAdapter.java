package com.goodsign.sangkghanews.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodsign.sangkghanews.fragments.News;
import com.goodsign.sangkghanews.handlers.HttpRequestHandler;
import com.goodsign.sangkghanews.models.NewsModel;
import com.goodsign.sangkghanews.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.rey.material.widget.ProgressView;

import java.util.ArrayList;

/**
 * Created by Машка on 14.12.2016.
 */

public class NewsListRecyclerAdapter extends RecyclerView.Adapter<NewsListRecyclerAdapter.NewsListViewHolder>
{

    private Context context;
    private ArrayList<NewsModel> newsList;
    private FragmentManager fragmentManager;
    private LinearLayoutManager layoutManager;
    private DisplayImageOptions displayImageOptions;
    private ImageLoader imageLoader;

    public static class NewsListViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView annotation;
        TextView date;
        LinearLayout linearLayout;


        public NewsListViewHolder(View itemView) {
            super(itemView);

                image = (ImageView) itemView.findViewById(R.id.image_news);
                title = (TextView) itemView.findViewById(R.id.text_news_title);
                annotation = (TextView) itemView.findViewById(R.id.text_news_annotation);
                date = (TextView) itemView.findViewById(R.id.text_news_date);
                linearLayout = (LinearLayout) itemView.findViewById(R.id.layout_news);
        }
    }

    public NewsListRecyclerAdapter(ArrayList<NewsModel> newsList, Context context, FragmentManager fragmentManager, LinearLayoutManager layoutManager) {
        this.context = context;
        this.newsList = newsList;
        this.fragmentManager = fragmentManager;
        this.layoutManager = layoutManager;
        displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .build();
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public NewsListRecyclerAdapter.NewsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_news, parent, false);
            v.setTag(Integer.valueOf(1));
            NewsListViewHolder vh = new NewsListViewHolder(v);
            return vh;
    }

    @Override
    public void onBindViewHolder(final NewsListRecyclerAdapter.NewsListViewHolder holder, final int position)
    {
//        if (position != newsList.size())
//        {
            String s2 = null;
            if (newsList.get(position).getTitle().length() > 50) {
                s2 = newsList.get(position).getTitle().substring(0, 50);
                holder.title.setText(s2 + "...");
            } else {
                holder.title.setText(newsList.get(position).getTitle());
            }

            imageLoader.displayImage(HttpRequestHandler.getInstance().getAbsoluteUrl(newsList.get(position).getImagepath()), holder.image, displayImageOptions);

            String s = null;
            if (newsList.get(position).getAnnotation().length() > 100) {
                s = newsList.get(position).getAnnotation().substring(0, 100);
                holder.annotation.setText(s + "...");
            } else {
                holder.annotation.setText(newsList.get(position).getAnnotation());
            }

            holder.date.setText(newsList.get(position).getDate());
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = News.newInstance(newsList.get(position));
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();

                }
            });
//        }
//        else if (layoutManager.getChildCount() == layoutManager.getItemCount())
//        {
//            if (holder.progressView != null)
//            {
//                holder.progressView.setVisibility(View.GONE);
//            }
//        }
//        else
//        {
//            if (holder.progressView != null)
//            {
//                holder.progressView.setVisibility(View.VISIBLE);
//            }
//        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void updateList(ArrayList<NewsModel> newsList)
    {
        this.newsList = newsList;
        notifyDataSetChanged();
    }
}
