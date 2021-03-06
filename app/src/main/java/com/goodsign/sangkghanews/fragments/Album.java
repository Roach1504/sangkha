package com.goodsign.sangkghanews.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.LinearLayout;

import com.goodsign.sangkghanews.adapters.AlbumListRecyclerAdapter;
import com.goodsign.sangkghanews.handlers.HttpRequestHandler;
import com.goodsign.sangkghanews.handlers.JsonParser;
import com.goodsign.sangkghanews.models.AlbumModel;
import com.goodsign.sangkghanews.R;
import com.rey.material.widget.ProgressView;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Машка on 19.12.2016.
 */

public class Album extends Fragment implements BackStackResumedFragment {
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private AlbumListRecyclerAdapter albumListRecyclerAdapter;

    private SwipeRefreshLayout refreshLayout; //отвечает за обновление списка  загрузки инфы с сервера
    private LinearLayout progressLayout;    //хранит ProgressView
    private ProgressView progressView;  //Отображает загрузку старых новостей
    private Snackbar noConnectionSnackbar; //Выводится, если нет подключения к интернету
    private LinearLayout layout;

    private ArrayList<AlbumModel> albumList;
    private ArrayList<AlbumModel> responseList, previousResponseList;
    private Callback lastNewsCallback, previousNewsCallback;
    private int pageNum; //отвечает за загрузку определенной страницы (одна страница = 20 записей)
    private boolean isLoading; //отвечает за проверку, идет ли сейчас загрузка данных с сервера

