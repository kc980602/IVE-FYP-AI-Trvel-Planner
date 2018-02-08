package com.triple.triple.Presenter.Mytrips;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.triple.triple.Adapter.TripAdapter;
<<<<<<< HEAD
import com.triple.triple.Adapter.TripRecyclerViewAdapter;
=======
import com.triple.triple.Helper.CheckLogin;
>>>>>>> bdf916ba0035b9fdf96b7b049c9d0400dbcfd411
import com.triple.triple.Model.Trip;
import com.triple.triple.R;
import com.triple.triple.Sync.GetTrip;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Thread.sleep;

public class MytripsActivity extends AppCompatActivity {

    private static final String TAG = "MytripsActivity";
    private static final int ACTIVITY_NUM = 2;

    private Context mcontext = MytripsActivity.this;

    private Toolbar myToolbar;
    private ListView lv_tripPlan;
    private RecyclerView rv_trips;

    private TripRecyclerViewAdapter adapter;
    private AVLoadingIndicatorView avi;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytrips);

//        lv_tripPlan = (ListView) findViewById(R.id.lv_tripPlan);
//        lv_tripPlan.setOnItemClickListener(lv_tripPlanListener);



        String indicator = getIntent().getStringExtra("indicator");
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.setIndicator(indicator);

        setupActionBar();
        RecyclerView rv_trips = (RecyclerView) findViewById(R.id.rv_trips);
        rv_trips.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(mcontext);
        rv_trips.setLayoutManager(llm);
        rv_trips.setAdapter(adapter);
        new MytripsActivity.RequestTrip().execute();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new MytripsActivity.RequestTrip().execute();
            }
        });

        if (CheckLogin.directLogin(mcontext)) {
            finish();
        } else {
            new MytripsActivity.RequestTrip().execute();
        }
    }

    private void setupActionBar() {
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle(R.string.title_mytrips);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_mytrips, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent i_create = new Intent(mcontext, TripCreateActivity.class);
                startActivity(i_create);
                break;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
        }
        return true;
    }

    /**
     * ListView Listener : lv_tripPlan
     */
//    AdapterView.OnItemClickListener lv_tripPlanListener = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            TextView tripPlanId = (TextView) view.findViewById(R.id.tv_tripid);
//            TextView tripPlanName = (TextView) view.findViewById(R.id.tv_tripname);
//            Intent i_tripPlan = new Intent(mcontext, TripDetailActivity.class);
//            i_tripPlan.putExtra("tid", tripPlanId.getText().toString());
//            i_tripPlan.putExtra("name", tripPlanName.getText().toString());
//            mcontext.startActivity(i_tripPlan);
//        }
//    };

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
                List<Trip> trips = (List<Trip>) gson.fromJson(DateArray.toString(), type);
//                TripAdapter adapter = new TripAdapter(MytripsActivity.this, trips);
//                lv_tripPlan.setAdapter(adapter);
                adapter = new TripRecyclerViewAdapter(MytripsActivity.this, trips);

            } catch (Exception e) {
                Toast.makeText(mcontext, R.string.mytrips_error, Toast.LENGTH_SHORT).show();
            }
            stopAnim();
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    public void startAnim() {
        avi.show();
    }

    public void stopAnim() {
        avi.hide();
    }

}
