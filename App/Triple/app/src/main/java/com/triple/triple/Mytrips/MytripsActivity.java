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

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.triple.triple.Home.HomeActivity;
import com.triple.triple.R;
import com.triple.triple.helper.BottomNavigationViewHelper;

public class MytripsActivity extends AppCompatActivity {

    private static final String TAG = "MytripsActivity";
    private static final int ACTIVITY_NUM = 2;

    private Context mcontext = MytripsActivity.this;

    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytrips);

        setupBottomNavigationView();
        setupToolbar();
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
     *Toolbar setup
     */
    private void setupToolbar() {
        Log.d(TAG, "setupToolbar: setting up Toolbar");
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(R.string.title_mytrips);
        myToolbar.setTitleTextColor(Color.WHITE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_mytrips, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent i_create = new Intent(mcontext, TripCreateActivity.class);
            startActivity(i_create);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
