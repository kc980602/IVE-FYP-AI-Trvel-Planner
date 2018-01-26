package com.triple.triple.Presenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.triple.triple.Presenter.Account.LoginActivity;
import com.triple.triple.Presenter.Home.HomeActivity;
import com.triple.triple.Presenter.Search.SearchActivity;
import com.triple.triple.R;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    private boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startApp();

    }

    public void startApp() {
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                    if (isLogin) {
                        Intent i_home = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(i_home);
                        finish();
                    } else {
                        Intent i_login = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(i_login);
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
