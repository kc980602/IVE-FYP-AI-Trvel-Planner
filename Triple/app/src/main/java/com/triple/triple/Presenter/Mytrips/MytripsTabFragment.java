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

import java.util.List;

public class MytripsTabFragment extends Fragment {

    public MytripsTabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mytrips_tab, container, false);
        return view;
    }

    private void initView() {

    }

}
