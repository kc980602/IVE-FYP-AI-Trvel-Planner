package com.triple.triple.Presenter.Account;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.securepreferences.SecurePreferences;
import com.triple.triple.Helper.ErrorUtils;
import com.triple.triple.Interface.ApiInterface;
import com.triple.triple.Model.AuthData;
import com.triple.triple.Model.APIError;
import com.triple.triple.Presenter.Registration.RegisterActivity;
import com.triple.triple.R;
import com.triple.triple.Sync.ApiClient;
import com.wang.avi.AVLoadingIndicatorView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText et_username, et_password;
    private ProgressDialog progressDialog;
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.dialog_progress_title));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        et_username = (TextInputEditText) findViewById(R.id.et_username);
        et_password = (TextInputEditText) findViewById(R.id.et_password);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                finish();
            }
        }
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
        String username, password;
        int id = view.getId();
        if (id == R.id.bt_login) {
            username = et_username.getText().toString();
            password = et_password.getText().toString();
            requestLogin(username, password);
        }
        return true;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup:
                startActivityForResult(new Intent(this, RegisterActivity.class), 1);
                return;
            case R.id.link_forget:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;
            default:
                Log.e("action", "Invalid Action");
        }
    }

    public void requestLogin(String username, String password) {
        progressDialog.show();
        Call<AuthData> call = apiService.authenticate(username, password);
        call.enqueue(new Callback<AuthData>() {
            @Override
            public void onResponse(Call<AuthData> call, Response<AuthData> response) {
                if (response.isSuccessful()){
                    AuthData auth = response.body();
                    continueRequestLogin(auth);
                } else {
                    APIError errorMessage = ErrorUtils.parseError(response);
                    stopRequestLogin(errorMessage.getMessage());
                }
            }

            @Override
            public void onFailure(Call<AuthData> call, Throwable t) {
                stopRequestLogin(getString(R.string.login_error_process));
            }
        });

    }

    public void continueRequestLogin(AuthData auth) {
        Gson gson = new Gson();
        //save data using SharedPreferences
        SharedPreferences data = new SecurePreferences(this);
        SharedPreferences.Editor editor = data.edit();
        editor.putString("token", auth.getToken());
        String json = gson.toJson(auth.getUser());
        editor.putString("userInfo", json);
        editor.apply();
        String message = getResources().getString(R.string.login_success) + ", " + auth.getUser().getUsername();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(i);
        System.exit(0);
    }

    public void stopRequestLogin(String message) {
        progressDialog.hide();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
