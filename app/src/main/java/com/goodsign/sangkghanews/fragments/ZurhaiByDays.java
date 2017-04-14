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
//    private ArrayList<ZurkhayModel> zurkhayList;
    private ArrayList<Zurkhay> zurkhayFragmentsList;

    private SwipeRefreshLayout refreshLayout; //отвечает за обновление списка  загрузки инфы с сервера
    private Snackbar noConnectionSnackbar; //Выводится, если нет подключения к интернету
    private LinearLayout layout;

    private Callback lastZurhaiCallback;
    private boolean isLoading, isTwice; //отвечает за проверку, идет ли сейчас загрузка данных с сервера

    public static ZurhaiByDays newInstance()
    {
        return new ZurhaiByDays();
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
                                loadLastZurhai();
                            }
                        });
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        isLoading = false;
//        zurkhayList = new ArrayList<>();
        zurkhayFragmentsList = new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_list_zurhai, container, false);
        layout = (LinearLayout) view.findViewById(R.id.layout_zurkhais);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_refresh_zurhai);
        refreshLayout.setColorSchemeResources(R.color.accent);
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
        /*Callback callback = new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {

                zurkhayList = JsonParser.getZurkhaisArrayFromJson(response.body().string());
                Log.e("EKEKEKE", "KEKEKEKE");
                if (zurkhayList != null && zurkhayList.size() > 0)
                {
                    if (zurkhayFragmentsList.size() > 0)
                    {
                        zurkhayFragmentsList.clear();
                    }
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
        HttpRequestHandler.getInstance().post("/api/zurkhay");*/

        lastZurhaiCallback = new Callback()
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
                if (!isTwice)
                {
                   ArrayList<ZurkhayModel> zurkhayList = JsonParser.getZurkhaisArrayFromJson(response.body().string());
//                    Log.e("EKEKEKE", "KEKEKEKE");
                    if (zurkhayList != null)
                    {
                        for (int i = 0; i < zurkhayList.size(); i++)
                        {
                            zurkhayFragmentsList.add(Zurkhay.newInstance(zurkhayList.get(i)));
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshLayout.setRefreshing(false);
                                //Занимательное костылирование.
                                // Без этой фичи ловится странный баг с отображением после возврата фрагмента из backstack
                                pagerAdapter.updateList(zurkhayFragmentsList);
                                pagerAdapter.updateList(zurkhayFragmentsList);
                            }
                        });
                    }
                    isLoading = false; // отмечаем, что загрузка окончена
                    isTwice = true;
                }
            }
        };

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                loadLastZurhai(); //загружаем последние новости
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
                loadLastZurhai();
            }
        });
    }

    @Override
    public void onFragmentResume()
    {
        isTwice  = false;
//        zurkhayList.clear();
        zurkhayFragmentsList.clear();
        loadLastZurhai();
//        HttpRequestHandler.getInstance().setCallback(lastZurhaiCallback); //устанавливаем, какой коллбэк получит ответ сервера
//        HttpRequestHandler.getInstance().post("/api/zurkhay"); //запрашиваем данные (в ответ всегда придет первые 20 ответов (1 страница))
//        pagerAdapter.updateList(zurkhayFragmentsList);
//        refreshLayout.post(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                //если не выполнить setRefreshing, не сработает отображение загрузки
//                refreshLayout.setRefreshing(true);
//                loadLastZurhai();
//            }
//        });
    }

    private void loadLastZurhai()
    {
//        if (isNetworkAvailable())
//        {
            isTwice = false;
            isLoading = true; // ставим метку, что идет загрузка
            zurkhayFragmentsList.clear();
            HttpRequestHandler.getInstance().setCallback(lastZurhaiCallback); //устанавливаем, какой коллбэк получит ответ сервера
            HttpRequestHandler.getInstance().post("/api/zurkhay"); //запрашиваем данные (в ответ всегда придет первые 20 ответов (1 страница))
//        }
//        else
//        {
//
//        }
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
