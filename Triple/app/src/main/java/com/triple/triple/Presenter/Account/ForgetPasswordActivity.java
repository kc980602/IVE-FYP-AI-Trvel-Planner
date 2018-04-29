package com.triple.triple.Presenter.Account;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.triple.triple.Interface.ApiInterface;
import com.triple.triple.R;
import com.triple.triple.Sync.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity {

    private Context mcontext = ForgetPasswordActivity.this;

    private TextInputEditText et_username, et_email;
    private ProgressDialog progressDialog;
    private Toolbar toolbar;

    private String username, email;
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        progressDialog = new ProgressDialog(mcontext);
        progressDialog.setMessage(getString(R.string.dialog_progress_title));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        et_username = (TextInputEditText) findViewById(R.id.et_username);
        et_email = (TextInputEditText) findViewById(R.id.et_email);

        //setupActionBar();
        toolbar.setTitle(R.string.attraction_comment_title);
        setSupportActionBar(toolbar);
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
        } else if (checkEmail(et_email.getText().toString())) {
            et_email.setError(getResources().getString(R.string.register_error_email));
        } else {
            isSuccess = true;
        }

        if (isSuccess) {
            username = et_username.getText().toString();
            email = et_email.getText().toString();
            //new ForgetPasswordActivity.RequestForgetPassword().execute();
            requestForgetPassword();
        }
    }

    public void requestForgetPassword() {
        progressDialog.show();

        Call<Void> call = apiService.forgetPassword(username, email);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 201) {
                    try {
                        Toast.makeText(mcontext, "Please check your Email for reset password.", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(mcontext, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("error", "Error occur." + response.code() + " " + response.errorBody());
                    progressDialog.hide();
                    Intent intent = new Intent(mcontext, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(mcontext, "Unexpected error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }

    private boolean checkEmail(String email) {
        return TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
