package com.goodsign.sangkghanews.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.goodsign.sangkghanews.adapters.VideoListRecyclerAdapter;
import com.goodsign.sangkghanews.handlers.HttpRequestHandler;
import com.goodsign.sangkghanews.handlers.JsonParser;
import com.goodsign.sangkghanews.models.VideoModel;
import com.goodsign.sangkghanews.R;
import com.rey.material.widget.ProgressView;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Машка on 17.12.2016.
 */
/* Вид списка видео*/
public class VideoList extends Fragment implements BackStackResumedFragment {
    private VideoListRecyclerAdapter videoListRecyclerAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout refreshLayout; //отвечает за обновление списка  загрузки инфы с сервера
    private LinearLayout progressLayout;    //хранит ProgressView
    private ProgressView progressView;  //Отображает загрузку старых новостей
    private Snackbar noConnectionSnackbar; //Выводится, если нет подключения к интернету
    private LinearLayout layout; //Контейнер верхнего уровня

    private ArrayList<VideoModel> videoModelArrayList;
    private ArrayList<VideoModel> responseList, previousResponseList;
    private Callback lastNewsCallback, previousVideosCallback;
    private int pageNum; //отвечает за загрузку определенной страницы (одна страница = 20 записей)
    private boolean isLoading; //отвечает за проверку, идет ли сейчас загрузка данных с сервера

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
                                pageNum = 1;
                                refreshLayout.setRefreshing(true);
                                loadLastVideos();
                            }
                        });
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        videoModelArrayList = new ArrayList<>();
//        pageNum = 2;
        isLoading = false;

        View view=inflater.inflate(R.layout.fragment_list_video, container,false);
        layout = (LinearLayout) view.findViewById(R.id.layout_video);

//        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_refresh_video);
//        refreshLayout.setColorSchemeResources(R.color.accent);
        recyclerView = (RecyclerView) view.findViewById(R.id.frag_video);
        progressLayout = (LinearLayout) view.findViewById(R.id.layout_progress_video);
//        progressView = (ProgressView) view.findViewById(R.id.progressViewVideo);
        //Отключаем отображение загрузки
        progressLayout.setVisibility(View.GONE);
//        progressView.setVisibility(View.GONE);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        videoListRecyclerAdapter = new VideoListRecyclerAdapter(videoModelArrayList, getContext());
        recyclerView.setAdapter(videoListRecyclerAdapter);


        /**
         * Этот коллбэк отвечает за обработку свежих новостей
         */
        previousVideosCallback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e)
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        noConnectionSnackbar.show();
//                        refreshLayout.setRefreshing(false);
                        isLoading = false;
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                String resp = response.body().string();
                Log.e("VIDEO", resp);
//                Log.e("VIDEO", response.body().string().trim());
                responseList = JsonParser.getVideosArrayFromJson(resp); //парсим ответ
                /*if (responseList != null && responseList.size()>0) //проверяем, не пуст ли он
                {
                    pageNum = 2; //сбрасываем страницу
                    previousResponseList = responseList; //запоминаем ответ с сервера
                    videoModelArrayList = responseList; // загружаем инфу в список новостей
                    videoModelArrayList.trimToSize();  //магия
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.setRefreshing(false); //выключаем refresh
//                            progress.setVisibility(View.GONE);
                            videoListRecyclerAdapter.updateList(videoModelArrayList); //обновляем список
                        }
                    });
                }
                isLoading = false; // отмечаем, что загрузка окончена*/
                if (previousResponseList == null || !contains(previousResponseList, responseList.get(0)))
                {
                    for (int i = 0; i < responseList.size(); i++)
                    {
//                        if (album.getId() == responseList.get(i).getAlbum())
//                        {
//                            photoList.add(responseList.get(i));
//                        }
                        videoModelArrayList.add(responseList.get(i));
                    }
                    getActivity().runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            videoListRecyclerAdapter.updateList(videoModelArrayList);
                        }
                    });
                    previousResponseList = responseList;
                    pageNum++;
                    recursiveVideoLoad(pageNum);
                }
            }
        };
        recursiveVideoLoad(pageNum);


        /**
         * Этот коллбэк отвечает за загрузку старых новостей
         */
        /**previousVideosCallback = new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        isLoading = false; // отмечаем, что загрузка окончена
                        progressLayout.setVisibility(View.GONE);
                        progressView.setVisibility(View.GONE); //Отключаем отображение загрузки
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException
            {
                responseList = JsonParser.getVideosArrayFromJson(response.body().string());
                /**
                 * функция contains отвечает за проверку того, какая страница пришла в ответ
                 * вся соль в том, что если на сервере больше нет информации, он присылает последнюю существующую страницу
                 * contains проверяет, совпадают ли страницы
                 */
                /*if (previousResponseList == null || !contains(previousResponseList, responseList.get(0)))
                //если лист существует и не совпадает с предыдущим запросом
                {
                    previousResponseList = responseList; //перезаписываем предыдущий запрос
                    videoModelArrayList.addAll(responseList); //добавляем полученные элементы в список
                    pageNum++; //изменяем число для того, чтобы загрузить следующую страницу
                    /**
                     * поскольку работать с интерфейсом приложения можно только в GUI-потоке
                     * нам надо получить к нему доступ
                     */
                    /*getActivity().runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run() {
                            Log.e("LIST", "UPDATED"); //без комментариев
                            videoListRecyclerAdapter.updateList(videoModelArrayList); //обновляем список
                        }
                    });
                }
                // даже если список совпадает, нам надо отключить отображение загрузки
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        //тут все ясно
                        progressLayout.setVisibility(View.GONE);
                        progressView.setVisibility(View.GONE);
                    }
                });
                isLoading = false; //тут тоже
            }
        };*/

        /**
         * чтобы вызвать onRefresh, надо пролистать в самый верх списка и потянуть его вниз
         * вылезет такая херь вращающаяся
         * думаю, ты сразу поймешь, что это
         */
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
//        {
//            @Override
//            public void onRefresh()
//            {
//                loadLastVideos(); //загружаем последние новости
//                pageNum = 1;
//                recursiveVideoLoad(pageNum);
//            }
//        });

        /**
         * эта функция вызывает onRefresh без необходимости тянуть вручную
         * в данном случае вызывается только при запуске фрагмента, чтобы загрузить первую страницу
         */
