package com.goodsign.sangkghanews.adapters;

import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.goodsign.sangkghanews.fragments.HooralsList;
import com.goodsign.sangkghanews.handlers.HttpRequestHandler;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Roman on 18.01.2017.
 */

public class HooralsPagerAdapter extends FragmentStatePagerAdapter
{
    private ArrayList<HooralsList> hooralsList;
    private TabLayout tabLayout;

    public HooralsPagerAdapter(FragmentManager fm, ArrayList<HooralsList> hooralsList, TabLayout tabLayout)
    {
        super(fm);
        this.hooralsList = hooralsList;
        this.tabLayout = tabLayout;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public Fragment getItem(int position)
    {
        if (tabLayout != null)
        {
            if (tabLayout.getTabAt(position) != null)
            {
                tabLayout.getTabAt(position).setText(hooralsList.get(position).getDateFromModel());
            }
        }
        return hooralsList.get(position);
    }

    @Override
    public int getCount() {
        return hooralsList.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return hooralsList.get(position).getDateFromModel();
    }

    public void updateList(ArrayList<HooralsList> hooralsList)
    {
        //TODO Говнокод перед дедлайном, желательно исправить
        this.hooralsList = hooralsList;
        notifyDataSetChanged();
        for (int i = 0; i < getCount(); i++)
        {
            try
            {
                tabLayout.getTabAt(i).setText(getPageTitle(i));
            }
            catch (IndexOutOfBoundsException e)
            {
                tabLayout.addTab(tabLayout.newTab().setText(getPageTitle(i)));
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
