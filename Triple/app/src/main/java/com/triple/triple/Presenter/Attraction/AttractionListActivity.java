package com.triple.triple.Presenter.Attraction;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.triple.triple.Adapter.CityAdapter;
import com.triple.triple.Helper.Constant;
import com.triple.triple.Helper.SystemPropertyHelper;
import com.triple.triple.Interface.ApiInterface;
import com.triple.triple.Model.Attraction;
import com.triple.triple.Model.City;
import com.triple.triple.Model.DataMeta;
import com.triple.triple.Model.TripDetail;
import com.triple.triple.Model.TripItinerary;
import com.triple.triple.Presenter.Mytrips.ItineraryActivity;
import com.triple.triple.Presenter.Mytrips.ItineraryFragment;
import com.triple.triple.R;
import com.triple.triple.Sync.ApiClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttractionListActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    private ViewPager mViewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private List <Attraction> attractions;
    private DataMeta dataMeta, attraction, hotel, restaurant;
    private City city;
    SearchView searchView;
    AttractionFragment chatFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction);

        findViews();
        initView();

    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mViewPager = (ViewPager) findViewById(R.id.container);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
    }

    private void initView() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        city = (City) bundle.getSerializable("city");
        dataMeta = (DataMeta) bundle.getSerializable("dataMeta");
        attraction = (DataMeta) bundle.getSerializable("attraction");
        hotel = (DataMeta) bundle.getSerializable("hotel");
        restaurant = (DataMeta) bundle.getSerializable("restaurant");
        toolbar.setTitle(city.getName());
        setSupportActionBar(toolbar);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), dataMeta, attraction, hotel, restaurant);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout.addTab(tabLayout.newTab().setText("Attractions").setTag(1));
        tabLayout.addTab(tabLayout.newTab().setText("Hotels").setTag(2));
        tabLayout.addTab(tabLayout.newTab().setText("Restaurants").setTag(3));

        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private DataMeta dataMeta, attraction, hotel, restaurant;

        public SectionsPagerAdapter(FragmentManager fm, DataMeta dataMeta, DataMeta attraction, DataMeta hotel, DataMeta restaurant) {
            super(fm);
            this.dataMeta = dataMeta;
            this.attraction = attraction;
            this.hotel = hotel;
            this.restaurant = restaurant;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new AttractionFragment();
            Bundle bundle = new Bundle();
            switch (position){
                case 0:
                    bundle.putSerializable("dataMeta", dataMeta);
                    //bundle.putSerializable("dataMeta", attraction);
                    fragment.setArguments(bundle);
                    break;
                case 1:
                    bundle.putSerializable("dataMeta", dataMeta);
                    //bundle.putSerializable("dataMeta", hotel);
                    fragment.setArguments(bundle);
                    break;
                case 2:
                    bundle.putSerializable("dataMeta", dataMeta);
                    //bundle.putSerializable("dataMeta", restaurant);
                    fragment.setArguments(bundle);
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }



}
