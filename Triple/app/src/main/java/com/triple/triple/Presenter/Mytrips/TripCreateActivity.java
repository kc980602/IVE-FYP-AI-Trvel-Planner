package com.triple.triple.Presenter.Mytrips;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.triple.triple.Helper.DateTimeHelper;
import com.triple.triple.Helper.CheckLogin;
import com.triple.triple.Helper.SystemPropertyHelper;
import com.triple.triple.Helper.UserDataHelper;
import com.triple.triple.Interface.ApiInterface;
import com.triple.triple.Model.City;
import com.triple.triple.Model.TripDetail;
import com.triple.triple.R;
import com.triple.triple.Sync.ApiClientDuration;
import com.triple.triple.Helper.HideKeyboardHelper;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.util.Calendar;

import me.rohanpeshkar.filterablelistdialog.FilterableListDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripCreateActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = "TripCreateActivity";

    private Context mcontext = TripCreateActivity.this;

    private Toolbar myToolbar;
    private TextInputEditText et_tripname, et_tripdate, et_detination;
    private CheckBox cb_generate;
    private ProgressDialog progressDialog;

    private String tripname, tripdateStart, dateCount, destination, generate;
    ApiInterface apiService = ApiClientDuration.getClient().create(ApiInterface.class);
    private TripDetail tripDetail;
    private GeneratingDialog generatingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytrips_create);
        findViews();
        setupActionBar();

        et_tripname.setOnFocusChangeListener(et_tripnameListener);
        et_tripdate.setOnFocusChangeListener(et_tripdateListener);
        et_detination.setOnFocusChangeListener(et_detinationListener);

        if (CheckLogin.directLogin(mcontext)) {
            finish();
        }
    }

    private void findViews() {
        et_tripname = (TextInputEditText) findViewById(R.id.et_tripname);
        et_tripdate = (TextInputEditText) findViewById(R.id.et_tripdate);
        et_detination = (TextInputEditText) findViewById(R.id.et_detination);
        cb_generate = (CheckBox) findViewById(R.id.cb_generate);
    }

    private void setupActionBar() {
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle(R.string.mytrips_create_title);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_mytrips_create, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create:
                requestCreateTrip();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    View.OnFocusChangeListener et_tripnameListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                HideKeyboardHelper.hideKeyboard(v);
            }
        }
    };

    View.OnFocusChangeListener et_detinationListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                FilterableListDialog.create(mcontext, SystemPropertyHelper.getSystemPropertyCityName(mcontext),
                        new FilterableListDialog.OnListItemSelectedListener() {
                            @Override
                            public void onItemSelected(String item) {
                                City city = SystemPropertyHelper.getSystemPropertyByCityName(mcontext, item);
                                et_detination.setText(city.getName() + ", " + city.getCountry());
                                destination = String.valueOf(city.getId());
                            }
                        }).show();
                et_detination.clearFocus();
            }
        }
    };

    View.OnFocusChangeListener et_tripdateListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            HideKeyboardHelper.hideKeyboard(v);
            if (hasFocus) {
                Calendar now = Calendar.getInstance();
                now.add(Calendar.DAY_OF_MONTH, 1);
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        TripCreateActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setMinDate(now);
                dpd.setMaxDate(DateTimeHelper.twoYearsLater());
                dpd.show(getFragmentManager(), "Datepickerdialog");
                et_detination.clearFocus();
            }
        }
    };


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        et_tripdate.clearFocus();
        String date = dayOfMonth + "/" + (++monthOfYear) + "/" + year + " - " + dayOfMonthEnd + "/" + (++monthOfYearEnd) + "/" + yearEnd;
        LocalDate Start = new LocalDate(year, monthOfYear, dayOfMonth);
        LocalDate End = new LocalDate(yearEnd, monthOfYearEnd, dayOfMonthEnd);
        if (Start.compareTo(End) == 1 || End.compareTo(Start) == -1) {
            Toast.makeText(mcontext, R.string.mytrips_create_error_date, Toast.LENGTH_LONG).show();
        } else {
            Days days = Days.daysBetween(Start, End);
            et_tripdate.setText(date);
            dateCount = String.valueOf(days.getDays() + 1 > 7 ? 7 : days.getDays() + 1);
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

    public void requestCreateTrip() {
        Boolean isSuccess = false;

        if (TextUtils.isEmpty(et_tripname.getText())) {
            et_tripname.setError("Trip name is required!");
        } else if (TextUtils.isEmpty(et_tripdate.getText())) {
            et_tripdate.setError("Trip date is required!");
        } else if (TextUtils.isEmpty(et_detination.getText())) {
            et_detination.setError("Trip destination is required!");
        } else {
            isSuccess = true;
        }

        if (isSuccess) {
            tripname = et_tripname.getText().toString();
            generate = String.valueOf((cb_generate.isChecked()) ? 1 : 0);
            createTrip();
        }
    }

    public void createTrip() {
        generatingDialog = GeneratingDialog.instance(tripname, Integer.valueOf(destination), tripdateStart, Integer.valueOf(dateCount));
        generatingDialog.show(getFragmentManager(), "GeneratingDialog");
        String token = "Bearer ";
        token += UserDataHelper.getToken(mcontext);
        Call<TripDetail> call = apiService.createTrip(token, tripname, tripdateStart, dateCount, destination, "1");
        call.enqueue(new Callback<TripDetail>() {
            @Override
            public void onResponse(Call<TripDetail> call, Response<TripDetail> response) {
                Log.d("ReturnResponse", String.valueOf(response.code()));

                if (response.body() == null) {
                    stopCreateTrip();
                } else {
                    Log.d("ReturnResponse", String.valueOf(response.code()));
                    tripDetail = response.body();
                    continueCreateTrip();
                }
            }

            @Override
            public void onFailure(Call<TripDetail> call, Throwable t) {
                Log.d("ReturnResponse", t.getMessage());
                stopCreateTrip();
            }
        });
    }

    public void continueCreateTrip() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("tripDetail", tripDetail);
        bundle.putBoolean("isNew", true);
        Intent intent = new Intent(mcontext, TripDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        Toast.makeText(mcontext, R.string.mytrips_create_success, Toast.LENGTH_SHORT).show();
        finish();
        generatingDialog.dismiss();
    }

    public void stopCreateTrip() {
        View view = getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(view, getString(R.string.mytrips_create_error_process), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.snackbar_ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
        generatingDialog.dismiss();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
