package com.triple.triple.Presenter.Registration;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mukesh.countrypicker.fragments.CountryPicker;
import com.mukesh.countrypicker.interfaces.CountryPickerListener;
import com.stepstone.stepper.StepperLayout;
import com.triple.triple.Adapter.RegistrationPagerAdapter;
import com.triple.triple.Interface.ApiInterface;
import com.triple.triple.Interface.DataManager;
import com.triple.triple.Presenter.Account.LoginActivity;
import com.triple.triple.R;
import com.triple.triple.Sync.ApiClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements DataManager {
    final String DATA = "data";
    HashMap<String, String> mData;
    StepperLayout mStepperLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mData = savedInstanceState != null ? (HashMap<String, String>) savedInstanceState.get(DATA) : new HashMap<String, String>();
        mStepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);
        mStepperLayout.setAdapter(new RegistrationPagerAdapter(getSupportFragmentManager(), this));
        setupActionBar();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(DATA, mData);
        super.onSaveInstanceState(outState);
    }

    private void setupActionBar() {
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(R.string.register_title);
        }
    }

    public void saveData(String key, String data) {
        mData.put(key, data);
    }

    public String getData(String key) {
        return mData.get(key);
    }
}
