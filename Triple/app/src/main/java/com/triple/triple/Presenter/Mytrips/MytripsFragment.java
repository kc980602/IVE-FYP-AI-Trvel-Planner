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

import com.triple.triple.Adapter.FragmentAdapter;
import com.triple.triple.Model.Trip;
import com.triple.triple.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MytripsFragment extends Fragment {


    private ViewPager mViewPager;
    private TabLayout tabLayout;

    public MytripsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mytrips, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.container);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        initView();
        return view;
    }

    private void initView() {

        initViewPager();
    }

    private void initViewPager() {
        List<String> titles = new ArrayList<>();
        titles.add(getString(R.string.mytrips_all));
        titles.add(getString(R.string.mytrips_saved));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(1)));

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new MytripsTabFragment());
        fragments.add(new MytripsTabFragment());

        mViewPager.setOffscreenPageLimit(1);

        FragmentAdapter mFragmentAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mFragmentAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabsFromPagerAdapter(mFragmentAdapter);
    }

}
