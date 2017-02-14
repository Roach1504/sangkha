package com.goodsign.sangkghanews.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodsign.sangkghanews.adapters.ZurhaiPagerAdapter;
import com.goodsign.sangkghanews.R;
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

public class ZurhaiByDays extends Fragment implements BackStackResumedFragment
{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ZurhaiPagerAdapter pagerAdapter;
    private ArrayList<ZurkhayModel> zurkhayList;
    private ArrayList<Zurkhay> zurkhayFragmentsList;

    public static ZurhaiByDays newInstance()
    {
        return new ZurhaiByDays();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        zurkhayList = new ArrayList<>();
        zurkhayFragmentsList = new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_list_zurhai, container, false);

        tabLayout = (TabLayout) view.findViewById(R.id.layout_zurhai);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager_zurhai);
        FragmentManager parentManager = getFragmentManager();
        pagerAdapter = new ZurhaiPagerAdapter(parentManager, zurkhayFragmentsList);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        Callback callback = new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                zurkhayList = JsonParser.getZurkhaisArrayFromJson(response.body().string());
                Log.e("EKEKEKE", "KEKEKEKE");
                if (zurkhayList != null && zurkhayList.size() > 0)
                {
                    for (int i = 0; i < zurkhayList.size(); i++)
                    {
                        zurkhayFragmentsList.add(Zurkhay.newInstance(zurkhayList.get(i)));
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run()
                        {
                            pagerAdapter.updateList(zurkhayFragmentsList);
                        }
                    });
                }
            }
        };
        HttpRequestHandler.getInstance().setCallback(callback);
        HttpRequestHandler.getInstance().post("/api/zurkhay");
    }

    @Override
    public void onFragmentResume()
    {
        Callback callback = new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                zurkhayList = JsonParser.getZurkhaisArrayFromJson(response.body().string());
                if (zurkhayList != null && zurkhayList.size() > 0)
                {
                    for (int i = 0; i < zurkhayList.size(); i++)
                    {
                        zurkhayFragmentsList.add(Zurkhay.newInstance(zurkhayList.get(i)));
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run()
                        {
                            pagerAdapter.updateList(zurkhayFragmentsList);
                        }
                    });
                }
                Log.e("ZURKHAY", "RESUMED");
            }
        };
        zurkhayFragmentsList.clear();
        HttpRequestHandler.getInstance().setCallback(callback);
        HttpRequestHandler.getInstance().post("/api/zurkhay");
    }
}
