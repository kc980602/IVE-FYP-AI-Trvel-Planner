package com.triple.triple.Presenter.Mytrips;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.triple.triple.Adapter.TripDayAdapter;
import com.triple.triple.Helper.BottomNavigationViewHelper;
import com.triple.triple.Helper.CalendarHelper;
import com.triple.triple.Model.Trip;
import com.triple.triple.Model.TripDay;
import com.triple.triple.R;

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

        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.nav_trip_card);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mcontext, bottomNavigationViewEx);
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


    private void setupBottomNavigationView() {

    }

    private void setupActionBar() {

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


    private View getTabIndicator(Context context, String title) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_layout_mytrips_detail, null);
//        TextView tv = (TextView) view.findViewById(R.id.tv_tab);
//        tv.setText(title);
        return view;
    }

//    private class RequestTrip extends AsyncTask<Void, Void, String> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            startAnim();
//        }
//
//        @Override
//        protected String doInBackground(Void... voids) {
//            String respone = "Error";
//            try {
//                String url =  getResources().getString(R.string.api_showTripPlan);
//                Log.d(TAG, url);
//                respone = new GetTrip().run(url);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return respone;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            Type type = new TypeToken<List<Trip>>(){}.getType();
//            Gson gson = new Gson();
//            List<Trip> trips = (List<Trip>) gson.fromJson(result, type);
//            TripAdapter adapter = new TripAdapter(MytripsActivity.this, trips);
//            lv_tripPlan.setAdapter(adapter);
//            stopAnim();
//        }
//
//    }
//
//    public void startAnim() {
//        avi.smoothToShow();
//    }
//
//    public void stopAnim() {
//        avi.smoothToHide();
//    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
