package com.triple.triple.Presenter.Attraction;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.triple.triple.Helper.AppBarStateChangeListener;
import com.triple.triple.R;

public class CityDetailActivity extends AppCompatActivity {

    private Context mcontext = CityDetailActivity.this;

    private Toolbar toolbar;
    private LinearLayout layout_cityname;
    private BottomNavigationViewEx nav_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);

        findViews();
        initView();
    }

    private void findViews() {
        nav_bar = (BottomNavigationViewEx) findViewById(R.id.nav_bar);
    }

    private void initView() {
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle("Tokyo");
        ab.setElevation(0);

        nav_bar.enableAnimation(false);
        nav_bar.enableItemShiftingMode(false);
        nav_bar.enableShiftingMode(false);
        nav_bar.setOnNavigationItemSelectedListener(nav_barListener);
        Menu menu = nav_bar.getMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationViewEx.OnNavigationItemSelectedListener nav_barListener = new BottomNavigationViewEx.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent = new Intent();
            switch (item.getItemId()) {
                case R.id.action_discover:

                    break;
                case R.id.action_info:

                    break;
                case R.id.action_plan:
                    break;
                case R.id.action_favorities:

                    break;
            }
            return false;
        }
    };


    public void buttonClick(View view) {
//        Intent intent = new Intent();
//        switch (view.getId()) {
//            case R.id.layout_button_info:
//                intent.setClass(mcontext, CityInfoActivity.class);
//                break;
//
//        }
//        startActivity(intent);
    }
}
