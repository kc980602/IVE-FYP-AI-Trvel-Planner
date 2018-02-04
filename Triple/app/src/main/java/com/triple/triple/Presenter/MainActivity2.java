package com.triple.triple.Presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.securepreferences.SecurePreferences;
import com.triple.triple.Presenter.Account.LoginActivity;
import com.triple.triple.Presenter.Home.HomeActivity;
import com.triple.triple.R;

import static java.lang.Thread.sleep;

public class MainActivity2 extends AppCompatActivity {

    public static final String DEFAULT = "N/A";
    private boolean isLogin = false;
    private Context mcontext = MainActivity2.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void startApp() {
        SharedPreferences data = new SecurePreferences(mcontext);
        final String token = data.getString("token", DEFAULT);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                    if (token.equals(DEFAULT)) {
                        Intent i_login = new Intent(MainActivity2.this, LoginActivity.class);
                        startActivity(i_login);
                        finish();
                    } else {
                        Intent i_home = new Intent(MainActivity2.this, HomeActivity.class);
                        startActivity(i_home);
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                }
            }
        };
        timer.start();
    }

}
