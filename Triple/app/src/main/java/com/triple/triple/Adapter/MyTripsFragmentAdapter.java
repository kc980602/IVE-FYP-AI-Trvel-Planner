package com.triple.triple.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.triple.triple.Presenter.Mytrips.MytripsTabFragment;

import java.util.List;

public class MyTripsFragmentAdapter extends FragmentStatePagerAdapter {
    private List<String> mTitles;

    public MyTripsFragmentAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        Fragment fragment = new MytripsTabFragment();
        switch (position) {
            case 0:
                bundle.putBoolean("isEnded", false);
            case 1:
                bundle.putBoolean("isEnded", true);
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

}
