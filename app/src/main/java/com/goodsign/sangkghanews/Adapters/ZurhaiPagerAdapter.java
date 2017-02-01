package com.goodsign.sangkghanews.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.goodsign.sangkghanews.Fragment.ZurhaiList;

import java.util.ArrayList;

/**
 * Created by Машка on 22.01.2017.
 */

public class ZurhaiPagerAdapter extends FragmentStatePagerAdapter {
    private FragmentManager parentManager;
    private ArrayList<ZurhaiList> zurhaiLists;

    public ZurhaiPagerAdapter(FragmentManager fm, FragmentManager parentManager) {
        super(fm);
        this.parentManager = parentManager;
        zurhaiLists = new ArrayList<>();
        for (int i = 0; i < 7; i++)
        {
            zurhaiLists.add(new ZurhaiList());
        }
    }

    @Override
    public Fragment getItem(int position) {
        return zurhaiLists.get(position);
    }

    @Override
    public int getCount() {
        return zurhaiLists.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position)
        {
            case 0:
                title ="пн";
                break;
            case 1:
                title = "вт";
                break;
            case 2:
                title = "ср";
                break;
            case 3:
                title = "чт";
                break;
            case 4:
                title = "пт";
                break;
            case 5:
                title = "сб";
                break;
            case 6:
                title = "вс";
                break;
        }
        return title;
    }
}
