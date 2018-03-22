package com.triple.triple.Presenter.Mytrips;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.triple.triple.Model.Trip;
import com.triple.triple.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MytripsFragment extends Fragment {


    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private MytripsFragment.SectionsPagerAdapter mSectionsPagerAdapter;

    public MytripsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mytrips, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.container);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        return view;
    }

    private void initView() {

        mSectionsPagerAdapter = new MytripsFragment.SectionsPagerAdapter(getActivity().getSupportFragmentManager(), tripDetail);

        mViewPager.setAdapter(mSectionsPagerAdapter);

        ArrayList<List<Trip>> twoTrips = new ArrayList<>();
//        twoTrips.add();
//        twoTrips.add();

        for (int i = 0; i < twoTrips.size(); i++) {
            TripItinerary itinerary = twoTrips.get(i);
            String dayName = getString(R.string.mytrips_detail_itinerary_dayname_pre) + (i + 1) + getString(R.string.mytrips_detail_itinerary_dayname_post);
            tabLayout.addTab(tabLayout.newTab().setText(dayName).setTag(itinerary.getId()));
        }

        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


//        TabLayout.Tab tab = tabLayout.getTag("");
//        tab.select();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<List<Trip>> twoTrips;

        public SectionsPagerAdapter(FragmentManager fm, ArrayList<List<Trip>> twoTrips) {
            super(fm);
            this.twoTrips = twoTrips;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new ItineraryFragment();
            List<Trip> trips = twoTrips.get(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("trips", (Serializable) trips);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return twoTrips.size();
        }
    }

}
