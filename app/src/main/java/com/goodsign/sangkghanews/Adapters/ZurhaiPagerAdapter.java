package com.goodsign.sangkghanews.adapters;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.goodsign.sangkghanews.fragments.Zurkhay;
import com.goodsign.sangkghanews.handlers.HttpRequestHandler;
import com.goodsign.sangkghanews.handlers.JsonParser;
import com.goodsign.sangkghanews.models.ZurkhayModel;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Машка on 22.01.2017.
 */

public class ZurhaiPagerAdapter extends FragmentStatePagerAdapter
{
    private ArrayList<ZurkhayModel> zurkhayList;
    private ArrayList<Zurkhay> zurkhayFragmentsList;

    public ZurhaiPagerAdapter(FragmentManager fm, ArrayList<Zurkhay> zurkhayFragmentsList)
    {
        super(fm);
        this.zurkhayFragmentsList = zurkhayFragmentsList;
    }

    @Override
    public Fragment getItem(int position)
    {
        if (zurkhayFragmentsList.size() > position)
        {
            return zurkhayFragmentsList.get(position);
        }
        else
        {
            return null;
        }
    }

    @Override
    public int getCount() {
        return zurkhayFragmentsList.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        if (zurkhayFragmentsList != null)
        {
            if (zurkhayFragmentsList.get(position) != null)
            {
                return zurkhayFragmentsList.get(position).getDateFromModel();
            }else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }

    public void updateList(ArrayList<Zurkhay> zurkhayFragmentsList)
    {
        this.zurkhayFragmentsList = zurkhayFragmentsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
