package com.triple.triple.Presenter.Mytrips;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.triple.triple.Model.TripDetail;
import com.triple.triple.Model.TripItinerary;
import com.triple.triple.R;

import java.util.List;

public class ItineraryActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private TripDetail tripDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);

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
        tripDetail = (TripDetail) bundle.getSerializable("tripDetail");

        toolbar.setTitle(tripDetail.getTitle());
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), tripDetail);

        mViewPager.setAdapter(mSectionsPagerAdapter);

        List<TripItinerary> itineraryList = tripDetail.getItinerary();

        for (int i = 0; i < itineraryList.size(); i++) {
            TripItinerary itinerary = itineraryList.get(i);
            String dayName = getString(R.string.mytrips_detail_itinerary_dayname_pre) + (i+1) + getString(R.string.mytrips_detail_itinerary_dayname_post);
            tabLayout.addTab(tabLayout.newTab().setText(dayName).setTag(itinerary.getId()));
        }

        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


//        TabLayout.Tab tab = tabLayout.getTag("");
//        tab.select();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main2, menu);
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

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private TripDetail tripDetail;

        public SectionsPagerAdapter(FragmentManager fm, TripDetail tripDetail) {
            super(fm);
            this.tripDetail = tripDetail;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new ItineraryFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("itinerary", tripDetail.getItinerary().get(position));
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return tripDetail.getItinerary().size();
        }
    }
}
