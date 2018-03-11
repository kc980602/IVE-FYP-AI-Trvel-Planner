package com.triple.triple.Presenter.Attraction;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.triple.triple.Helper.AppBarStateChangeListener;
import com.triple.triple.R;

public class CityDetailActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private AppBarLayout layout_appbar;
    private CollapsingToolbarLayout layout_collapsing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);
        
        findViews();
        initView();
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        layout_appbar = (AppBarLayout) findViewById(R.id.layout_appbar);
        layout_collapsing = (CollapsingToolbarLayout) findViewById(R.id.layout_collapsing);
    }

    private void initView() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


    }

}