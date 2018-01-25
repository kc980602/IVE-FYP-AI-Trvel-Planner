package com.triple.triple.Presenter.Mytrips;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.triple.triple.Model.Mapper.TripAdapter;
import com.triple.triple.Model.Trip;
import com.triple.triple.R;
import com.triple.triple.Sync.SynchronousGet;
import com.triple.triple.helper.BottomNavigationViewHelper;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static java.lang.Thread.sleep;

public class MytripsActivity extends AppCompatActivity {

    private static final String TAG = "MytripsActivity";
    private static final int ACTIVITY_NUM = 2;

    private Context mcontext = MytripsActivity.this;

    private Toolbar myToolbar;
    private ListView lv_tripPlan;

    private AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytrips);

        lv_tripPlan = (ListView) findViewById(R.id.lv_tripPlan);
        lv_tripPlan.setOnItemClickListener(lv_tripPlanListener);

        String indicator = getIntent().getStringExtra("indicator");
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.setIndicator(indicator);

        setupBottomNavigationView();
        setupToolbar();


        new MytripsActivity.RequestTrip().execute();

    }

    /**
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mcontext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    /**
     * Toolbar setup
     */
    private void setupToolbar() {
        Log.d(TAG, "setupToolbar: setting up Toolbar");
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(R.string.title_mytrips);
        myToolbar.setTitleTextColor(Color.WHITE);
    }

    /**
     * Toolbar setup
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_mytrips, menu);
        return true;
    }

    /**
     * Toolbar "+" listener
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent i_create = new Intent(mcontext, TripCreateActivity.class);
            startActivity(i_create);
            return true;
        }
        return true;
    }

    /**
     * ListView Listener : lv_tripPlan
     */
    AdapterView.OnItemClickListener lv_tripPlanListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView tripPlanId = (TextView) view.findViewById(R.id.tv_tripid);
            String tpid = tripPlanId.getText().toString();
            Intent i_tripPlan = new Intent(mcontext, TripDetailActivity.class);
            i_tripPlan.putExtra("tid", tpid);
            mcontext.startActivity(i_tripPlan);
        }
    };

    /**
     * make get request to obtain trip plan related to user
     */
    private void loadData(ArrayList<HashMap<String, Object>> listData) {
        SimpleAdapter show = new SimpleAdapter(this, listData, R.layout.listviewitem_mytrips,
                new String[]{"tv_tripname", "tv_owner", "tv_tripdate", "image1", "image2"},
                new int[]{R.id.tv_tripname, R.id.tv_owner, R.id.tv_tripdate, R.id.image1, R.id.image2});
        lv_tripPlan.setAdapter(show);
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
                String url =  getResources().getString(R.string.api_showTripPlan);
                Log.d(TAG, url);
                respone = new SynchronousGet().run(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return respone;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Type type = new TypeToken<List<Trip>>(){}.getType();
            Gson gson = new Gson();
            List<Trip> trips = (List<Trip>) gson.fromJson(result, type);
            TripAdapter adapter = new TripAdapter(MytripsActivity.this, trips);
            lv_tripPlan.setAdapter(adapter);
            stopAnim();
        }

    }

    public void startAnim() {
        avi.smoothToShow();
    }

    public void stopAnim() {
        avi.smoothToHide();
    }

}