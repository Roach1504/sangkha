package com.goodsign.sangkghanews.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodsign.sangkghanews.R;
import com.goodsign.sangkghanews.adapters.PhotoGalleryPagerAdapter;
import com.goodsign.sangkghanews.models.PhotoModel;

import java.util.ArrayList;

/**
 * Created by Roman on 05.02.2017.
 */

public class PhotoGallery extends Fragment implements BackStackResumedFragment
{
    private ViewPager pager;
    private PhotoGalleryPagerAdapter pagerAdapter;

    public static PhotoGallery newInstance(ArrayList<PhotoModel> photos, int startPosition)
    {
        PhotoGallery fragment = new PhotoGallery();
        Bundle bundle = new Bundle();
        bundle.putSerializable("photos", photos);
        bundle.putInt("startPosition", startPosition);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager_album, container, false);
        pager = (ViewPager) view.findViewById(R.id.viewPager_albums);
        ArrayList<PhotoModel> images = (ArrayList<PhotoModel>)  getArguments().getSerializable("photos");
        int startPosition = getArguments().getInt("startPosition");
        pagerAdapter = new PhotoGalleryPagerAdapter(images);
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(startPosition);

        return view;
    }

    @Override
    public void onFragmentResume()
    {

    }
}
