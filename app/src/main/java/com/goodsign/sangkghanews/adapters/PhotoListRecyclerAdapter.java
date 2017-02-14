package com.goodsign.sangkghanews.adapters;

//TODO почистить импорты
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.goodsign.sangkghanews.R;
import com.goodsign.sangkghanews.fragments.PhotoGallery;
import com.goodsign.sangkghanews.fragments.PhotoList;
import com.goodsign.sangkghanews.handlers.HttpRequestHandler;
import com.goodsign.sangkghanews.models.PhotoModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;

/**
 * Created by Машка on 21.01.2017.
 */

public class PhotoListRecyclerAdapter extends RecyclerView.Adapter<PhotoListRecyclerAdapter.PhotoListRecyclerViewHolder>
{
    private ArrayList<PhotoModel> photoList;
    private DisplayImageOptions displayImageOptions;
    private FragmentManager fragmentManager;

    public PhotoListRecyclerAdapter(ArrayList<PhotoModel> photoList, FragmentManager fragmentManager)
    {
        this.photoList = photoList;
        this.fragmentManager = fragmentManager;
        displayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.video_stub)
                .showImageForEmptyUri(R.drawable.video_stub)
                .showImageOnFail(R.drawable.video_stub)
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .build();
    }

    public static class PhotoListRecyclerViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        LinearLayout layout;
        public PhotoListRecyclerViewHolder(View itemView)
        {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image_photo);
            layout = (LinearLayout) itemView.findViewById(R.id.layout_photo);
        }
    }

    @Override
    public PhotoListRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_photo, parent, false);
        PhotoListRecyclerViewHolder plrvh = new PhotoListRecyclerViewHolder(v);
        return plrvh;
    }

    @Override
    public void onBindViewHolder(PhotoListRecyclerViewHolder holder, final int position)
    {
        ImageLoader.getInstance().displayImage(HttpRequestHandler.getInstance().getAbsoluteUrl(photoList.get(position).getImage()),
                holder.image, displayImageOptions);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                PhotoGallery fragment = PhotoGallery.newInstance(photoList, position);
                fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return photoList.size();
    }

    public void updateList(ArrayList<PhotoModel> photoList)
    {
        this.photoList = photoList;
        notifyDataSetChanged();
    }
}
