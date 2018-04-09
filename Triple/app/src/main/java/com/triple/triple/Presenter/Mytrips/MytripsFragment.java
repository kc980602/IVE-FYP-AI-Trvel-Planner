package com.triple.triple.Presenter.Mytrips;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.triple.triple.Adapter.MyTripsFragmentAdapter;
import com.triple.triple.Adapter.TripAdapter;
import com.triple.triple.Helper.CheckLogin;
import com.triple.triple.Helper.UserDataHelper;
import com.triple.triple.Model.Trip;
import com.triple.triple.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

public class MytripsFragment extends Fragment {
    private View view;
    private Fragment fragment = this;
    private AVLoadingIndicatorView avi;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TripAdapter adapter;

    private List<Trip> trips = new ArrayList<>();
    private RecyclerView rv_trips;
    private Context mcontext;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private MytripsTabFragment fragment_ongo;
    private MytripsTabFragment fragment_ended;

    public MytripsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mytrips, container, false);

        mViewPager = (ViewPager) view.findViewById(R.id.container);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);


        if (!UserDataHelper.checkTokenExist(mcontext)) {
            CheckLogin.directLogin(mcontext);
        } else {
            initView();
            setHasOptionsMenu(true);

        }
        return view;
    }

    private void initView() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        List<String> titles = new ArrayList<>();
        titles.add(getString(R.string.mytrips_ongoing));
        titles.add(getString(R.string.mytrips_ended));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(1)));

        mViewPager.setOffscreenPageLimit(2);

        MyTripsFragmentAdapter mFragmentAdapter = new MyTripsFragmentAdapter(getFragmentManager(), titles);
        mViewPager.setAdapter(mFragmentAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabsFromPagerAdapter(mFragmentAdapter);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_mytrips, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(getContext(), TripCreateActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mcontext = context;
    }
}