//        refreshLayout.post(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                если не выполнить setRefreshing, не сработает отображение загрузки
//                pageNum = 1;
//                refreshLayout.setRefreshing(true);
//                loadLastVideos();
//                recursiveVideoLoad(pageNum);
//            }
//        });

        /**
         * OnScrollListener отвечает за обработку прокрутки списка пользователем
         */
        /*recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();//смотрим сколько элементов на экране
                int totalItemCount = layoutManager.getItemCount();//сколько всего элементов
                int firstVisibleItems = layoutManager.findFirstVisibleItemPosition();//какая позиция первого видимого элемента
                if ((visibleItemCount + firstVisibleItems) >= totalItemCount && visibleItemCount < totalItemCount)
                //если кол-во видимых и номер первого видимого элемента >= общему числу
                // и если число видимых меньше общего числа
                {
                    if (!isLoading) //если ничего не загружается с сервера
                    {
                        Log.e("SCROLL", "LOAD");
                        loadPreviousVideos(pageNum); // загружаем с сервера страницу по номеру
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


    @Override
    public void onFragmentResume()
    {

    }


    private void recursiveVideoLoad(int pageNum)
    {
        if (isNetworkAvailable())
        {
            HttpRequestHandler.getInstance().setCallback(previousVideosCallback);
            HttpRequestHandler.getInstance().get("/api/videos", pageNum);
        }
        else
        {
            noConnectionSnackbar.show();
        }
    }

    private boolean contains(ArrayList<VideoModel> list, VideoModel model)
    {
        /**
         * суть функции заключается в том, что мы берем предыдущий ответ сервера и первый элемент нового ответа
         * проверяем, если хоть у одного элемента из предыдущего ответа совпадает id с новым ответом, возвращаем true
         */
        for (VideoModel m: list) //цикл по всему списку. каждая итерация возвращает элемент списка
        {
            if (m.getId() == model.getId()) // если совпадает id
            {
                return true; //возвращаем true, если было совпадение
            }
        }
        return false; // если не было совпадения
    }

    private void loadLastVideos()
    {
        isLoading = true; // ставим метку, что идет загрузка
        HttpRequestHandler.getInstance().setCallback(lastNewsCallback); //устанавливаем, какой коллбэк получит ответ сервера
        HttpRequestHandler.getInstance().post("/api/videos"); //запрашиваем данные (в ответ всегда придет первые 20 ответов (1 страница))
    }

    private void loadPreviousVideos(int pageNum)
    {
        Log.e("PAGE NUM", pageNum+"");
        isLoading = true;
        progressLayout.setVisibility(View.VISIBLE);
//        progressView.setVisibility(View.VISIBLE);
        HttpRequestHandler.getInstance().setCallback(previousVideosCallback);
        HttpRequestHandler.getInstance().postWithParams("/api/videos", pageNum); //работает аналогично обычному post(), с той разницей
        //что передает на сервер информацию о том, какую страницу загрузить
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
}
