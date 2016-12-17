package com.goodsign.sangkghanews;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.NavigationView;

import android.view.MenuItem;

import com.goodsign.sangkghanews.Fragment.News;
import com.goodsign.sangkghanews.Fragment.TableTime;
import com.goodsign.sangkghanews.Fragment.Video;

/**
 * Created by Roman on 10.12.2016.
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    //    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_koleso48);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("Буддийская сангха");
//        setSupportActionBar(toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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
        Fragment fragment = new News();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        item.setChecked(true);
        setTitle(item.getTitle());
    }

    else if(id==R.id.nav_tabletime)

    {
        Fragment fragment = new TableTime();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        item.setChecked(true);
        setTitle(item.getTitle());
    }

    else if(id==R.id.nav_astrology)

    {

    }

    else if(id==R.id.nav_order)

    {

    }

    else if(id==R.id.nav_history)

    {

    }

    else if(id==R.id.nav_map)

    {

    }

    else if(id==R.id.nav_lecture)

    {

    }

    else if(id==R.id.nav_photo)

    {

    }

    else if(id==R.id.nav_video)

    {
        Fragment fragment = new Video();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        item.setChecked(true);
        setTitle(item.getTitle());
    }


    drawer.closeDrawer(GravityCompat.START);
    return true;
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
