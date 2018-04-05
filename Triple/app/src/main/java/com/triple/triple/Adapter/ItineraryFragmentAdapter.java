package com.triple.triple.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.triple.triple.Presenter.Mytrips.ItineraryFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 2018/2/19.
 */

public class ItineraryFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();

    public ItineraryFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }

    public static class Holder {
        private final List<Fragment> fragments = new ArrayList<>();
        private FragmentManager manager;

        public Holder(FragmentManager manager) {
            this.manager = manager;
        }

        public Holder add(Fragment f) {
            fragments.add(f);
            return this;
        }

        public ItineraryFragmentAdapter set() {
            return new ItineraryFragmentAdapter(manager, fragments);
        }
    }
}