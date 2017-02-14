package com.goodsign.sangkghanews;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Roman on 02.02.2017.
 */

public class SangkhaApp extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
    }
}
