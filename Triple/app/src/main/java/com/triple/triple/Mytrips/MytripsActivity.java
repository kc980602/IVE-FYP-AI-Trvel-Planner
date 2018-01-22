package com.triple.triple.Mytrips;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.triple.triple.R;
import com.triple.triple.helper.BottomNavigationViewHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class MytripsActivity extends AppCompatActivity {

    private static final String TAG = "MytripsActivity";
    private static final int ACTIVITY_NUM = 2;

    private Context mcontext = MytripsActivity.this;

    private Toolbar myToolbar;
    private ListView lv_tripPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytrips);

        lv_tripPlan = (ListView) findViewById(R.id.lv_tripPlan);
        lv_tripPlan.setOnItemClickListener(lv_tripPlanListener);

        setupBottomNavigationView();
        setupToolbar();
        loadData();

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
    private void loadData() {
        ArrayList<HashMap<String, Object>> listData = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = null;
        int[] img = {R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a5, R.drawable.a6};
        for (int i = 0; i <= 10; i++) {
            map = new HashMap<String, Object>();
            map.put("tv_tripid", "111");
            map.put("tv_tripname", "Fucking trip");
            map.put("tv_owner", "Owner: asshole");
            map.put("tv_tripdate", "1/7/1997 - 1/7/2047");
            map.put("image1", img[(int) Math.ceil(Math.random() * 6)]);
            map.put("image2", img[(int) Math.ceil(Math.random() * 6)]);
            listData.add(map);
        }

        SimpleAdapter show = new SimpleAdapter(this, listData, R.layout.listviewitem_mytrips,
                new String[]{"tv_tripname", "tv_owner", "tv_tripdate", "image1", "image2"},
                new int[]{R.id.tv_tripname, R.id.tv_owner, R.id.tv_tripdate, R.id.image1, R.id.image2});
        lv_tripPlan.setAdapter(show);
    }
}