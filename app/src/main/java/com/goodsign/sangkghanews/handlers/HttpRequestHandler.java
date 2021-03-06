package com.goodsign.sangkghanews.handlers;

import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.IOException;
import java.math.BigDecimal;

import cz.msebera.android.httpclient.message.BasicHeader;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Roman on 01.02.2017.
 */

public class HttpRequestHandler
{
    private OkHttpClient client;
    private static HttpRequestHandler INSTANCE = new HttpRequestHandler();
    private final String BASE_URL = "http://195.133.144.16";

    private Request request;
    private Callback callback;
    private Call call;
    private Response response;
    private boolean load;

    private HttpRequestHandler()
    {
        client = new OkHttpClient();
    }

    /*
    * Читай, что такое синглтон
    * */
    public static HttpRequestHandler getInstance()
    {
        return INSTANCE;
    }

    public void get(String url, int pageNum)
    {
        request = new Request.Builder()
                .url(getUrlWithParams(url, pageNum))
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .get()
                .build();
        call = client.newCall(request);
        new AsyncRequest().execute();
    }

    public void post(String url)
    {
        //формируем параметры запроса
        RequestBody body = new FormBody.Builder()
                .addEncoded("page", "1")
                .build();
        //формируем запрос
        request = new Request.Builder()
                .url(getAbsoluteUrl(url))
                //хэдер обязателен
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                //прикрепляем параметры
                .post(body)
                .build();
        //инициализируем запрос
        new AsyncRequest().execute();
    }

    public void postWithParams(String url, int pageNum)
    {
        RequestBody body = new FormBody.Builder()
                .addEncoded("page", pageNum+"")
                .build();
        request = new Request.Builder()
                .url(getAbsoluteUrl(url))
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .post(body)
                .build();
        new AsyncRequest().execute();
        //TODO безопасное прерывание работы запроса
    }

    public void requestExternalPayment(BigDecimal amount)
    {
        RequestBody body = new FormBody.Builder()
                .addEncoded("pattern_id","")
                .addEncoded("instance_id","")
                .addEncoded("to", "")
                .addEncoded("amount", amount.toPlainString())
                .addEncoded("message","")
                .build();
    }

    public BigDecimal getAmount(String amount)
    {
        return new BigDecimal(amount);
    }

    public void setCallback(Callback callback)
    {
        this.callback = callback;
    }

    public boolean isLoad()
    {
        return load;
    }

    public void setLoad(boolean b)
    {
        load = b;
    }

    public String getAbsoluteUrl(String relativeUrl)
    {
        return BASE_URL + relativeUrl;
    }

    public String getUrlWithParams(String relativeUrl, int pageNum)
    {
        return getAbsoluteUrl(relativeUrl) + "?" + "page=" + pageNum;
    }

    public void cancelAllRequests()
    {
        for (Call call: client.dispatcher().queuedCalls())
        {
            call.cancel();
        }
        for (Call call: client.dispatcher().runningCalls())
        {
            call.cancel();
        }
    }

    class AsyncRequest extends AsyncTask <Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            try
            {
                //не знаю, что эта херь делает, но она нужна
                call = client.newCall(request);
                response = call.execute();
                //передача ответа в коллбэк
                callback.onResponse(call, response);
            }
            catch (IOException e)
            {
                //e.printStackTrace();
                callback.onFailure(call, e);
            }
            return null;
        }
    }

}
