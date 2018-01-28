package com.triple.triple.Presenter.Mytrips;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.triple.triple.R;
import com.triple.triple.Sync.CreateTrip;
import com.triple.triple.helper.HideKeyboardHelper;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.util.Calendar;

public class TripCreateActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = "TripCreateActivity";

    private Context mcontext = TripCreateActivity.this;

    private Toolbar myToolbar;
    private EditText et_tripname, et_tripdate;
    private AutoCompleteTextView actw_detination;
    private CheckBox cb_generate;

    private String tripname, tripdateStart, dateCount, destination, generate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_create);
        Log.d(TAG, "onCreate: starting.");
        setupActionBar();

        et_tripname = (EditText) findViewById(R.id.et_tripname);
        et_tripdate = (EditText) findViewById(R.id.et_tripdate);
        actw_detination = (AutoCompleteTextView) findViewById(R.id.actw_detination);
        cb_generate = (CheckBox) findViewById(R.id.cb_generate);

        et_tripname.setOnFocusChangeListener(et_tripnameListener);
        et_tripdate.setOnFocusChangeListener(et_tripdateListener);

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
     * Action Bar  menu setup
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
                dpd.setMinDate(now);
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        }
    };

    /**
     * onDateSet : DatePickerDialog
     */
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        Log.d("onDateSet", "i am in");
        et_tripdate.clearFocus();
        String date = dayOfMonth + "/" + (++monthOfYear) + "/" + year + " - " + dayOfMonthEnd + "/" + (++monthOfYearEnd) + "/" + yearEnd;
        LocalDate Start = new LocalDate(year, monthOfYear, dayOfMonth);
        LocalDate End = new LocalDate(yearEnd, monthOfYearEnd, dayOfMonthEnd);
        if (Start.compareTo(End) == 1 || End.compareTo(Start) == -1) {
            Toast.makeText(mcontext, R.string.mytrips_create_error_date, Toast.LENGTH_LONG).show();
        } else {
            et_tripdate.setText(date);
            Days days = Days.daysBetween(Start, End);
            dateCount = String.valueOf(days.getDays());
            tripdateStart = year + "-" + monthOfYear + "-" + dayOfMonth;
        }
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
                finish();
                break;
        }
        return true;
    }

    /**
     * Process use case logic opertion
     */
    public void requestCreateTrip() {
        Boolean isSuccess = false;

        if (TextUtils.isEmpty(et_tripname.getText())) {
            et_tripname.setError("Trip name is required!");
        } else if (TextUtils.isEmpty(et_tripdate.getText())) {
            et_tripdate.setError("Trip date is required!");
        } else if (TextUtils.isEmpty(actw_detination.getText())) {
            actw_detination.setError("Trip destination is required!");
        } else {
            isSuccess = true;
        }

        if (isSuccess) {
            tripname = et_tripname.getText().toString();
            destination = actw_detination.getText().toString();
            generate = String.valueOf(cb_generate.isChecked());
            new TripCreateActivity.RequestCreateTrip().execute();
        }
    }

    private class RequestCreateTrip extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String respone = "Error";
            try {
                String url = getResources().getString(R.string.api_prefix) + getResources().getString(R.string.api_trip_create);
                respone = new CreateTrip().run(url, mcontext, tripname, tripdateStart, dateCount, destination, generate);
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
            return respone;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (!result.equals("201")) {
                Toast.makeText(mcontext, R.string.mytrips_create_error_process, Toast.LENGTH_SHORT).show();
            } else {
                Intent i_home = new Intent(mcontext, MytripsActivity.class);
                startActivity(i_home);
                finish();
                Toast.makeText(mcontext, R.string.mytrips_create_success, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
