package com.triple.triple.Presenter.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.triple.triple.Model.Preference;
import com.triple.triple.R;
import com.triple.triple.Sync.GetPreference;
import com.wang.avi.AVLoadingIndicatorView;

import java.lang.reflect.Type;
import java.util.List;

public class PreferenceActivity extends AppCompatActivity {

    private Context mcontext = PreferenceActivity.this;

    private Toolbar myToolbar;
    private AVLoadingIndicatorView avi;
    private ImageView[] imageViewsList = new ImageView[5];
    private TextView[] textViewsList = new TextView[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        int[] imageViewIdList = {R.id.iv_1, R.id.iv_2, R.id.iv_3, R.id.iv_4, R.id.iv_5};
        int[] textViewIdList = {R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4, R.id.tv_5};
        for (int i = 0; i <= 4; i++) {
            imageViewsList[i] = (ImageView) findViewById(imageViewIdList[i]);
            textViewsList[i] = (TextView) findViewById(textViewIdList[i]);
        }

        String indicator = getIntent().getStringExtra("indicator");
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.setIndicator(indicator);

        setupToolbar();

        new PreferenceActivity.RequestPreference().execute();
    }

    private void setupToolbar() {
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
            Intent i_home = new Intent(mcontext, ProfileActivity.class);
            startActivity(i_home);
            finish();
            return true;
        }
        return true;
    }

    private class RequestPreference extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            startAnim();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String respone = "";
            try {
                String url = getResources().getString(R.string.api_prefix) + getResources().getString(R.string.api_trip_get);
                respone = new GetPreference().run(url, mcontext);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return respone;
        }

        @Override
        protected void onPostExecute(String respone) {
            super.onPostExecute(respone);
            Log.d("aac", respone);
            try {
                Type type = new TypeToken<List<Preference>>() {
                }.getType();
                Gson gson = new Gson();
                List<Preference> preferenceList = (List<Preference>) gson.fromJson(respone, type);
                for (int i = 0; i <= 4; i++) {
                    String filename = "preference_" + preferenceList.get(i).getKey();
                    if (preferenceList.get(i).getKey().equals("60+_traveller")) {
                        filename = "preference_60_traveller";
                    }
                    imageViewsList[i].setImageResource(mcontext.getResources().getIdentifier(filename, "drawable", getPackageName()));
                    int percent = (int) Math.floor(Double.parseDouble(preferenceList.get(i).getValue()) * 100);
                    textViewsList[i].setText(percent + "% you will like it");
                }
            } catch (Exception e) {
                Toast.makeText(mcontext, R.string.login_error_data, Toast.LENGTH_SHORT).show();
            }
            stopAnim();
        }
    }

    public void startAnim() {
        avi.show();
    }

    public void stopAnim() {
        avi.hide();
    }
}
