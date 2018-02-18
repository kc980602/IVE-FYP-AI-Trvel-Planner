package com.triple.triple.Presenter.Mytrips;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.triple.triple.Adapter.TripAdapter;
import com.triple.triple.Helper.DrawerUtil;
import com.triple.triple.Model.TripPlanDetail;
import com.triple.triple.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ItineraryActivity extends AppCompatActivity {

    private FragmentTabHost mTabHost;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);
        findViews();
        initView();

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);


    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void initView() {
        toolbar.setTitle(getString(R.string.title_mytrips));
        setSupportActionBar(toolbar);
        
    }


    private View getTabIndicator(Context context, String title) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerviewitem_mytrips_itinerary, null);
//        TextView tv = (TextView) view.findViewById(R.id.tv_tab);
//        tv.setText(title);
        return view;
    }
}
