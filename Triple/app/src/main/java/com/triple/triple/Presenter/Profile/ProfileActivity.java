package com.triple.triple.Presenter.Profile;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.securepreferences.SecurePreferences;
import com.triple.triple.Presenter.Account.LoginActivity;
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

        bt_signin = (Button) findViewById(R.id.bt_signin);
        bt_preference = (Button) findViewById(R.id.bt_preference);
        bt_signout = (Button) findViewById(R.id.bt_signout);
        SharedPreferences data = new SecurePreferences(mcontext);
        String token = data.getString("token", DEFAULT);
        if (token.equals(DEFAULT)) {
            bt_preference.setVisibility(View.INVISIBLE);
            bt_signout.setVisibility(View.INVISIBLE);
            bt_signin.setVisibility(View.VISIBLE);
        } else {
            bt_preference.setVisibility(View.VISIBLE);
            bt_signout.setVisibility(View.VISIBLE);
            bt_signin.setVisibility(View.INVISIBLE);
        }
    }

    public boolean onButtonSignOutClicked(View view) {
        int id = view.getId();
        if (id == R.id.bt_signout) {
            SharedPreferences data = new SecurePreferences(mcontext);
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

    public boolean onButtonPreferenceClicked(View view) {
        int id = view.getId();
        if (id == R.id.bt_preference) {
            Intent i = new Intent(mcontext, TravelStyleActivity.class);
            startActivity(i);
            finish();
        }
        return true;
    }

    public boolean onButtonSignInClicked(View view) {
        int id = view.getId();
        if (id == R.id.bt_signin) {
            Intent i = new Intent(mcontext, LoginActivity.class);
            startActivity(i);
            finish();
        }
        return true;
    }
}
