package com.goodsign.sangkghanews.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodsign.sangkghanews.R;

/**
 * Created by Roman on 22.02.2017.
 */

public class AboutDevelopers extends Fragment implements BackStackResumedFragment
{

    public static AboutDevelopers newInstance()
    {
        return new AboutDevelopers();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_developers, container, false);
        return view;
    }

    @Override
    public void onFragmentResume() {

    }
}
