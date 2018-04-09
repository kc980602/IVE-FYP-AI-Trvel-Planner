package com.triple.triple.Presenter.Attraction;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.triple.triple.Adapter.FragmentAdapter;
import com.triple.triple.Adapter.MyTripsFragmentAdapter;
import com.triple.triple.Model.City;
import com.triple.triple.R;

import java.util.ArrayList;
import java.util.List;

public class CityInfoActivity extends AppCompatActivity {

    private ViewPager container;
    private TabLayout tabs;
    private City city;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_info);
        findViews();
        initViewPager();
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabs = (TabLayout) findViewById(R.id.tabs);
        container = (ViewPager) findViewById(R.id.container);
    }

    private void initViewPager() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        city = (City) bundle.getSerializable("city");
        toolbar.setTitle(city.getName());
        setSupportActionBar(toolbar);

        List<String> titles = new ArrayList<>();
        titles.add("Overview");
        tabs.addTab(tabs.newTab().setText(titles.get(0)));

        List<Fragment> fragments = new ArrayList<>();
        Fragment fragment = new CityInfoOverviewFragment();
        fragment.setArguments(bundle);
        fragments.add(fragment);


        container.setOffscreenPageLimit(2);

        FragmentAdapter mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        container.setAdapter(mFragmentAdapter);
        tabs.setupWithViewPager(container);
        tabs.setTabsFromPagerAdapter(mFragmentAdapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                super.onBackPressed();
                break;
        }
        return true;
    }

}
