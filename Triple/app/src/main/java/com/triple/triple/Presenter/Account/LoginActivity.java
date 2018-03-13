package com.triple.triple.Presenter.Account;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.securepreferences.SecurePreferences;
import com.triple.triple.Interface.ApiInterface;
import com.triple.triple.Model.AuthData;
import com.triple.triple.Presenter.MainActivity;
import com.triple.triple.R;
import com.triple.triple.Sync.ApiClient;
import com.triple.triple.Sync.Authentication;
import com.wang.avi.AVLoadingIndicatorView;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private Context mcontext = LoginActivity.this;

    private Toolbar myToolbar;
    private AVLoadingIndicatorView avi;
    private AlertDialog dialog;
    private TextInputEditText et_username, et_password;
    private String username, password;
    private ProgressDialog progressDialog;
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(mcontext);
        progressDialog.setMessage(getString(R.string.dialog_progress_title));

        setupToolbar();
        et_username = (TextInputEditText) findViewById(R.id.et_username);
        et_password = (TextInputEditText) findViewById(R.id.et_password);
    }

    private void setupToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_account_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_close) {
            NavUtils.navigateUpFromSameTask(this);
            finish();
            return true;
        }
        return true;
    }

    public boolean onButtonLoginClicked(View view) {
        int id = view.getId();
        if (id == R.id.bt_login) {
            username = et_username.getText().toString();
            password = et_password.getText().toString();
//            new LoginActivity.RequestLogin().execute();
            requestLogin(username, password);
        }
        return true;
    }

    public void onClick(View view) {
        int id = view.getId();
        Intent indent = new Intent();
        switch (id) {
            case R.id.link_signup:
                indent.setClass(mcontext, RegisterActivity.class);
                break;
            case R.id.link_forget:
                indent.setClass(mcontext, ForgetPasswordActivity.class);
                break;
        }
        startActivity(indent);
    }

    private class RequestLogin extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String respone = "Error";
            try {
                String url = getResources().getString(R.string.api_prefix) + getResources().getString(R.string.api_login);
                respone = new Authentication().run(url, username, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return respone;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.equals("Error")) {
                progressDialog.hide();
                Toast.makeText(mcontext, R.string.login_error_data, Toast.LENGTH_SHORT).show();
            } else {
                Type type = new TypeToken<AuthData>() {
                }.getType();
                Gson gson = new Gson();
                try {
                    AuthData auth = (AuthData) gson.fromJson(result, type);
                    auth.toString();
                    //save data using SharedPreferences
                    SharedPreferences data = new SecurePreferences(mcontext);
                    SharedPreferences.Editor editor = data.edit();
                    editor.putString("token", auth.getToken());
                    String json = gson.toJson(auth.getUser());
                    editor.putString("userInfo", json);
                    editor.commit();
                    String message = getResources().getString(R.string.login_success) + ", " + auth.getUser().getUsername();
                    Toast.makeText(mcontext, message, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Intent indent = new Intent(mcontext, MainActivity.class);
                    startActivity(indent);
                    finish();
                } catch (Exception e) {
                    Toast.makeText(mcontext, R.string.login_error_process, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void requestLogin(String username, String password){
        progressDialog.show();

        Call<AuthData> call = apiService.authenticate(username, password);
        call.enqueue(new Callback<AuthData>() {
            @Override
            public void onResponse(Call<AuthData> call, Response<AuthData> response) {
                try{
                    AuthData auth = response.body();
                    continueRequestLogin(auth);
                } catch (Exception e){
                    Toast.makeText(mcontext, R.string.login_error_process, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AuthData> call, Throwable t) {
                stopRequestLogin();
            }
        });

    }

    public void continueRequestLogin(AuthData auth){
        Gson gson = new Gson();
        auth.toString();
        //save data using SharedPreferences
        SharedPreferences data = new SecurePreferences(mcontext);
        SharedPreferences.Editor editor = data.edit();
        editor.putString("token", auth.getToken());
        String json = gson.toJson(auth.getUser());
        editor.putString("userInfo", json);
        editor.commit();
        String message = getResources().getString(R.string.login_success) + ", " + auth.getUser().getUsername();
        Toast.makeText(mcontext, message, Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        Intent indent = new Intent(mcontext, MainActivity.class);
        startActivity(indent);
        finish();
    }

    public void stopRequestLogin(){
        progressDialog.hide();
        Toast.makeText(mcontext, R.string.login_error_data, Toast.LENGTH_SHORT).show();
    }

}
