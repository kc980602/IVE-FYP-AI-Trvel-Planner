package com.triple.triple.Presenter.Profile;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.triple.triple.R;
import com.triple.triple.helper.BottomNavigationViewHelper;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";
    private static final int ACTIVITY_NUM = 3;

    private Context mcontext = ProfileActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setupBottomNavigationView();
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

    public boolean onButtonSignOutClicked(View view) {
        int id = view.getId();
        if (id == R.id.bt_signout) {
            SharedPreferences data = getSharedPreferences("user", Context.MODE_PRIVATE);
            data.edit().clear().commit();
            PackageManager packageManager = mcontext.getPackageManager();
            Intent intent = packageManager.getLaunchIntentForPackage(mcontext.getPackageName());
            ComponentName componentName = intent.getComponent();
            Intent mainIntent = IntentCompat.makeRestartActivityTask(componentName);
            mcontext.startActivity(mainIntent);
            System.exit(0);
        }
        return true;
    }

}
