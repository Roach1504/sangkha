package com.goodsign.sangkghanews.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.goodsign.sangkghanews.R;
import com.goodsign.sangkghanews.handlers.HttpRequestHandler;
import com.goodsign.sangkghanews.models.PhotoModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by Roman on 05.02.2017.
 */

public class PhotoGalleryPagerAdapter extends PagerAdapter
{
    private ArrayList<PhotoModel> images;
    private DisplayImageOptions displayImageOptions;

    public PhotoGalleryPagerAdapter(ArrayList<PhotoModel> images)
    {
        this.images = images;

        displayImageOptions = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void updateList(ArrayList<PhotoModel> images)
    {
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.viewpager_album, container, false);
        assert view != null;
        ImageView imageView = (ImageView) view.findViewById(R.id.image_photo);
        ImageLoader.getInstance().displayImage(
                HttpRequestHandler.getInstance().getAbsoluteUrl(images.get(position).getImage()),
                imageView, displayImageOptions, new SimpleImageLoadingListener()
        {

        });
        container.addView(view);
        return view;
    }
}
