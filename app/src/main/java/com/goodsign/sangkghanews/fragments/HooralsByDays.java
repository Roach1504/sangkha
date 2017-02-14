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

import com.goodsign.sangkghanews.adapters.HooralsPagerAdapter;
import com.goodsign.sangkghanews.R;
import com.goodsign.sangkghanews.handlers.HttpRequestHandler;
import com.goodsign.sangkghanews.handlers.JsonParser;
import com.goodsign.sangkghanews.models.HooralModel;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Roman on 18.01.2017.
 */

public class HooralsByDays extends Fragment implements BackStackResumedFragment
{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<ArrayList<HooralModel>> hooralsList;
    private ArrayList<HooralsList> hooralsFragmentsArrayList;
    private Callback callback;
    private HooralsPagerAdapter pagerAdapter;

    private Boolean isTwice;

    public static HooralsByDays newInstance()
    {
        return new HooralsByDays();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_list_hoorals, container, false);

        isTwice = false;

        tabLayout = (TabLayout) view.findViewById(R.id.layout_hoorals);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager_hoorals);

        hooralsList = new ArrayList<>();
        hooralsFragmentsArrayList = new ArrayList<>();

        FragmentManager manager = getChildFragmentManager();
        final FragmentManager parentManager = getFragmentManager();
        pagerAdapter = new HooralsPagerAdapter(manager, hooralsFragmentsArrayList, tabLayout);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        callback = new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                //TODO исправить костыль, это хреновое решение
                if (!isTwice)
                {
                    ArrayList<HooralModel> responseList = JsonParser.getHooralsArrayFromJson(response.body().string());
                    Log.e("KEKEKEKE", "ELEKEKEKEKE");
                    if (responseList != null && responseList.size() > 0)
                    {
                        Boolean isAdded = false;
                        Log.e("RESPONSE SIZE", responseList.size() + "");
                        for (int i = 0; i < responseList.size(); i++)
                        {
                            if (hooralsList.size() == 0) {
                                ArrayList<HooralModel> arrayList = new ArrayList<>();
                                arrayList.add(responseList.get(i));
                                hooralsList.add(arrayList);
                                Log.e("KEK", "KEKEK");
                            }
                            else
                            {
                                for (int j = 0; j < hooralsList.size(); j++)
                                {
                                    Log.e("KEK", j + "");
                                    if (responseList.get(i).getDate().equals(
                                            hooralsList.get(j).get(0).getDate())) {
                                        hooralsList.get(j).add(responseList.get(i));
                                        Log.e("KOK", "ADDED");
                                        isAdded = true;
                                    break;
                                    }
                                }
                                if (!isAdded)
                                {
                                    ArrayList<HooralModel> arrayList = new ArrayList<>();
                                    arrayList.add(responseList.get(i));
                                    hooralsList.add(arrayList);
                                }
                                else
                                {
                                    isAdded = false;
                                }
                            }
                        }

                        for (int i = 0; i < hooralsList.size(); i++)
                        {
                            hooralsFragmentsArrayList.add(HooralsList.newInstance(parentManager, hooralsList.get(i)));
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run()
                            {
                                pagerAdapter.updateList(hooralsFragmentsArrayList);
                            }
                        });

                    }
                    isTwice = true;
                }
            }
        };
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        HttpRequestHandler.getInstance().setCallback(callback);
        HttpRequestHandler.getInstance().post("/api/hurals");
    }

    @Override
    public void onFragmentResume()
    {
        isTwice = false;
        hooralsList.clear();
        hooralsFragmentsArrayList.clear();
        HttpRequestHandler.getInstance().setCallback(callback);
        HttpRequestHandler.getInstance().post("api/hurals");
    }
}
