package com.triple.triple.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.triple.triple.Presenter.Mytrips.ItineraryFragment;

/**
 * Created by Kevin on 2018/2/19.
 */

public class ItineraryFragmentAdapter extends FragmentPagerAdapter {

    public ItineraryFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

//    @Override
//    public Fragment getItem(int position) {
//        // getItem is called to instantiate the fragment for the given page.
//        // Return a PlaceholderFragment (defined as a static inner class below).
//        return ItineraryFragment.newInstance(position + 1);
//    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}