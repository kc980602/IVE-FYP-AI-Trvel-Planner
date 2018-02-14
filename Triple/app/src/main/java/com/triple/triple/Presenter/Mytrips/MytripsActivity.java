package com.triple.triple.Presenter.Mytrips;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

    private List<Trip> trips = new ArrayList<>();
    private TripAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytrips);

        initView();

        if (CheckLogin.directLogin(mcontext)) {
            finish();
        } else {
            new MytripsActivity.RequestTrip().execute();
        }
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.title_mytrips));
        setSupportActionBar(toolbar);
        DrawerUtil.getDrawer(this, toolbar);

        String indicator = getIntent().getStringExtra("indicator");
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.setIndicator(indicator);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new MytripsActivity.RequestTrip().execute();
            }
        });

        rv_trips = (RecyclerView) findViewById(R.id.rv_trips);
        adapter = new TripAdapter(MytripsActivity.this, trips);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_trips.setHasFixedSize(true);
        rv_trips.setLayoutManager(mLayoutManager);
        rv_trips.setItemAnimator(new DefaultItemAnimator());
        rv_trips.setAdapter(adapter);
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
                JSONObject jsonObject = new JSONObject(result);
                JSONArray DateArray = jsonObject.getJSONArray("data");
                Type type = new TypeToken<List<Trip>>() {
                }.getType();
                Gson gson = new Gson();
                List<Trip> newTrips = (List<Trip>) gson.fromJson(DateArray.toString(), type);
                for (int i=0; i<newTrips.size(); i++) {
                    trips.add(newTrips.get(i));
                }
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                new MytripsActivity.RequestTrip().execute();
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
