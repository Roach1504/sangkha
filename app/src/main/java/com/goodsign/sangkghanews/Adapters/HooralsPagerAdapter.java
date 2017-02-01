package com.goodsign.sangkghanews.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.goodsign.sangkghanews.Fragment.HooralsList;

import java.util.ArrayList;

/**
 * Created by Roman on 18.01.2017.
 */

public class HooralsPagerAdapter extends FragmentStatePagerAdapter
{
    private FragmentManager parentManager;
    private ArrayList<HooralsList> hooralsList;

    public HooralsPagerAdapter(FragmentManager fm, FragmentManager parentManager)
    {
        super(fm);
        this.parentManager = parentManager;
        hooralsList = new ArrayList<>();
        for (int i = 0; i < 7; i++)
        {
            hooralsList.add(new HooralsList());
        }
    }

    @Override
    public Fragment getItem(int position) {
//        Log.e("POSITION", position + "");
        switch (position)
        {
//            case 0:
//                f = DialogueListFragment.newInstance(parentManager);
//                break;
//            case 1:
//                f = ChatListFragment.newInstance(parentManager);
//                break;
//            case 2:
//                f = AnnouncementListFragment.newInstance(parentManager);
//                break;
//            case 3:
//                f = SupportListFragment.newInstance(parentManager);
//                break;
        }
        return hooralsList.get(position);
    }

    @Override
    public int getCount() {
        return hooralsList.size();
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
