package com.goodsign.sangkghanews.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodsign.sangkghanews.Fragment.PhotoList;
import com.goodsign.sangkghanews.Models.AlbumModel;
import com.goodsign.sangkghanews.R;

import java.util.ArrayList;

/**
 * Created by Машка on 17.01.2017.
 */

public class AlbumListRecyclerAdapter extends RecyclerView.Adapter<AlbumListRecyclerAdapter.PhotoViewHolder>{
    private Context context;
    private ArrayList<AlbumModel> albumList;
    private FragmentManager fragmentManager;

    public AlbumListRecyclerAdapter(ArrayList<AlbumModel> albumList, FragmentManager fragmentManager)
    {
        this.fragmentManager = fragmentManager;
        this.albumList = albumList;
    }
    public class PhotoViewHolder extends RecyclerView.ViewHolder{
        TextView photo_name;
        TextView photo_null;
        ImageView photo;
        LinearLayout linearLayout;
        public PhotoViewHolder(View itemView) {
            super(itemView);
            photo = (ImageView) itemView.findViewById(R.id.image_album);
            photo_name = (TextView) itemView.findViewById(R.id.text_album_name);
            photo_null = (TextView) itemView.findViewById(R.id.text_album_null);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.album_layout);
        }
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_album,parent,false);
        PhotoViewHolder pvh = new PhotoViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, final int position) {
//        holder.photo_null.setText(R.string.test_short);
//        holder.photo_name.setText(R.string.test_short);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = PhotoList.newInstance();
                fragmentManager.beginTransaction().replace(R.id.container,fragment).addToBackStack(null).commit();


            }
        });

    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }



}
