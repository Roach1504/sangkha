package com.goodsign.sangkghanews;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.NavigationView;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.goodsign.sangkghanews.fragments.AboutDevelopers;
import com.goodsign.sangkghanews.fragments.Album;
import com.goodsign.sangkghanews.fragments.BackStackResumedFragment;
import com.goodsign.sangkghanews.fragments.DatsansList;
import com.goodsign.sangkghanews.fragments.HistoryList;
import com.goodsign.sangkghanews.fragments.HooralsByDays;
import com.goodsign.sangkghanews.fragments.LecturesList;
import com.goodsign.sangkghanews.fragments.News;
import com.goodsign.sangkghanews.fragments.NewsList;
import com.goodsign.sangkghanews.fragments.VideoList;
import com.goodsign.sangkghanews.fragments.ZurhaiByDays;
import com.goodsign.sangkghanews.handlers.HttpRequestHandler;
import com.goodsign.sangkghanews.handlers.QuoteRandomizer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Roman on 10.12.2016.
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private TextView header_quote;

    private final int WRITE_EXTERNAL_STORAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setIcon(R.drawable.ic_koleso48);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        checkPermissions();

        final QuoteRandomizer quoteRandomizer = new QuoteRandomizer();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        header_quote = (TextView) navigationView.getHeaderView(0).findViewById(R.id.navheader_quote);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name)
        {
            @Override
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
                header_quote.setText(quoteRandomizer.getRandomQuote());
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged()
            {
                //TODO доделать
                FragmentManager manager = getSupportFragmentManager();
                if (manager != null)
                {
                    BackStackResumedFragment currentFragment = (BackStackResumedFragment) manager.findFragmentById(R.id.container);
                    currentFragment.onFragmentResume();

                    if (currentFragment instanceof NewsList)
                    {
                        navigationView.setCheckedItem(R.id.nav_news);
                    }
                    else if (currentFragment instanceof HooralsByDays)
                    {
                        navigationView.setCheckedItem(R.id.nav_hoorals);
                    }
                    else if (currentFragment instanceof ZurhaiByDays)
                    {
                        navigationView.setCheckedItem(R.id.nav_astrology);
                    }
                    else if (currentFragment instanceof HistoryList)
                    {
                        navigationView.setCheckedItem(R.id.nav_history);
                    }
                    else if (currentFragment instanceof DatsansList)
                    {
                        navigationView.setCheckedItem(R.id.nav_datsans);
                    }
                    else if (currentFragment instanceof LecturesList)
                    {
                        navigationView.setCheckedItem(R.id.nav_lecture);
                    }
                    else if (currentFragment instanceof Album)
                    {
                        navigationView.setCheckedItem(R.id.nav_photo);
                    }
                    else if (currentFragment instanceof VideoList)
                    {
                        navigationView.setCheckedItem(R.id.nav_video);
                    }
                    else if (currentFragment instanceof AboutDevelopers)
                    {
                        navigationView.setCheckedItem(R.id.nav_developers);
                    }
                }
            }
        });

//        class TestConnectServer extends AsyncTask<Void, Void, Void> {
//
//
//
//            protected String getStatus(String key, String strJson) {
//                JSONObject dataJsonObj = null;
//                String secondName = "";
//                try {
//                    dataJsonObj = new JSONObject(strJson);
//                    secondName = dataJsonObj.getString(key);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                return secondName;
//            }
//
//            protected Void doInBackground(Void... params) {
//                try {
//
//
//                    Log.e("START CONNECT", "ff");
//                    OkHttpClient client = new OkHttpClient();
//
//                    RequestBody formBody = new FormBody.Builder()
//                            .addEncoded("page", "2")
//                            .build();
//
//
//                    Request request = new Request.Builder()
//.url("http://195.133.144.16/api/news")
//.url("http://195.133.144.16/api/datsans")
//.url("http://195.133.144.16/api/history")
//.url("http://195.133.144.16/api/hurals")
//                            .url("http://195.133.144.16/api/history")
//.url("http://195.133.144.16/api/lectures")
//.url("http://195.133.144.16/api/photos")
//.url("http://195.133.144.16/api/videos")
//.url("http://195.133.144.16/api/zurkhay")
//                            .addHeader("Content-Type", "application/x-www-form-urlencoded")
//                            .post(formBody)
//.get()
//                            .build();
//
//                    okhttp3.Call call = client.newCall(request);
//                    Response response = call.execute();
//                    Callback callback = new Callback() {
//                        @Override
//                        public void onFailure(Call call, IOException e) {
//
//                        }
//
//                        @Override
//                        public void onResponse(Call call, Response response) throws IOException {
//
//                        }
//                    };
//
//                    callback.onResponse(call, response);
//                    String message = response.body().string().trim();
//
//                    Log.e("ответ от сервера ", message);
//
//Log.e(TAG, "message GPS1 = " + message);
//Log.e(TAG, "Вот прям точно ушли");
// idreg = getStatus("id",srt);

//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                super.onPostExecute(aVoid);
//            }
//        }
//
//
//        TestConnectServer in1 = new TestConnectServer();
//        in1.execute();

//        Callback callback = new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException
//            {
//                Log.e("response", response.body().string().trim());
//            }
//        };
//        HttpRequestHandler.getInstance().setCallback(callback);
//        HttpRequestHandler.getInstance().postWithParams("/api/news", 1);
//
        if (savedInstanceState == null)
        {
            Fragment fragment = NewsList.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            navigationView.setCheckedItem(R.id.nav_news);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id==R.id.nav_news)
        {
            Fragment fragment = NewsList.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            item.setChecked(true);
//        setTitle(item.getTitle());
        }
        else if(id==R.id.nav_hoorals)
        {
            Fragment fragment = HooralsByDays.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            item.setChecked(true);
//        setTitle(item.getTitle());
        }
        else if(id==R.id.nav_astrology)
        {
            Fragment fragment = ZurhaiByDays.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            item.setChecked(true);
        }
        else if(id==R.id.nav_history)
        {
            Fragment fragment = HistoryList.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            item.setChecked(true);
//        setTitle(item.getTitle());
        }
        else if(id==R.id.nav_datsans)
        {
            Fragment fragment = DatsansList.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).addToBackStack(null).commit();
            item.setChecked(true);
            //setTitle(item.getTitle());
        }
        else if(id==R.id.nav_lecture)
        {
            Fragment fragment = LecturesList.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            item.setChecked(true);
//        setTitle(item.getTitle());
        }
        else if(id==R.id.nav_photo)
        {
            Fragment fragment = Album.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            item.setChecked(true);
//        setTitle(item.getTitle());
        }
        else if(id==R.id.nav_video)
        {
            Fragment fragment = new VideoList();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            item.setChecked(true);
//        setTitle(item.getTitle());
        }
        else if (id==R.id.nav_developers)
        {
            Fragment fragment = AboutDevelopers.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            item.setChecked(true);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean checkPermissions() {
        boolean result = true;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            result = false;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST);

        }
        return result;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults)
    {
        Log.e("PERMISSIONS", permissions[0] + " " + permissions.length);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length >= 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    finish();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            HttpRequestHandler.getInstance().cancelAllRequests();
            if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            {
                getSupportFragmentManager().popBackStack();
            }
            else
            {
                super.onBackPressed();
            }
        }
    }
}

