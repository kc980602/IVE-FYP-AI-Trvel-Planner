package com.triple.triple.Presenter.Mytrips;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.triple.triple.R;
import com.triple.triple.helper.HideKeyboardHelper;

import java.util.Calendar;

public class TripCreateActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = "TripCreateActivity";

    private Context mcontext = TripCreateActivity.this;

    private Toolbar myToolbar;
    private EditText et_tripname, et_tripdate;
    private AutoCompleteTextView actw_detination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_create);
        Log.d(TAG, "onCreate: starting.");
        setupActionBar();

        et_tripname = (EditText) findViewById(R.id.et_tripname);
        et_tripdate = (EditText) findViewById(R.id.et_tripdate);

        et_tripname.setOnFocusChangeListener(et_tripnameListener);
        et_tripdate.setOnFocusChangeListener(et_tripdateListener);

        actw_detination = (AutoCompleteTextView) findViewById(R.id.actw_detination);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.destination));
        actw_detination.setAdapter(adapter);
    }


    /**
     * Action Bar setup
     */
    private void setupActionBar() {
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle(R.string.mytrips_create_title);
    }

    /**
     *  Action Bar  menu setup
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: setting up Toolbar");
        getMenuInflater().inflate(R.menu.toolbar_mytrips_create, menu);
        return true;
    }

    /**
     * Edit Text Listener : et_tripname
     */
    View.OnFocusChangeListener et_tripnameListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                HideKeyboardHelper.hideKeyboard(v);
            }
        }
    };

    /**
     * Edit Text Listener : et_tripdate
     */
    View.OnFocusChangeListener et_tripdateListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        TripCreateActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        }
    };

    /**
     * onDateSet : DatePickerDialog
     */
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        String date = dayOfMonth + "/" + (++monthOfYear) + "/" + year + " - " + dayOfMonthEnd + "/" + (++monthOfYearEnd) + "/" + yearEnd;
        et_tripdate.setText(date);
        et_tripdate.clearFocus();
    }

    public boolean onButtonCreateClicked(View view) {
        int id = view.getId();
        if (id == R.id.bt_create) {
            requestCreateTrip();
        }
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create:
                Log.d(TAG, "onOptionsItemSelected: Create,  send to process");
                requestCreateTrip();
                break;
            case android.R.id.home:
                Log.d(TAG, "onOptionsItemSelected: android.R.id.home, return to parent activity");
                Intent i_create = new Intent(mcontext, MytripsActivity.class);
                startActivity(i_create);
                break;
        }
        return true;
    }

    /**
     * Process use case logic opertion
     */
    public boolean requestCreateTrip() {
        Log.d(TAG, "requestCreateTrip: sending request");
        Boolean isSuccess = false;

        if (TextUtils.isEmpty(et_tripname.getText())) {
            et_tripname.setError("Trip name is required!");
        } else if (TextUtils.isEmpty(actw_detination.getText())) {
            actw_detination.setError("Trip detination is required!");
        } else {
            isSuccess = true;
        }

        if (isSuccess) {
            Toast.makeText(mcontext, R.string.mytrips_create_success, Toast.LENGTH_SHORT).show();
            Intent i_create = new Intent(mcontext, MytripsActivity.class);
            startActivity(i_create);
        }

        return true;
    }


}
