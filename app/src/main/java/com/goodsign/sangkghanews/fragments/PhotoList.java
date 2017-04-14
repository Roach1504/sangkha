package com.goodsign.sangkghanews.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodsign.sangkghanews.adapters.AlbumListRecyclerAdapter;
import com.goodsign.sangkghanews.adapters.PhotoListRecyclerAdapter;
import com.goodsign.sangkghanews.handlers.HttpRequestHandler;
import com.goodsign.sangkghanews.handlers.JsonParser;
import com.goodsign.sangkghanews.models.AlbumModel;
import com.goodsign.sangkghanews.models.PhotoModel;
import com.goodsign.sangkghanews.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Машка on 18.01.2017.
 */

public class PhotoList extends Fragment implements BackStackResumedFragment{
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private PhotoListRecyclerAdapter photoListRecyclerAdapter;

    private ImageView header_image;
    private LinearLayout layout_title;
    private TextView header_title, header_description;

    private Snackbar noConnectionSnackbar; //Выводится, если нет подключения к интернету
    private CoordinatorLayout layout;

    private Callback lastPhotosCallback, previousPhotosCallback;
    private ArrayList<PhotoModel> responseList, previousResponseList;
    private ArrayList<PhotoModel> photoList;
    private int pageNum;
    private DisplayImageOptions displayImageOptions; //TODO передать опции по ссылке в адаптер без инициализации

    private boolean isLoading;

    private AlbumModel album;

    public static PhotoList newInstance(AlbumModel model)
    {
        PhotoList photoList = new PhotoList();
        Bundle bundle = new Bundle();
        bundle.putSerializable("albumModel", model);
        photoList.setArguments(bundle);
        return photoList;
    }

//    public static PhotoList newInstance(PhotoModel photoModel)
//    {
//        PhotoList fragment = new PhotoList();
//        Bundle bundle = new Bundle();
////        bundle.putSerializable("PhotoModel", photoModel);
//        fragment.setArguments(bundle);
//
//        return fragment;
//    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        noConnectionSnackbar = Snackbar.make(layout, R.string.no_connection, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.refresh, new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        noConnectionSnackbar.dismiss();
                        pageNum = 1;
                        recursivePhotoLoad(pageNum);
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        displayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.pic_stub_192)
                .showImageForEmptyUri(R.drawable.pic_stub_192)
                .showImageOnFail(R.drawable.pic_fail_512)
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .build();
        isLoading = false;

        View view=inflater.inflate(R.layout.fragment_list_photo, container,false);
        layout = (CoordinatorLayout) view.findViewById(R.id.layout_photos);
        layout_title = (LinearLayout) view.findViewById(R.id.layout_album_title);
        header_image = (ImageView) view.findViewById(R.id.image_photos_header);
        header_title = (TextView) view.findViewById(R.id.text_photos_header_title);
        header_description = (TextView) view.findViewById(R.id.text_photos_header_description);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_photos);

        album = (AlbumModel) getArguments().getSerializable("albumModel");
        photoList = new ArrayList<>();
        pageNum = 1;

        String image_url = album.getImage_url();
        if (!image_url.equals(""))
        {
            if (image_url.contains("uploads") && (!image_url.contains("http") || !image_url.contains("https")))
            {
                ImageLoader.getInstance().displayImage(HttpRequestHandler.getInstance().getAbsoluteUrl(image_url), header_image, displayImageOptions);
            }
            else
            {
                ImageLoader.getInstance().displayImage(image_url, header_image, displayImageOptions);
            }
        }
        if (album.getTitle() == null || album.getTitle().equals(""))
        {
            header_title.setVisibility(View.GONE);
        }
        else
        {
            header_title.setText(album.getTitle());
        }
        if (album.getDecription() == null || album.getDecription().equals(""))
        {
            header_description.setVisibility(View.GONE);
        }
        else
        {
            header_description.setText(album.getDecription());
        }
        //TODO открытие диалога с полным описанием при нажатии на текст
        photoListRecyclerAdapter = new PhotoListRecyclerAdapter(photoList, getFragmentManager());
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(photoListRecyclerAdapter);

        previousPhotosCallback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e)
            {
                isLoading = false;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                responseList = JsonParser.getPhotosArrayFromJson(response.body().string());

                if (previousResponseList == null || !contains(previousResponseList, responseList.get(0)))
                {
                    for (int i = 0; i < responseList.size(); i++)
                    {
                        if (album.getId() == responseList.get(i).getAlbum())
                        {
                            photoList.add(responseList.get(i));
                        }
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run()
                        {
                            photoListRecyclerAdapter.updateList(photoList);
                        }
                    });
                    previousResponseList = responseList;
                    pageNum++;
                    recursivePhotoLoad(pageNum);
                }
            }
        };
        recursivePhotoLoad(pageNum);
//        Boolean empty = false;
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(album.getTitle());
        builder.setMessage(album.getDecription());
        builder.setPositiveButton("Закрыть", null);
        builder.create();
        layout_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                builder.show();
            }
        });
    }

    @Override
    public void onFragmentResume()
    {
//        pageNum = 1;
//        recursivePhotoLoad(pageNum);
    }

    private void recursivePhotoLoad(int pageNum)
    {
        if (isNetworkAvailable())
        {
            HttpRequestHandler.getInstance().setCallback(previousPhotosCallback);
            HttpRequestHandler.getInstance().get("/api/photos", pageNum);
        }
        else
        {
            noConnectionSnackbar.show();
        }
    }

    private boolean contains(ArrayList<PhotoModel> list, PhotoModel model)
    {
        for (PhotoModel m : list)
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
