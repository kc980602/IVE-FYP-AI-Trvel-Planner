package com.triple.triple.Mytrips;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.triple.triple.R;

public class TripCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_create);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
