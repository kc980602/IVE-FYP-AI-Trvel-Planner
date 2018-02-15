package com.triple.triple.Presenter.Mytrips;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.securepreferences.SecurePreferences;
import com.triple.triple.Adapter.TripDayAdapter;
import com.triple.triple.Helper.BottomNavigationViewHelper;
import com.triple.triple.Helper.CalendarHelper;
import com.triple.triple.Model.Trip;
import com.triple.triple.Model.TripDay;
import com.triple.triple.R;
import com.triple.triple.Sync.GetTrip;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TripDetailActivity extends AppCompatActivity {
    private static final String TAG = "TripCreateActivity";

    private Context mcontext = TripDetailActivity.this;


    private ListView lv_tripdaylist;
    private List<TripDay> tripdays = new ArrayList<>();
    private TripDayAdapter adapter;
    private Trip trip;
    private TextView tv_tripdate, tv_tripdaysleftMessage, tv_tripdaysleft;
    private AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytrips_detail);
        initView();
//
//        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
//        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
//
//        for (int i = 0; i <= 30; i++) {
//            ArrayList<HashMap<String, Object>> listData = new ArrayList<HashMap<String, Object>>();
//            HashMap<String, Object> map = null;
//            int[] img = {R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a5, R.drawable.a6};
//            int[] imgRate = {R.drawable.ic_rate0, R.drawable.ic_rate1, R.drawable.ic_rate2, R.drawable.ic_rate3, R.drawable.ic_rate4, R.drawable.ic_rate5};
//            for (int x = 0; x <= 3; x++) {
//                map = new HashMap<String, Object>();
//                map.put("image1", img[(int) Math.ceil(Math.random() * 6)]);
//                map.put("tv_attId", "1234567");
//                map.put("tv_attName", "Temple");
//                map.put("iv_rate", imgRate[(int) Math.ceil(Math.random() * 5)]);
//                map.put("tv_attReview", (int) Math.ceil(Math.random() * 9999) + " Reviews");
//                map.put("tv_attAddress", "China");
//                map.put("tv_attType", "Land Mark");
//                listData.add(map);
//            }
//            TripPlanDetail listDataObject = new TripPlanDetail();
//            String newTabSpec = (i + 1) + "/1";
//
//            listDataObject.setDate(newTabSpec);
//            listDataObject.setListData(listData);
//
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("listDataObject", listDataObject);
//            mTabHost.addTab(mTabHost.newTabSpec(newTabSpec).setIndicator(getTabIndicator(mTabHost.getContext(), newTabSpec)), FragmentTab.class, bundle);
//        }
    }

    private void initView() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        trip = (Trip) bundle.getSerializable("trip");

        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle(trip.getName());
        ab.setElevation(0);

        String indicator = getIntent().getStringExtra("indicator");
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.setIndicator(indicator);
        avi.hide();

        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.nav_trip_card);
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(bottomNavigationViewExListener);
        Menu menu = bottomNavigationViewEx.getMenu();

        tv_tripdate = (TextView) findViewById(R.id.tv_tripdate);
        tv_tripdaysleftMessage = (TextView) findViewById(R.id.tv_tripdaysleftMessage);
        tv_tripdaysleft = (TextView) findViewById(R.id.tv_tripdaysleft);


        tv_tripdate.setText(CalendarHelper.castDateToLocale(trip.getVisit_date()) + " - " + CalendarHelper.castDateToLocale(CalendarHelper.endDate(trip.getVisit_date(), trip.getVisit_length())));
        int dayLeft = CalendarHelper.daysLeft(trip.getVisit_date());
        if (dayLeft == 0) {
            tv_tripdaysleftMessage.setText(getString(R.string.mytrips_detail_daysleft_after));
        } else {
            tv_tripdaysleftMessage.setText(getString(R.string.mytrips_detail_daysleft_before));
        }
        tv_tripdaysleft.setText(String.valueOf(dayLeft));

        prepareTripDays();
        lv_tripdaylist = (ListView) findViewById(R.id.lv_tripdaylist);
        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < tripdays.size(); i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            TripDay tripDay = tripdays.get(i);
            item.put("name", tripDay.getName());
            item.put("desc", tripDay.getDesc());
            items.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(this,
                items, R.layout.listviewitem_mytrips_detail_day, new String[]{"name", "desc"},
                new int[]{R.id.tv_day, R.id.tv_desc});
        lv_tripdaylist.setAdapter(adapter);
    }

    private void prepareTripDays() {
        TripDay tripday = new TripDay();
        tripday.setName("Day" + 1);
        tripday.setDesc(CalendarHelper.castDateToLocaleFull(trip.getVisit_date()));
        tripdays.add(tripday);

        for (int i = 2; i <= trip.getVisit_length(); i++) {
            tripday = new TripDay();
            tripday.setName("Day" + (i));
            tripday.setDesc(CalendarHelper.castDateToLocaleFull(CalendarHelper.endDate(trip.getVisit_date(), i)));
            tripdays.add(tripday);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_mytrips_detail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:

                break;
            case R.id.action_delete:

                break;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                finish();
                break;
        }
        return true;
    }

     private BottomNavigationViewEx.OnNavigationItemSelectedListener bottomNavigationViewExListener = new BottomNavigationViewEx.OnNavigationItemSelectedListener() {
         @Override
         public boolean onNavigationItemSelected(@NonNull MenuItem item) {
             switch (item.getItemId()) {
                 case R.id.action_info:
                     Log.d("aac", "action_info");
                     break;
                 case R.id.action_itenary:
                     Log.d("aac", "action_itenary");
                     break;
                 case R.id.action_invite:
                     Log.d("aac", "action_invite");
                     break;
                 case R.id.action_save:
                     saveTripToLocal();
                     break;
             }
             return false;
         }
     };

    private void saveTripToLocal() {
        List<Trip> savedTrips;
        Type type = new TypeToken<List<Trip>>() {
        }.getType();
        Gson gson = new Gson();
        SharedPreferences data = new SecurePreferences(mcontext);
        SharedPreferences.Editor editor = data.edit();
        String jsonTripList = data.getString("savedTrips", null);
        if (jsonTripList == null || jsonTripList.equals("[]")) {
            savedTrips =  new ArrayList<>();
            savedTrips.add(trip);
        } else {
            savedTrips = (List<Trip>) gson.fromJson(jsonTripList.toString(), type);
            Boolean isFound = false;
            for(Trip tmpTrip: savedTrips){
                if(tmpTrip.getId() == trip.getId()){
                    savedTrips.remove(tmpTrip);
                    savedTrips.add(trip);
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                savedTrips.add(trip);
            }
        }
        String json = gson.toJson(savedTrips);
        editor.putString("savedTrips", json);
        editor.commit();
    }

    private class RequestTripItinerary  extends AsyncTask<Void, Void, String> {
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
                SharedPreferences data = new SecurePreferences(mcontext);
                data.getString("token", "ll");
            } catch (Exception e) {
            }
            stopAnim();
        }
    }

    public void startAnim() {
        avi.smoothToShow();
    }

    public void stopAnim() {
        avi.smoothToHide();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
