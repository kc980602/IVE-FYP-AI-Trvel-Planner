package com.triple.triple.Presenter.Mytrips;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.securepreferences.SecurePreferences;
import com.triple.triple.Adapter.TripAdapter;
import com.triple.triple.Helper.CheckLogin;
import com.triple.triple.Helper.DrawerUtil;
import com.triple.triple.Model.Trip;
import com.triple.triple.Presenter.MainActivity;
import com.triple.triple.R;
import com.triple.triple.Sync.GetTrip;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MytripsActivity extends AppCompatActivity {

    private static final String TAG = "MytripsActivity";
    private static final int ACTIVITY_NUM = 2;

    private Context mcontext = MytripsActivity.this;

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ListView lv_tripPlan;
    private RecyclerView rv_trips;
    private AVLoadingIndicatorView avi;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TabLayout tabLayout;

    private List<Trip> allTrips = new ArrayList<>();
    private List<Trip> savedTrips = new ArrayList<>();
    private TripAdapter adapter_allTrips, adapter_savedTrips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytrips);
        findViews();
        initView();

        if (CheckLogin.directLogin(mcontext)) {
            finish();
        } else {
            refreshData();
        }
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        rv_trips = (RecyclerView) findViewById(R.id.rv_trips);
    }

    private void initView() {
        toolbar.setTitle(getString(R.string.title_mytrips));
        setSupportActionBar(toolbar);
        DrawerUtil.getDrawer(this, toolbar);

        String indicator = getIntent().getStringExtra("indicator");

        avi.setIndicator(indicator);

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.mytrips_all)).setTag("all"));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.mytrips_saved)).setTag("saved"));
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setOnTabSelectedListener(tabLayoutListener);

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        adapter_allTrips = new TripAdapter(MytripsActivity.this, allTrips, "false");
        adapter_savedTrips = new TripAdapter(MytripsActivity.this, savedTrips, "true");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_trips.setHasFixedSize(true);
        rv_trips.setLayoutManager(mLayoutManager);
        rv_trips.setItemAnimator(new DefaultItemAnimator());
        rv_trips.setAdapter(adapter_allTrips);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_mytrips, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent i_create = new Intent(mcontext, TripCreateActivity.class);
                startActivity(i_create);
                break;
        }
        return true;
    }

    private TabLayout.OnTabSelectedListener tabLayoutListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switch (tab.getTag().toString()) {
                case "all":
                    rv_trips.swapAdapter(adapter_allTrips, false);
                    break;
                case "saved":
                    rv_trips.swapAdapter(adapter_savedTrips, false);
                    break;
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };


    private void refreshData() {
        new MytripsActivity.RequestTrip().execute();
        Type type = new TypeToken<List<Trip>>() {
        }.getType();
        Gson gson = new Gson();
        SharedPreferences data = new SecurePreferences(mcontext);
        String jsonTripList = data.getString("savedTrips", null);
        if (jsonTripList != null) {
            List<Trip> newTrips = (List<Trip>) gson.fromJson(jsonTripList.toString(), type);
            savedTrips.clear();
            for (int i=0; i<newTrips.size(); i++) {
                savedTrips.add(newTrips.get(i));
            }
            adapter_savedTrips.notifyDataSetChanged();
        }
    }

    private class RequestTrip extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            startAnim();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String respone = "Error";
            try {
                String url = getResources().getString(R.string.api_prefix) + getResources().getString(R.string.api_trip_get);
                respone = new GetTrip().run(url, mcontext);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return respone;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                Log.d("aac", result);
                JSONObject jsonObject = new JSONObject(result);
                JSONArray DateArray = jsonObject.getJSONArray("data");
                Type type = new TypeToken<List<Trip>>() {
                }.getType();
                Gson gson = new Gson();
                List<Trip> newTrips = (List<Trip>) gson.fromJson(DateArray.toString(), type);
                allTrips.clear();
                for (int i=0; i<newTrips.size(); i++) {
                    allTrips.add(newTrips.get(i));
                }
                adapter_allTrips.notifyDataSetChanged();
            } catch (Exception e) {
//                new MytripsActivity.RequestTrip().execute();
            }
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
            stopAnim();
        }
    }

    public void startAnim() {
        avi.show();
    }

    public void stopAnim() {
        avi.hide();
    }

}
