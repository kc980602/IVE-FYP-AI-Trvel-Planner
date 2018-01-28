package com.triple.triple.Presenter.Account;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.constraint.solver.SolverVariable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.triple.triple.Model.ResponeMessage;
import com.triple.triple.R;
import com.triple.triple.Sync.Registration;

import org.angmarch.views.NiceSpinner;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class RegisterActivity extends AppCompatActivity {

    private Context mcontext = RegisterActivity.this;

    private EditText et_username, et_email, et_password, et_cpassword;
    private NiceSpinner sp_age, sp_gender, sp_income;
    private String username, password, cPassword, email, age, gender, income;
    private String[] ageList;
    private String[] genderList;
    private String[] incomeList;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_username = (EditText) findViewById(R.id.et_username);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        et_cpassword = (EditText) findViewById(R.id.et_cpassword);
        sp_age = (NiceSpinner) findViewById(R.id.sp_age);
        sp_gender = (NiceSpinner) findViewById(R.id.sp_gender);
        sp_income = (NiceSpinner) findViewById(R.id.sp_income);
        //load option for spinner
        ageList = getResources().getStringArray(R.array.age);
        genderList = getResources().getStringArray(R.array.gender);
        incomeList = getResources().getStringArray(R.array.income);
        List<String> dataset = new LinkedList<>(Arrays.asList(ageList));
        sp_age.attachDataSource(dataset);
        dataset = new LinkedList<>(Arrays.asList(genderList));
        sp_gender.attachDataSource(dataset);
        dataset = new LinkedList<>(Arrays.asList(incomeList));
        sp_income.attachDataSource(dataset);

        setupActionBar();
    }

    private void setupActionBar() {
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle(R.string.register_title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_account_register, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create:
                requestRegister();
                break;
            case android.R.id.home:
                Intent i_login = new Intent(mcontext, LoginActivity.class);
                startActivity(i_login);
                finish();
                break;
        }
        return true;
    }

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
        } else if (sp_age.getSelectedIndex() == 0) {
            sp_age.setError(getResources().getString(R.string.register_error_age));
        } else if (sp_gender.getSelectedIndex() == 0) {
            sp_gender.setError(getResources().getString(R.string.register_error_gender));
        } else if (sp_income.getSelectedIndex() == 0) {
            sp_income.setError(getResources().getString(R.string.register_error_income));
        } else {
            isSuccess = true;
        }

        if (isSuccess) {
            username = et_username.getText().toString();
            password = et_password.getText().toString();
            cPassword = et_cpassword.getText().toString();
            email = et_email.getText().toString();
            age = String.valueOf(sp_age.getSelectedIndex());
            gender = genderList[sp_gender.getSelectedIndex()];
            income = incomeList[sp_gender.getSelectedIndex()];
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
            dialog = new SpotsDialog(mcontext, R.style.CustomDialog);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String respone = "";
            try {
                String url = getResources().getString(R.string.api_prefix) + getResources().getString(R.string.api_registration);
                respone = new Registration().run(url, username, password, cPassword, email, age, gender, income);
            } catch (Exception e) {
            }
            return respone;
        }

        @Override
        protected void onPostExecute(String respone) {
            super.onPostExecute(respone);
            if (!respone.equals("")) {
                String editResult = "[" + respone + "]";
                Type type = new TypeToken<List<ResponeMessage>>() {}.getType();
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
            dialog.dismiss();
        }
    }
}