package com.triple.triple.Presenter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;


import com.triple.triple.Presenter.Mytrips.GeneratingDialog;
import com.triple.triple.R;

public class NewMainActivity extends AppCompatActivity {


    private GeneratingDialog generatingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);
        generatingDialog = GeneratingDialog.instance("ABC", Integer.valueOf(2), "2018-03-01", Integer.valueOf(3));
        generatingDialog.show(getFragmentManager(), "GeneratingDialog");
    }



}
