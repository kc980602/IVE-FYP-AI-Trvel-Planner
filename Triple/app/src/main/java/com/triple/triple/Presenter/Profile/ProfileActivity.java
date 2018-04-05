package com.triple.triple.Presenter.Profile;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.securepreferences.SecurePreferences;
import com.triple.triple.Presenter.Account.LoginActivity;
import com.triple.triple.Presenter.MainActivity;
import com.triple.triple.R;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";
    private static final int ACTIVITY_NUM = 3;
    public static final String DEFAULT = "N/A";

    private Context mcontext = ProfileActivity.this;

    private Button bt_signin, bt_preference, bt_signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

    }

}
