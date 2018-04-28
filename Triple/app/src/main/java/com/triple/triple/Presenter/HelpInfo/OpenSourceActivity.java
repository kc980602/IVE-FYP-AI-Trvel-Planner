package com.triple.triple.Presenter.HelpInfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.triple.triple.R;

public class OpenSourceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_source);

        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle(R.string.title_open_source);
    }
}
