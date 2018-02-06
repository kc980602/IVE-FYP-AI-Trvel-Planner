package com.triple.triple.Presenter.Account;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.triple.triple.Model.ResponeMessage;
import com.triple.triple.R;
import com.triple.triple.Sync.ForgetPassword;

import java.lang.reflect.Type;
import java.util.List;

public class ForgetPasswordActivity extends AppCompatActivity {

    private Context mcontext = ForgetPasswordActivity.this;

    private TextInputEditText et_username;
    private ProgressDialog progressDialog;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        progressDialog = new ProgressDialog(mcontext);
        progressDialog.setMessage(getString(R.string.dialog_progress_title));

        et_username = (TextInputEditText) findViewById(R.id.et_username);

        setupActionBar();
    }

    private void setupActionBar() {
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle(R.string.forget_password_title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_forget_password, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reset:
                requestRegister();
                break;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
        }
        return true;
    }

    public void onClick(View view) {
        int id = view.getId();
        Intent indent = new Intent();
        switch (id) {
            case R.id.bt_reset:
                requestRegister();
        }
    }

    public void requestRegister() {
        Boolean isSuccess = false;

        if (TextUtils.isEmpty(et_username.getText()) || et_username.getText().toString().length() < 6) {
            et_username.setError(getResources().getString(R.string.register_error_username));
        } else {
            isSuccess = true;
        }

        if (isSuccess) {
            username = et_username.getText().toString();
            new ForgetPasswordActivity.RequestForgetPassword().execute();
        }
    }

    private class RequestForgetPassword extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String respone = "Error";
            try {
                String url = getResources().getString(R.string.api_prefix) + getResources().getString(R.string.api_forget_password);
                respone = new ForgetPassword().run(url, username);
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
                progressDialog.hide();
                Intent intent = new Intent(mcontext, LoginActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(mcontext, R.string.mytrips_create_success, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
