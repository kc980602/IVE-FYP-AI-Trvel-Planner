package com.triple.triple.Presenter.Account;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mukesh.countrypicker.fragments.CountryPicker;
import com.mukesh.countrypicker.interfaces.CountryPickerListener;
import com.triple.triple.Helper.UserDataHelper;
import com.triple.triple.Interface.ApiInterface;
import com.triple.triple.Model.User;
import com.triple.triple.R;
import com.triple.triple.Sync.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private Context mcontext = EditProfileActivity.this;


    private TextInputEditText et_username, et_fname, et_lname, et_email, et_password, et_cpassword, et_age, et_gender, et_country;
    private String username, fname, lname, password, cPassword, email, age, gender, country;
    private ProgressDialog progressDialog;
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        progressDialog = new ProgressDialog(mcontext);
        progressDialog.setMessage(getString(R.string.dialog_progress_title));

        et_username = (TextInputEditText) findViewById(R.id.et_username);
        et_fname = (TextInputEditText) findViewById(R.id.et_fname);
        et_lname = (TextInputEditText) findViewById(R.id.et_lname);
        et_email = (TextInputEditText) findViewById(R.id.et_email);
        et_password = (TextInputEditText) findViewById(R.id.et_password);
        et_cpassword = (TextInputEditText) findViewById(R.id.et_cpassword);
        et_age = (TextInputEditText) findViewById(R.id.et_age);
        et_gender = (TextInputEditText) findViewById(R.id.et_gender);
        et_country = (TextInputEditText) findViewById(R.id.et_country);

        et_age.setOnClickListener(et_ageListener);
        et_gender.setOnClickListener(et_genderListener);
        et_country.setOnClickListener(et_countryListener);

        setupActionBar();
        getInfo();
    }

    private void setupActionBar() {
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle("Edit Profile");
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void getInfo(){
        user = UserDataHelper.getUserInfo(mcontext);
        et_username.setText(user.getUsername());
        et_fname.setText(user.getFirst_name());
        et_lname.setText(user.getLast_name());
        et_email.setText(user.getEmail());
        et_age.setText(getResources().getStringArray(R.array.age)[user.getAge()]);
        et_gender.setText(user.getGender());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_profile_edit, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                requestSave();
                break;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
        }
        return true;
    }


    View.OnClickListener et_ageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final String[] singleChoiceItems = getResources().getStringArray(R.array.age);
            int itemSelected = user.getAge();
            new AlertDialog.Builder(mcontext)
                    .setTitle(getString(R.string.register_age_guide))
                    .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            et_age.setText(singleChoiceItems[i].toString());
                            age = String.valueOf(i);
                        }
                    })
                    .setNegativeButton(getString(R.string.dialog_cancel), null)
                    .show();
        }
    };
    View.OnClickListener et_genderListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final String[] singleChoiceItems = getResources().getStringArray(R.array.gender);
            int itemSelected = user.getGender() == "M" ? 1 : 0 ;
            new AlertDialog.Builder(mcontext)
                    .setTitle(getString(R.string.register_gender_guide))
                    .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            et_gender.setText(singleChoiceItems[i].toString());
                            gender = singleChoiceItems[i].toString();
                        }
                    })
                    .setNegativeButton(getString(R.string.dialog_cancel), null)
                    .show();
        }
    };
    View.OnClickListener et_countryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final CountryPicker picker = CountryPicker.newInstance(getString(R.string.register_country_guide));
            picker.setListener(new CountryPickerListener() {
                @Override
                public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                    picker.dismiss();
                    Log.d("country", name + ", " + code + ", " + dialCode + ", " + flagDrawableResID);
                    et_country.setText(name);
                    country = code;
                }
            });
            picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
        }
    };

    public void requestSave() {
        Boolean isSuccess = false;

        if (TextUtils.isEmpty(et_fname.getText())) {
            et_fname.setError(getResources().getString(R.string.register_error_required));
        } else if (TextUtils.isEmpty(et_lname.getText())) {
            et_lname.setError(getResources().getString(R.string.register_error_required));
        } else if (et_age.getText().toString().equals("")) {
            et_age.setError(getResources().getString(R.string.register_error_age));
        } else if (et_gender.getText().toString().equals("")) {
            et_gender.setError(getResources().getString(R.string.register_error_gender));
        } else {
            isSuccess = true;
        }

        if (isSuccess) {
            fname = et_fname.getText().toString();
            lname = et_lname.getText().toString();
            save();
        }
    }

    public void save() {
        progressDialog.show();
        Call<Void> call = apiService.editInfo( fname, lname, gender, age);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 201){
                    Toast.makeText(mcontext, "Successful!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(mcontext, "Error!", Toast.LENGTH_LONG).show();
                    Log.e("respose", String.valueOf(response.code()));
                }
                progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(mcontext, "Somethings goes wrong!", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

}
