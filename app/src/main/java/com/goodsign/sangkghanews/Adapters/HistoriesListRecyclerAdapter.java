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
import com.goodsign.sangkghanews.fragments.History;
import com.goodsign.sangkghanews.handlers.HttpRequestHandler;
import com.goodsign.sangkghanews.models.HistoryModel;
import com.goodsign.sangkghanews.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;

/**
 * Created by Roman on 15.01.2017.
 */

public class HistoriesListRecyclerAdapter extends RecyclerView.Adapter<HistoriesListRecyclerAdapter.HistoriesListViewHolder>
{

    private ArrayList<HistoryModel> historiesList;
    private FragmentManager fragmentManager;
    private DisplayImageOptions displayImageOptions;

    public static class HistoriesListViewHolder extends RecyclerView.ViewHolder
    {

        private MaterialRippleLayout history_layout;
        private TextView title;
        private TextView annotation;
        private TextView date;
        private ImageView image;

        public HistoriesListViewHolder(View itemView)
        {
            super(itemView);
            history_layout = (MaterialRippleLayout) itemView.findViewById(R.id.layout_recyclerview_history);
            title = (TextView) itemView.findViewById(R.id.text_history_name);
            annotation = (TextView) itemView.findViewById(R.id.text_history_annotation);
            date = (TextView) itemView.findViewById(R.id.text_history_date);
            image = (ImageView) itemView.findViewById(R.id.image_history);
        }
    }

    public HistoriesListRecyclerAdapter(ArrayList<HistoryModel> historiesList, FragmentManager fragmentManager)
    {
        this.fragmentManager = fragmentManager;
        this.historiesList = historiesList;
        displayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.video_stub)
                .showImageForEmptyUri(R.drawable.video_stub)
                .showImageOnFail(R.drawable.video_stub)
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .build();
    }

    @Override
    public HistoriesListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_history, parent, false);
        HistoriesListRecyclerAdapter.HistoriesListViewHolder hlvh = new HistoriesListRecyclerAdapter.HistoriesListViewHolder(v);
        return hlvh;
    }

    @Override
    public void onBindViewHolder(HistoriesListViewHolder holder, final int position)
    {
        /*String s2 = null;
        if(historiesList.get(position).getName().length()>50)
        {
            s2 = historiesList.get(position).getName().substring(0,50);
            holder.title.setText(s2+"...");
        }
        else
        {
            holder.title.setText(historiesList.get(position).getName());
        }
        String s = null;
        if(historiesList.get(position).getAnnotation().length()>100)
        {
            s = historiesList.get(position).getAnnotation().substring(0,100);
            holder.annotation.setText(s+"...");
        }
        else
        {
            holder.annotation.setText(historiesList.get(position).getAnnotation());
        }*/
        holder.title.setText(historiesList.get(position).getTitle());
        String image_url = historiesList.get(position).getImage();
        //TODO сделать подобную проверку для всех вызовов imageloader
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
//        ImageLoader.getInstance().displayImage(HttpRequestHandler.getInstance().getAbsoluteUrl("/uploads/fb60f5284700f2e67bc7c702878bfef1.jpg"), holder.image, displayImageOptions);
        holder.annotation.setText(historiesList.get(position).getAnnotation());
        holder.date.setText(historiesList.get(position).getDate());
        holder.history_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = History.newInstance(historiesList.get(position));
                fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.container, fragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return historiesList.size();
    }

    public void updateList(ArrayList<HistoryModel> historiesList)
    {
        this.historiesList = historiesList;
        notifyDataSetChanged();
    }

}
