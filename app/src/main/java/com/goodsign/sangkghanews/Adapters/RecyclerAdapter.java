package com.goodsign.sangkghanews.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodsign.sangkghanews.Models.NewsElement;
import com.goodsign.sangkghanews.R;

import java.util.ArrayList;

/**
 * Created by Машка on 14.12.2016.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<NewsElement> newsElementArrayList;
    // private RealmResults<RealmData> Results;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView;
        TextView txtView;
        TextView txtView2;


        public ViewHolder(View itemView) {
            super(itemView);

            imgView = (ImageView) itemView.findViewById(R.id.imageView1);
            txtView = (TextView) itemView.findViewById(R.id.textView1);
            txtView2 = (TextView) itemView.findViewById(R.id.textView2);
        }
    }

    public RecyclerAdapter(ArrayList<NewsElement> newsElementArrayList, Context context) {
        this.context = context;
        this.newsElementArrayList = newsElementArrayList;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_element, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(final RecyclerAdapter.ViewHolder holder, final int position) {
        holder.txtView.setText(newsElementArrayList.get(position).getHeader());
        holder.txtView2.setText(newsElementArrayList.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return newsElementArrayList.size();
    }
}
