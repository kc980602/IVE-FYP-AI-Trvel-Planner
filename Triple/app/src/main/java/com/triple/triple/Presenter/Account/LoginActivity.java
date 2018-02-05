package com.triple.triple.Presenter.Account;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.securepreferences.SecurePreferences;
import com.triple.triple.Model.AuthData;
import com.triple.triple.Presenter.Home.HomeActivity;
import com.triple.triple.R;
import com.triple.triple.Sync.Authentication;
import com.wang.avi.AVLoadingIndicatorView;

import java.lang.reflect.Type;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private Context mcontext = LoginActivity.this;

    private Toolbar myToolbar;
    private AVLoadingIndicatorView avi;
    private AlertDialog dialog;
    private EditText et_username, et_password;
    private String username, password;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(mcontext);
        progressDialog.setMessage(getString(R.string.dialog_progress_title));

        setupToolbar();
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
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
            new LoginActivity.RequestLogin().execute();
        }
        return true;
    }

    public boolean onButtonRegisterClicked(View view) {
        int id = view.getId();
        if (id == R.id.link_signup) {
            Intent i_register = new Intent(mcontext, RegisterActivity.class);
            startActivity(i_register);
        }
        return true;
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
                String editResult = "[" + result + "]";
                Type type = new TypeToken<List<AuthData>>() {}.getType();
                Gson gson = new Gson();
                try {
                    List<AuthData> authList = (List<AuthData>) gson.fromJson(editResult, type);
                    AuthData auth = authList.get(0);
                    auth.toString();
                    //save data using SharedPreferences
                    SharedPreferences data = new SecurePreferences(mcontext);
                    SharedPreferences.Editor editor = data.edit();
                    editor.putString("token", auth.getToken());
                    editor.putInt("userid", auth.getUser().getId());
                    editor.putString("username", auth.getUser().getUsername());
                    editor.commit();
                    String message = getResources().getString(R.string.login_success) + ", " + auth.getUser().getUsername();
                    Toast.makeText(mcontext, message, Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
                    Intent i_home = new Intent(mcontext, HomeActivity.class);
                    startActivity(i_home);
                    finish();
                } catch (Exception e) {
                    Toast.makeText(mcontext, R.string.login_error_process, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
