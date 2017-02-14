package com.goodsign.sangkghanews.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodsign.sangkghanews.fragments.PhotoList;
import com.goodsign.sangkghanews.handlers.HttpRequestHandler;
import com.goodsign.sangkghanews.models.AlbumModel;
import com.goodsign.sangkghanews.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

import java.util.ArrayList;

/**
 * Created by Машка on 17.01.2017.
 */

public class AlbumListRecyclerAdapter extends RecyclerView.Adapter<AlbumListRecyclerAdapter.AlbumListViewHolder>{
    private Context context;
    private ArrayList<AlbumModel> albumList;
    private FragmentManager fragmentManager;
    private DisplayImageOptions displayImageOptions;

    public AlbumListRecyclerAdapter(ArrayList<AlbumModel> albumList, FragmentManager fragmentManager)
    {
        this.fragmentManager = fragmentManager;
        this.albumList = albumList;
        displayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.video_stub)
                .showImageForEmptyUri(R.drawable.video_stub)
                .showImageOnFail(R.drawable.video_stub)
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .build();
    }
    public class AlbumListViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView description;
        ImageView photo;
        LinearLayout linearLayout;
        public AlbumListViewHolder(View itemView) {
            super(itemView);
            photo = (ImageView) itemView.findViewById(R.id.image_album);
            title = (TextView) itemView.findViewById(R.id.text_album_title);
            description = (TextView) itemView.findViewById(R.id.text_album_description);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.layout_album);
        }
    }

    @Override
    public AlbumListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_album,parent,false);
        AlbumListViewHolder pvh = new AlbumListViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(AlbumListViewHolder holder, final int position) {
//        holder.photo_null.setText(R.string.test_short);
//        holder.photo_name.setText(R.string.test_short);
        String image_url = HttpRequestHandler.getInstance().getAbsoluteUrl(albumList.get(position).getImage_url());
        ImageLoader.getInstance().displayImage(image_url, holder.photo, displayImageOptions);
        String title = "";
        if (albumList.get(position).getTitle().length()> 16)
        {
            title = albumList.get(position).getTitle().substring(0, 16) + "...";
        }
        else
        {
            title = albumList.get(position).getTitle();
        }
        holder.title.setText(title);
//        holder.description.setText(albumList.get(position).getDecription());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = PhotoList.newInstance(albumList.get(position));
                fragmentManager.beginTransaction().replace(R.id.container,fragment).addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public void updateList(ArrayList<AlbumModel> albumList)
    {
        this.albumList = albumList;
        notifyDataSetChanged();
    }

}
