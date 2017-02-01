package com.goodsign.sangkghanews;

import android.Manifest;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.NavigationView;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.goodsign.sangkghanews.Fragment.Album;
import com.goodsign.sangkghanews.Fragment.DatsansList;
import com.goodsign.sangkghanews.Fragment.HistoryList;
import com.goodsign.sangkghanews.Fragment.HooralsByDays;
import com.goodsign.sangkghanews.Fragment.LecturesList;
import com.goodsign.sangkghanews.Fragment.News;
import com.goodsign.sangkghanews.Fragment.NewsList;
import com.goodsign.sangkghanews.Fragment.Photo;
import com.goodsign.sangkghanews.Fragment.TableTime;
import com.goodsign.sangkghanews.Fragment.Video;
import com.goodsign.sangkghanews.Fragment.VideoList;
import com.goodsign.sangkghanews.Fragment.ZurhaiByDays;
import com.goodsign.sangkghanews.R;

/**
 * Created by Roman on 10.12.2016.
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private Toolbar toolbar;

    private final int WRITE_EXTERNAL_STORAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setIcon(R.drawable.ic_koleso48);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Сангкха онлайн");
        setSupportActionBar(toolbar);

        checkPermissions();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id==R.id.nav_news)

        {
            Fragment fragment = new NewsList();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            item.setChecked(true);
//        setTitle(item.getTitle());
        }

        else if(id==R.id.nav_hoorals)

        {
            Fragment fragment = HooralsByDays.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            item.setChecked(true);
//        setTitle(item.getTitle());
        }

        else if(id==R.id.nav_astrology)

        {
            Fragment fragment = ZurhaiByDays.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            item.setChecked(true);
        }

        else if(id==R.id.nav_order)

        {

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
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            item.setChecked(true);
//        setTitle(item.getTitle());
        }

        else if(id==R.id.nav_video)

        {
            Fragment fragment = new VideoList();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            item.setChecked(true);
//        setTitle(item.getTitle());
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
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}

