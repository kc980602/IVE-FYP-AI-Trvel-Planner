package com.triple.triple.Presenter.Account;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.triple.triple.Presenter.Home.HomeActivity;
import com.triple.triple.R;
import com.triple.triple.Sync.Authentication;
import com.triple.triple.Sync.SynchronousGet;
import com.wang.avi.AVLoadingIndicatorView;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private Context mcontext = LoginActivity.this;

    private Toolbar myToolbar;
    private AVLoadingIndicatorView avi;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupToolbar();
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
                String url =  getResources().getString(R.string.api_login);
                Log.d(TAG, url);
                respone = new Authentication().run(url);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return respone;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
            Toast.makeText(mcontext, R.string.login_success, Toast.LENGTH_SHORT).show();
            Log.d(TAG, result);
        }

    }
}
