package com.goodsign.sangkghanews.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.goodsign.sangkghanews.adapters.LecturesListRecyclerAdapter;
import com.goodsign.sangkghanews.handlers.HttpRequestHandler;
import com.goodsign.sangkghanews.handlers.JsonParser;
import com.goodsign.sangkghanews.models.LectureModel;
import com.goodsign.sangkghanews.R;
import com.rey.material.widget.ProgressView;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Roman on 10.01.2017.
 */

public class LecturesList extends Fragment implements BackStackResumedFragment
{

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private LecturesListRecyclerAdapter lecturesListRecyclerAdapter;
    private SwipeRefreshLayout refreshLayout; //отвечает за обновление списка  загрузки инфы с сервера
    private LinearLayout progressLayout;    //хранит ProgressView
    private ProgressView progressView;  //Отображает загрузку старых новостей
    private Snackbar noConnectionSnackbar; //Выводится, если нет подключения к интернету
    private LinearLayout layout;

    private ArrayList<LectureModel> lecturesList;
    private ArrayList<LectureModel> responseList, previousResponseList;
    private Callback lastNewsCallback, previousNewsCallback;
    private int pageNum; //отвечает за загрузку определенной страницы (одна страница = 20 записей)
    private boolean isLoading; //отвечает за проверку, идет ли сейчас загрузка данных с сервера

    public static LecturesList newInstance()
    {
        return new LecturesList();
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
                                loadLastLectures();
                            }
                        });
                    }
                });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        lecturesList = new ArrayList<>();
        pageNum = 2;
        isLoading = false;
        View view = inflater.inflate(R.layout.fragment_list_lectures, container, false);
        layout = (LinearLayout) view.findViewById(R.id.layout_lectures);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_refresh_lectures);
        refreshLayout.setColorSchemeResources(R.color.accent);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_lectures);
        progressLayout = (LinearLayout) view.findViewById(R.id.layout_progress_lectures);
        progressView = (ProgressView) view.findViewById(R.id.progressViewLectures);
        //Отключаем отображение загрузки
        progressLayout.setVisibility(View.GONE);
        progressView.setVisibility(View.GONE);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        lecturesListRecyclerAdapter = new LecturesListRecyclerAdapter(lecturesList, getFragmentManager());
        recyclerView.setAdapter(lecturesListRecyclerAdapter);

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
                responseList = JsonParser.getLecturesArrayFromJson(response.body().string()); //парсим ответ
                if (responseList != null && responseList.size()>0) //проверяем, не пуст ли он
                {
                    pageNum = 2; //сбрасываем страницу
                    previousResponseList = responseList; //запоминаем ответ с сервера
                    lecturesList = responseList; // загружаем инфу в список новостей
                    lecturesList.trimToSize();  //магия
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.setRefreshing(false); //выключаем refresh
//                            progress.setVisibility(View.GONE);
                            lecturesListRecyclerAdapter.updateList(lecturesList); //обновляем список
                        }
                    });
                }
                isLoading = false; // отмечаем, что загрузка окончена
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
                responseList = JsonParser.getLecturesArrayFromJson(response.body().string());
                /**
                 * функция contains отвечает за проверку того, какая страница пришла в ответ
                 * вся соль в том, что если на сервере больше нет информации, он присылает последнюю существующую страницу
                 * contains проверяет, совпадают ли страницы
                 */
                if (previousResponseList == null || !contains(previousResponseList, responseList.get(0)))
                //если лист существует и не совпадает с предыдущим запросом
                {
                    previousResponseList = responseList; //перезаписываем предыдущий запрос
                    lecturesList.addAll(responseList); //добавляем полученные элементы в список
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
                            lecturesListRecyclerAdapter.updateList(lecturesList); //обновляем список
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
                loadLastLectures(); //загружаем последние новости
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
                loadLastLectures();
            }
        });

        /**
         * OnScrollListener отвечает за обработку прокрутки списка пользователем
         */
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();//смотрим сколько элементов на экране
                int totalItemCount = layoutManager.getItemCount();//сколько всего элементов
                int firstVisibleItems = layoutManager.findFirstVisibleItemPosition();//какая позиция первого видимого элемента

                /*Log.e("VISIBLE", visibleItemCount+"");
                Log.e("FIRST POS", firstVisibleItems+"");
                Log.e("TOTAL", totalItemCount+"");*/
//                Log.v("LOADING", isLoading+"");

                if ((visibleItemCount + firstVisibleItems) >= totalItemCount && visibleItemCount < totalItemCount)
                //если кол-во видимых и номер первого видимого элемента >= общему числу
                // и если число видимых меньше общего числа
                {
                    if (!isLoading) //если ничего не загружается с сервера
                    {
                        Log.e("SCROLL", "LOAD");
                        loadPreviousLectures(pageNum); // загружаем с сервера страницу по номеру
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
        });

        return view;
    }


    private boolean contains(ArrayList<LectureModel> list, LectureModel model)
    {
        /**
         * суть функции заключается в том, что мы берем предыдущий ответ сервера и первый элемент нового ответа
         * проверяем, если хоть у одного элемента из предыдущего ответа совпадает id с новым ответом, возвращаем true
         */
        for (LectureModel m: list) //цикл по всему списку. каждая итерация возвращает элемент списка
        {
            if (m.getId() == model.getId()) // если совпадает id
            {
                return true; //возвращаем true, если было совпадение
            }
        }
        return false; // если не было совпадения
    }

    private void loadLastLectures()
    {
        if (isNetworkAvailable())
        {
            isLoading = true; // ставим метку, что идет загрузка
            HttpRequestHandler.getInstance().setCallback(lastNewsCallback); //устанавливаем, какой коллбэк получит ответ сервера
            HttpRequestHandler.getInstance().post("/api/lectures"); //запрашиваем данные (в ответ всегда придет первые 20 ответов (1 страница))
        }
        else
        {
            noConnectionSnackbar.show();
            refreshLayout.setRefreshing(false);
            isLoading = false;
        }
    }

    private void loadPreviousLectures(int pageNum)
    {
//        if (isNetworkAvailable())
//        {
            Log.e("PAGE NUM", pageNum + "");
            isLoading = true;
            progressLayout.setVisibility(View.VISIBLE);
            progressView.setVisibility(View.VISIBLE);
            HttpRequestHandler.getInstance().setCallback(previousNewsCallback);
            HttpRequestHandler.getInstance().postWithParams("/api/lectures", pageNum); //работает аналогично обычному post(), с той разницей
            //что передает на сервер информацию о том, какую страницу загрузить
//        }
//        else
//        {
//            noConnectionSnackbar.show();
//            isLoading = false;
//        }
    }

    @Override
    public void onFragmentResume() {

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
