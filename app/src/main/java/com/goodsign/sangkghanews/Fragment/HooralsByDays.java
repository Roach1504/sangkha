package com.goodsign.sangkghanews.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodsign.sangkghanews.Adapters.HooralsPagerAdapter;
import com.goodsign.sangkghanews.R;

/**
 * Created by Roman on 18.01.2017.
 */

public class HooralsByDays extends Fragment
{
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static HooralsByDays newInstance()
    {
        return new HooralsByDays();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_list_hoorals, container, false);

        tabLayout = (TabLayout) view.findViewById(R.id.layout_hoorals);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager_hoorals);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        FragmentManager manager = getChildFragmentManager();
        FragmentManager parentManager = getFragmentManager();
        HooralsPagerAdapter pagerAdapter = new HooralsPagerAdapter(manager, parentManager);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}