    public static Album newInstance()
    {
        return new Album();
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
                                loadLastAlbums();
                            }
                        });
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_album, container,false);

        layout = (LinearLayout) view.findViewById(R.id.layout_album);
        pageNum = 2;
        isLoading = false;
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_refresh_album);
        refreshLayout.setColorSchemeResources(R.color.accent);
        recyclerView = (RecyclerView) view.findViewById(R.id.frag_album);
        progressLayout = (LinearLayout) view.findViewById(R.id.layout_progress_album);
        progressView = (ProgressView) view.findViewById(R.id.progressViewAlbum);
        //Отключаем отображение загрузки
        progressLayout.setVisibility(View.GONE);
        progressView.setVisibility(View.GONE);

        albumList = new ArrayList<>();
        albumListRecyclerAdapter = new AlbumListRecyclerAdapter(albumList,getFragmentManager());
        gridLayoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(albumListRecyclerAdapter);

        /**
         * Этот коллбэк отвечает за обработку свежих новостей
         */
        lastNewsCallback = new Callback() {
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
                responseList = JsonParser.getAlbumsArrayFromJson(response.body().string()); //парсим ответ
                if (responseList != null && responseList.size()>0) //проверяем, не пуст ли он
                {
                    pageNum = 2; //сбрасываем страницу
                    previousResponseList = responseList; //запоминаем ответ с сервера
                    albumList = responseList; // загружаем инфу в список новостей
                    albumList.trimToSize();  //магия
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.setRefreshing(false); //выключаем refresh
//                            progress.setVisibility(View.GONE);
                            albumListRecyclerAdapter.updateList(albumList); //обновляем список
                        }
                    });
                }
                isLoading = false; // отмечаем, что загрузка окончена
                recursiveAlbumLoad(pageNum);
            }
        };

        /**
         * Этот коллбэк отвечает за загрузку старых новостей
         */
        previousNewsCallback = new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                isLoading = false; // отмечаем, что загрузка окончена
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException
            {
                responseList = JsonParser.getAlbumsArrayFromJson(response.body().string());
                /**
                 * функция contains отвечает за проверку того, какая страница пришла в ответ
                 * вся соль в том, что если на сервере больше нет информации, он присылает последнюю существующую страницу
                 * contains проверяет, совпадают ли страницы
                 */
                if (previousResponseList == null || !contains(previousResponseList, responseList.get(0)))
                //если лист существует и не совпадает с предыдущим запросом
                {
                    previousResponseList = responseList; //перезаписываем предыдущий запрос
                    albumList.addAll(responseList); //добавляем полученные элементы в список
                    pageNum++; //изменяем число для того, чтобы загрузить следующую страницу
                    /**
                     * поскольку работать с интерфейсом приложения можно только в GUI-потоке
                     * нам надо получить к нему доступ
                     */
                    getActivity().runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run() {
                            Log.e("LIST", "UPDATED"); //без комментариев
                            albumListRecyclerAdapter.updateList(albumList); //обновляем список
                        }
                    });
                    recursiveAlbumLoad(pageNum);
                }
                isLoading = false;
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
                if (noConnectionSnackbar.isShown())
                {
                    noConnectionSnackbar.dismiss();
                }
                loadLastAlbums(); //загружаем последние новости
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
                loadLastAlbums();
            }
        });

        /**
         * OnScrollListener отвечает за обработку прокрутки списка пользователем
         */
        /*recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = gridLayoutManager.getChildCount();//смотрим сколько элементов на экране
                int totalItemCount = gridLayoutManager.getItemCount();//сколько всего элементов
                int firstVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();//какая позиция первого видимого элемента


                if ((visibleItemCount + firstVisibleItems) >= totalItemCount && visibleItemCount < totalItemCount)
                //если кол-во видимых и номер первого видимого элемента >= общему числу
                // и если число видимых меньше общего числа
                {
                    if (!isLoading) //если ничего не загружается с сервера
                    {
                        Log.e("SCROLL", "LOAD");
                        loadPreviousNews(pageNum); // загружаем с сервера страницу по номеру
                    }
                    else // если уже идет загрузка
                    {
                        if (dy > 0) // если листаем вниз
                        {
                            progressLayout.setVisibility(View.VISIBLE);
                            progressView.setVisibility(View.VISIBLE);
                        }
                        else // если листаем вверх
                        {
                            progressLayout.setVisibility(View.GONE);
                            progressView.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });*/

        return view;
    }

    private boolean contains(ArrayList<AlbumModel> list, AlbumModel model)
    {
        /**
         * суть функции заключается в том, что мы берем предыдущий ответ сервера и первый элемент нового ответа
         * проверяем, если хоть у одного элемента из предыдущего ответа совпадает id с новым ответом, возвращаем true
         */
        for (AlbumModel m: list) //цикл по всему списку. каждая итерация возвращает элемент списка
        {
            if (m.getId() == model.getId()) // если совпадает id
            {
                return true; //возвращаем true, если было совпадение
            }
        }
        return false; // если не было совпадения
    }

    private void loadLastAlbums()
    {
        if (isNetworkAvailable())
        {
            isLoading = true; // ставим метку, что идет загрузка
            HttpRequestHandler.getInstance().setCallback(lastNewsCallback); //устанавливаем, какой коллбэк получит ответ сервера
            HttpRequestHandler.getInstance().post("/api/albums"); //запрашиваем данные (в ответ всегда придет первые 20 ответов (1 страница))
        }
        else
        {
            noConnectionSnackbar.show();
            refreshLayout.setRefreshing(false);
            isLoading = false;
        }
    }

    private void loadPreviousNews(int pageNum)
    {
        if (isNetworkAvailable())
        {
            Log.e("PAGE NUM", pageNum + "");
            isLoading = true;
            HttpRequestHandler.getInstance().setCallback(previousNewsCallback);
            HttpRequestHandler.getInstance().postWithParams("/api/albums", pageNum); //работает аналогично обычному post(), с той разницей
            //что передает на сервер информацию о том, какую страницу загрузить
        }
        else
        {
            noConnectionSnackbar.show();
            isLoading = false;
        }
    }

    private void recursiveAlbumLoad(int pageNum)
    {
        if (isNetworkAvailable())
        {
            isLoading = true;
            HttpRequestHandler.getInstance().setCallback(previousNewsCallback);
            HttpRequestHandler.getInstance().get("/api/photos", pageNum);
        }
        else
        {
            noConnectionSnackbar.show();
            isLoading = false;
        }
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
    public void onFragmentResume()
    {

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

