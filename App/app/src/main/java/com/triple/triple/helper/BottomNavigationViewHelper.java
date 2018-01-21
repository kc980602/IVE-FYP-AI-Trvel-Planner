package com.triple.triple.helper;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.triple.triple.Home.HomeActivity;
import com.triple.triple.Mytrips.MytripsActivity;
import com.triple.triple.Profile.ProfileActivity;
import com.triple.triple.R;
import com.triple.triple.Search.SearchActivity;

/**
 * Created by Kevin on 2018/1/19.
 */

public class BottomNavigationViewHelper {

    private static final String TAG = "BottomNavigationViewHel";

    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        Log.d(TAG, "setupBottomNavigationView: Setting up BottomNavigationView");
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }
    public static void enableNavigation(final Context context, BottomNavigationViewEx view) {
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_home:
                        Intent i_home = new Intent(context, HomeActivity.class); //ACTIVITY_NUM 0
                        context.startActivity(i_home);
                        break;
                    case R.id.ic_search:
                        Intent i_search = new Intent(context, SearchActivity.class); //ACTIVITY_NUM 1
                        context.startActivity(i_search);
                        break;
                    case R.id.ic_suitcase:
                        Intent i_mytrips = new Intent(context, MytripsActivity.class); //ACTIVITY_NUM 2
                        context.startActivity(i_mytrips);
                        break;
                    case R.id.ic_account:
                        Intent i_account = new Intent(context, ProfileActivity.class); //ACTIVITY_NUM 3
                        context.startActivity(i_account);
                        break;
                }
                return false;
            }
        });
    }
}
