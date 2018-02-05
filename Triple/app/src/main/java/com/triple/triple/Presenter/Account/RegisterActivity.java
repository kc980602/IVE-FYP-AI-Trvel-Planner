package com.triple.triple.Presenter.Account;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mukesh.countrypicker.fragments.CountryPicker;
import com.mukesh.countrypicker.interfaces.CountryPickerListener;
import com.triple.triple.Model.ResponeMessage;
import com.triple.triple.R;
import com.triple.triple.Sync.Registration;

import java.lang.reflect.Type;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private Context mcontext = RegisterActivity.this;


    private TextInputEditText et_username, et_email, et_password, et_cpassword, et_age, et_gender, et_country;
    private String username, password, cPassword, email, age, gender, country;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(mcontext);
        progressDialog.setMessage(getString(R.string.dialog_progress_title));

        et_username = (TextInputEditText) findViewById(R.id.et_username);
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
    }

    private void setupActionBar() {
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle(R.string.register_title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_account_register, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create:
                requestRegister();
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
            int itemSelected = 0;
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
            int itemSelected = 0;
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

    public void requestRegister() {
        Boolean isSuccess = false;

        if (TextUtils.isEmpty(et_username.getText()) || et_username.getText().toString().length() < 6) {
            et_username.setError(getResources().getString(R.string.register_error_username));
        } else if (checkEmail(et_email.getText().toString())) {
            et_email.setError(getResources().getString(R.string.register_error_email));
        } else if (et_password.getText().toString().equals("") || et_password.getText().toString().length() < 6) {
            et_password.setError(getResources().getString(R.string.register_error_password));
        } else if (!et_password.getText().toString().equals(et_cpassword.getText().toString())) {
            et_password.setError(getResources().getString(R.string.register_error_password2));
        } else if (et_age.getText().toString().equals("")) {
            et_age.setError(getResources().getString(R.string.register_error_age));
        } else if (et_gender.getText().toString().equals("")) {
            et_gender.setError(getResources().getString(R.string.register_error_gender));
        } else if (et_country.getText().toString().equals("")) {
            et_country.setError(getResources().getString(R.string.register_error_country));
        } else {
            isSuccess = true;
        }

        if (isSuccess) {
            username = et_username.getText().toString();
            password = et_password.getText().toString();
            cPassword = et_cpassword.getText().toString();
            email = et_email.getText().toString();
            new RegisterActivity.RequestRegister().execute();
        }
    }

    public boolean checkEmail(String email) {
        return TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private class RequestRegister extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
            Log.d("data", username + ",  " + password + ",  " + cPassword + ",  " + email + ",  " + age + ",  " + gender + ",  " + country);
        }

        @Override
        protected String doInBackground(Void... voids) {
            String respone = "";
            try {
                String url = getResources().getString(R.string.api_prefix) + getResources().getString(R.string.api_registration);
                respone = new Registration().run(url, username, password, cPassword, email, age, gender, country);
            } catch (Exception e) {
            }
            return respone;
        }

        @Override
        protected void onPostExecute(String respone) {
            super.onPostExecute(respone);
            if (!respone.equals("")) {
                String editResult = "[" + respone + "]";
                Type type = new TypeToken<List<ResponeMessage>>() {
                }.getType();
                Gson gson = new Gson();
                try {
                    List<ResponeMessage> list = (List<ResponeMessage>) gson.fromJson(editResult, type);
                    ResponeMessage message = list.get(0);
                    Toast.makeText(mcontext, message.getMessage(), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(mcontext, e.toString(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Intent i = new Intent(mcontext, LoginActivity.class);
                startActivity(i);
                finish();
                Toast.makeText(mcontext, R.string.register_success_create, Toast.LENGTH_LONG).show();
            }
            progressDialog.hide();
        }
    }
}
