package com.goodsign.sangkghanews.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
    private ArrayList<HooralModel> previousResponseList;
    private Callback hooralsCallback;
    private HooralsPagerAdapter pagerAdapter;

    private SwipeRefreshLayout refreshLayout; //отвечает за обновление списка  загрузки инфы с сервера
    private Snackbar noConnectionSnackbar; //Выводится, если нет подключения к интернету
    private LinearLayout layout;

    private Boolean isTwice;
    private int pageNum; //отвечает за загрузку определенной страницы (одна страница = 20 записей)
    private boolean isLoading; //отвечает за проверку, идет ли сейчас загрузка данных с сервера

    public static HooralsByDays newInstance()
    {
        return new HooralsByDays();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        noConnectionSnackbar = Snackbar.make(layout, R.string.no_connection, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.refresh, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        noConnectionSnackbar.dismiss();
                        refreshLayout.post(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                //если не выполнить setRefreshing, не сработает отображение загрузки
                                refreshLayout.setRefreshing(true);
                                pageNum = 1;
                                recursiveLoad(pageNum);
                            }
                        });
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        isTwice = false;
        isLoading = false;
        pageNum = 1;

        View view = inflater.inflate(R.layout.fragment_list_hoorals, container, false);
        layout = (LinearLayout) view.findViewById(R.id.layout_hoorals);

        tabLayout = (TabLayout) view.findViewById(R.id.layout_tab_hoorals);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager_hoorals);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_refresh_hoorals);
        refreshLayout.setColorSchemeResources(R.color.accent);

        hooralsList = new ArrayList<>();
        hooralsFragmentsArrayList = new ArrayList<>();

        FragmentManager manager = getChildFragmentManager();
        final FragmentManager parentManager = getFragmentManager();
        pagerAdapter = new HooralsPagerAdapter(manager, hooralsFragmentsArrayList, tabLayout);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        hooralsCallback = new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        noConnectionSnackbar.show();
                        refreshLayout.setRefreshing(false);
                        isLoading = false;
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                //TODO исправить костыль, это хреновое решение
                if (!isTwice)
                {
                    String s = response.body().string();
//                    Log.e("HOORALS", s);
//                    Log.e("PAGE NUMBER", pageNum+"");
                    ArrayList<HooralModel> responseList = JsonParser.getHooralsArrayFromJson(s);
                    if (responseList != null && responseList.size() > 0)
                    {
                        if (previousResponseList == null || !contains(previousResponseList, responseList.get(0)))
                        {
                            Boolean isAdded = false;
                            for (int i = 0; i < responseList.size(); i++) {
                                if (hooralsList.size() == 0) {
                                    ArrayList<HooralModel> arrayList = new ArrayList<>();
                                    arrayList.add(responseList.get(i));
                                    hooralsList.add(arrayList);
                                } else {
                                    for (int j = 0; j < hooralsList.size(); j++) {
                                        if (responseList.get(i).getDate().equals(
                                                hooralsList.get(j).get(0).getDate())) {
                                            hooralsList.get(j).add(responseList.get(i));
                                            isAdded = true;
                                            break;
                                        }
                                    }
                                    if (!isAdded) {
                                        ArrayList<HooralModel> arrayList = new ArrayList<>();
                                        arrayList.add(responseList.get(i));
                                        hooralsList.add(arrayList);
                                    } else {
                                        isAdded = false;
                                    }
                                }
                            }

                            for (int i = 0; i < hooralsList.size(); i++) {
                                hooralsFragmentsArrayList.add(HooralsList.newInstance(parentManager, hooralsList.get(i)));
                            }
                            previousResponseList = responseList;
                            pageNum++;
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pagerAdapter.updateList(hooralsFragmentsArrayList);
                                }
                            });
                            recursiveLoad(pageNum);
                        }
                        else
                        {
                            pageNum = 1;
                        }
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.setRefreshing(false);
                        }
                    });
                    isTwice = true;
                }
            }
        };

        /**
         * чтобы вызвать onRefresh, надо пролистать в самый верх списка и потянуть его вниз
         * вылезет такая херь вращающаяся
         * думаю, ты сразу поймешь, что это
         */
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
//                loadHoorals(); //загружаем последние новости
                pageNum = 1;
                recursiveLoad(pageNum);
            }
        });

        /**
         * эта функция вызывает onRefresh без необходимости тянуть вручную
         * в данном случае вызывается только при запуске фрагмента, чтобы загрузить первую страницу
         */
        refreshLayout.post(new Runnable()
        {
            @Override
            public void run()
            {
                //если не выполнить setRefreshing, не сработает отображение загрузки
                refreshLayout.setRefreshing(true);
//                loadHoorals();
                pageNum = 1;
                recursiveLoad(pageNum);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
//        HttpRequestHandler.getInstance().setCallback(hooralsCallback);
//        HttpRequestHandler.getInstance().post("/api/hurals");
    }

    @Override
    public void onFragmentResume()
    {
        isTwice = false;
        hooralsList.clear();
        hooralsFragmentsArrayList.clear();
        if (previousResponseList != null)
        previousResponseList.clear();
        pageNum = 1;
        HttpRequestHandler.getInstance().setCallback(hooralsCallback);
        recursiveLoad(pageNum);
//        HttpRequestHandler.getInstance().post("/api/hurals");
    }

    private void loadHoorals()
    {
//        if (isNetworkAvailable())
//        {
            isLoading = true; // ставим метку, что идет загрузка
            HttpRequestHandler.getInstance().setCallback(hooralsCallback); //устанавливаем, какой коллбэк получит ответ сервера
            HttpRequestHandler.getInstance().post("/api/hurals"); //запрашиваем данные (в ответ всегда придет первые 20 ответов (1 страница))
//        }
//        else
//        {

//        }
    }

    private boolean contains(ArrayList<HooralModel> list, HooralModel model)
    {
        for (HooralModel m : list)
        {
            if (m.getId() == model.getId())
            {
                Log.e("CONTAINS", "TRUE");
                return true;
            }
        }
        Log.e("CONTAINS", "FALSE");
        return false;
    }

    private void recursiveLoad(int pageNum)
    {
        isTwice = false;
        HttpRequestHandler.getInstance().setCallback(hooralsCallback);
        HttpRequestHandler.getInstance().get("/api/hurals", pageNum);
    }

    public boolean isNetworkAvailable()
    {
        ConnectivityManager manager = (ConnectivityManager)  getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (noConnectionSnackbar.isShownOrQueued())
        {
            noConnectionSnackbar.dismiss();
        }
    }
}
