package com.triple.triple.Presenter.Attraction;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.triple.triple.Adapter.FragmentAdapter;
import com.triple.triple.R;

import java.util.ArrayList;
import java.util.List;

public class CityInfoActivity extends AppCompatActivity {

    private ViewPager container;
    private TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_info);
        findViews();
        initViewPager();
    }

    private void findViews() {
        tabs = (TabLayout) findViewById(R.id.tabs);
        container = (ViewPager) findViewById(R.id.container);
    }

    private void initViewPager() {


        List<String> titles = new ArrayList<>();
        titles.add("Overview");
        tabs.addTab(tabs.newTab().setText(titles.get(0)));

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new CityInfoOverviewFragment());


        container.setOffscreenPageLimit(2);

        FragmentAdapter mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        container.setAdapter(mFragmentAdapter);
        tabs.setupWithViewPager(container);
        tabs.setTabsFromPagerAdapter(mFragmentAdapter);

    }
}
