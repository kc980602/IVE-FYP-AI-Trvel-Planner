package com.triple.triple;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final Intent main = new Intent(this, MainActivity.class);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(main);
                    finish();
                }
            }
        };
        timer.start();
    }
}
