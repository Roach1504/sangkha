package com.goodsign.sangkghanews.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.goodsign.sangkghanews.R;
import com.goodsign.sangkghanews.handlers.HttpRequestHandler;
import com.goodsign.sangkghanews.models.NewsModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.HashMap;

/**
 * Created by Машка on 10.01.2017.
 */

public class News extends Fragment implements ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener, BackStackResumedFragment
{
    private SliderLayout imageSlider;

    private NewsModel news;

    private TextView title, annotation, date;
    private HtmlTextView description;

    public static News newInstance(NewsModel model)
    {
        Bundle bundle = new Bundle();
        bundle.putSerializable("news", model);
        News news = new News();
        news.setArguments(bundle);
        return news;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        news = (NewsModel) getArguments().getSerializable("news");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);

        title = (TextView) view.findViewById(R.id.text_news_title);
        annotation = (TextView) view.findViewById(R.id.text_news_annotation);
        description = (HtmlTextView) view.findViewById(R.id.text_news_description);
        date = (TextView) view.findViewById(R.id.text_news_date);

        title.setText(news.getTitle());
        annotation.setText(news.getAnnotation());
        //description.setText(news.getText());
        date.setText(news.getDate());
        Document document = Jsoup.parseBodyFragment(news.getText());
        Element element = document.body();
//        description.setText(element.toString());
        description.setHtml(element.toString(), new HtmlHttpImageGetter(description));

        imageSlider = (SliderLayout) view.findViewById(R.id.sliderLayout);
        imageSlider.stopAutoCycle();

        HashMap<String,String> imgmap = new HashMap<>();
        //imgmap.put("Звезды","https://cdn.phead.mu/production/resized_img/1459bd3f-c398-4df0-b225-ceaf8c9281d0");
        imgmap.put("Волны","https://www.scirra.com/arcade/images/6162/450/1.jpg");
        imgmap.put("Цветок","http://oboi.pw/640-480-100-uploads/11_05_2013/view/201209/oboik.ru_42014.jpg");
        //imgmap.put("Небо","http://img-fotki.yandex.ru/get/18/pumalin.70/0_e05a_f7237c7f_XL");


        /*for(String value: imgmap.values())
        {
            /*TextSliderView textSliderView = new TextSliderView(view.getContext());
            textSliderView
                    .description(name)
                    .image("file:///android_asset/" + imgmap.get(name) + ".jpg")
                    .setScaleType(BaseSliderView.ScaleType.FitCenterCrop)
                    .setOnSliderClickListener(this);
            imageSlider.addSlider(textSliderView);*/
            /*DefaultSliderView defaultSliderView = new DefaultSliderView(view.getContext());
            defaultSliderView
                    .image(imgmap.get(value))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(this);
            imageSlider.addSlider(defaultSliderView);
        }*/
        DefaultSliderView defaultSliderView = new DefaultSliderView(view.getContext());
        defaultSliderView
                .image(HttpRequestHandler.getInstance().getAbsoluteUrl(news.getImagepath()))
                .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                .setOnSliderClickListener(this);
        imageSlider.addSlider(defaultSliderView);

        imageSlider.setCustomAnimation(new DescriptionAnimation());
        imageSlider.addOnPageChangeListener(this);

        return view;

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onFragmentResume() {

    }
}


