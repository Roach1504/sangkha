package com.goodsign.sangkghanews.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.goodsign.sangkghanews.fragments.Lecture;
import com.goodsign.sangkghanews.handlers.HttpRequestHandler;
import com.goodsign.sangkghanews.models.LectureModel;
import com.goodsign.sangkghanews.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;

/**
 * Created by Roman on 10.01.2017.
 */

public class LecturesListRecyclerAdapter extends RecyclerView.Adapter<LecturesListRecyclerAdapter.LecturesListViewHolder>
{
    private FragmentManager fragmentManager;
    private ArrayList<LectureModel> lecturesList;
    private DisplayImageOptions displayImageOptions;

    public LecturesListRecyclerAdapter(ArrayList<LectureModel> lecturesList, FragmentManager fragmentManager)
    {
        this.fragmentManager = fragmentManager;
        this.lecturesList = lecturesList;
        displayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.video_stub)
                .showImageForEmptyUri(R.drawable.video_stub)
                .showImageOnFail(R.drawable.video_stub)
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .build();
    }

    public static class LecturesListViewHolder extends RecyclerView.ViewHolder
    {
        private MaterialRippleLayout lecture_layout;
        private TextView title;
        private TextView annotation;
        private TextView date;
        private ImageView image;

        public LecturesListViewHolder(View itemView)
        {
            super(itemView);
            lecture_layout = (MaterialRippleLayout) itemView.findViewById(R.id.layout_recyclerview_lecture);
            title = (TextView) itemView.findViewById(R.id.text_lecture_title);
            annotation = (TextView) itemView.findViewById(R.id.text_lecture_annotation);
            date = (TextView) itemView.findViewById(R.id.text_lecture_date);
            image = (ImageView) itemView.findViewById(R.id.image_lecture);
        }
    }

    @Override
    public LecturesListRecyclerAdapter.LecturesListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_lecture, parent, false);
        LecturesListRecyclerAdapter.LecturesListViewHolder llvh = new LecturesListRecyclerAdapter.LecturesListViewHolder(v);
        return  llvh;
    }

    @Override
    public void onBindViewHolder(LecturesListRecyclerAdapter.LecturesListViewHolder holder, final int position)
    {
        holder.title.setText(lecturesList.get(position).getTitle());
        String image_url = lecturesList.get(position).getImage();
        if (!image_url.equals(""))
        {
            if (image_url.contains("uploads") && (!image_url.contains("http") || !image_url.contains("https")))
            {
                ImageLoader.getInstance().displayImage(HttpRequestHandler.getInstance().getAbsoluteUrl(image_url), holder.image, displayImageOptions);
            }
            else
            {
                ImageLoader.getInstance().displayImage(image_url, holder.image, displayImageOptions);
            }

        }
        else
        {
            holder.image.setVisibility(View.GONE);
        }
        holder.lecture_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = Lecture.newInstance(lecturesList.get(position));
                fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lecturesList.size();
    }

    public void updateList(ArrayList<LectureModel> lecturesList)
    {
        this.lecturesList = lecturesList;
        notifyDataSetChanged();
    }

}
