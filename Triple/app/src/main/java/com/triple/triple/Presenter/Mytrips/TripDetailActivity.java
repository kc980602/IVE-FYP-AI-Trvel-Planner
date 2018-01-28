package com.triple.triple.Presenter.Mytrips;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.triple.triple.R;
import com.triple.triple.Model.TripPlanDetail;

import java.util.ArrayList;
import java.util.HashMap;

public class TripDetailActivity extends AppCompatActivity {
    private static final String TAG = "TripCreateActivity";

    private Context mcontext = TripDetailActivity.this;

    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);
        setupActionBar();



        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        for (int i = 0; i <= 30; i++) {
            ArrayList<HashMap<String, Object>> listData = new ArrayList<HashMap<String, Object>>();
            HashMap<String, Object> map = null;
            int[] img = {R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a5, R.drawable.a6};
            int[] imgRate = {R.drawable.ic_rate0, R.drawable.ic_rate1, R.drawable.ic_rate2, R.drawable.ic_rate3, R.drawable.ic_rate4, R.drawable.ic_rate5};
            for (int x = 0; x <= 3; x++) {
                map = new HashMap<String, Object>();
                map.put("image1", img[(int) Math.ceil(Math.random() * 6)]);
                map.put("tv_attId", "1234567");
                map.put("tv_attName", "Temple");
                map.put("iv_rate", imgRate[(int) Math.ceil(Math.random() * 5)]);
                map.put("tv_attReview", (int) Math.ceil(Math.random() * 9999) + " Reviews");
                map.put("tv_attAddress", "China");
                map.put("tv_attType", "Land Mark");
                listData.add(map);
            }
            TripPlanDetail listDataObject = new TripPlanDetail();
            String newTabSpec = (i + 1) + "/1";

            listDataObject.setDate(newTabSpec);
            listDataObject.setListData(listData);

            Bundle bundle = new Bundle();
            bundle.putSerializable("listDataObject", listDataObject);
            mTabHost.addTab(mTabHost.newTabSpec(newTabSpec).setIndicator(getTabIndicator(mTabHost.getContext(), newTabSpec)), FragmentTab.class, bundle);
        }
    }

    /**
     * Action Bar setup
     */
    private void setupActionBar() {
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        Intent i= getIntent();
        ab.setTitle(i.getStringExtra("name"));
        getSupportActionBar().setElevation(0);
    }

    /**
     * Action Bar  menu setup
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: setting up Toolbar");
        getMenuInflater().inflate(R.menu.toolbar_mytrips_detail, menu);
        return true;
    }

    private View getTabIndicator(Context context, String title) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_layout_mytrips_detail, null);
        TextView tv = (TextView) view.findViewById(R.id.tv_tab);
        tv.setText(title);
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
}
