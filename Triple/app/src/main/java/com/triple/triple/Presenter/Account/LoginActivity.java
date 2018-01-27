package com.triple.triple.Presenter.Account;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.triple.triple.Presenter.MainActivity;
import com.triple.triple.R;
import com.triple.triple.Sync.Authentication;
import com.wang.avi.AVLoadingIndicatorView;

import java.lang.reflect.Type;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private Context mcontext = LoginActivity.this;

    private Toolbar myToolbar;
    private AVLoadingIndicatorView avi;
    private AlertDialog dialog;
    private EditText et_username, et_password;
    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupToolbar();
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
    }

    /**
     * Toolbar setup
     */
    private void setupToolbar() {
        Log.d(TAG, "setupToolbar: setting up Toolbar");
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("");
    }

    /**
     * Toolbar setup
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_account_login, menu);
        return true;
    }

    /**
     * Toolbar "+" listener
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_close) {
            Intent i_home = new Intent(mcontext, HomeActivity.class);
            startActivity(i_home);
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

    private class RequestLogin extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new SpotsDialog(mcontext, R.style.CustomDialog);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String respone = "Error";
            try {
                String url = getResources().getString(R.string.api_login);
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
                dialog.dismiss();
                Toast.makeText(mcontext, R.string.login_error_data, Toast.LENGTH_SHORT).show();
            } else {
                String editResult = "[" + result + "]";

                Type type = new TypeToken<List<AuthData>>() {
                }.getType();
                Gson gson = new Gson();
                try {
                    List<AuthData> authList = (List<AuthData>) gson.fromJson(editResult, type);
                    AuthData auth = authList.get(0);
                    auth.toString();
                    //save data using SharedPreferences
//                    SharedPreferences data = new SecurePreferences(mcontext);
                    SharedPreferences data = getSharedPreferences("user", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = data.edit();
                    editor.putString("token", auth.getToken());
                    editor.putInt("userid", auth.getUser().getId());
                    editor.putString("username", auth.getUser().getUsername());
                    editor.commit();
                    String message = getResources().getString(R.string.login_success) + ", " + auth.getUser().getUsername();
                    Toast.makeText(mcontext, message, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
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
