package com.triple.triple.Presenter.Profile;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
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
import com.triple.triple.Helper.CheckLogin;
import com.triple.triple.Model.KeyValue;
import com.triple.triple.R;
import com.triple.triple.Sync.GetPreference;
import com.wang.avi.AVLoadingIndicatorView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TravelStyleActivity extends AppCompatActivity {

    private Context mcontext = TravelStyleActivity.this;

    private Toolbar myToolbar;
    private AVLoadingIndicatorView avi;
    private ImageView[] imageViewsList = new ImageView[5];
    private TextView[] textViewsList = new TextView[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_style);

        int[] imageViewIdList = {R.id.iv_1, R.id.iv_2, R.id.iv_3, R.id.iv_4};
        int[] textViewIdList = {R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4};
        for (int i = 0; i <= 3; i++) {
            imageViewsList[i] = (ImageView) findViewById(imageViewIdList[i]);
            textViewsList[i] = (TextView) findViewById(textViewIdList[i]);
        }

        String indicator = getIntent().getStringExtra("indicator");
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.setIndicator(indicator);

        setupActionBar();
        if (CheckLogin.directLogin(mcontext)) {
            finish();
        } else {
            new TravelStyleActivity.RequestPreference().execute();
        }
    }

    private void setupActionBar() {
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle(R.string.title_travelstyle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_travel_style, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                NavUtils.navigateUpFromSameTask(this);
                break;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
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
                String url = getResources().getString(R.string.api_prefix) + getResources().getString(R.string.api_profile_preference);
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
                Type type = new TypeToken<List<KeyValue>>() {
                }.getType();
                Gson gson = new Gson();
                List<KeyValue> keyValueList = (List<KeyValue>) gson.fromJson(respone, type);
                List<KeyValue> keyValueListSorted = new ArrayList<>();
//                int i;
//                int index;
//                float max = 0;
//                KeyValue tmpMax = new KeyValue();
//                for (int j = 0; j <= 3; j++) {
//                    max = Float.parseFloat(keyValueList.get(0).getValue());
//                    index = 0;
//                    for (i = 1; i < keyValueList.size(); i++) {
//                        if (max < Float.parseFloat(keyValueList.get(i).getValue())) {
//                            max = Float.parseFloat(keyValueList.get(i).getValue());
//                            tmpMax = keyValueList.get(i);
//                            index = i;
//                        }
//                    }
//                    keyValueListSorted.add(j, tmpMax);
//                    array[index] = Integer.MIN_VALUE;
//
//                    System.out.println("Largest " + j +  " : " + large[j]);
//                }



                for (int i = 0; i <= 5; i++) {
                    String filename = "preference_" + keyValueList.get(i).getKey();
                    if (keyValueList.get(i).getKey().equals("60+_traveller")) {
                        filename = "preference_60_traveller";
                    }
                    imageViewsList[i].setImageResource(mcontext.getResources().getIdentifier(filename, "drawable", getPackageName()));
                    String key = keyValueList.get(i).getKey().replace('_', ' ');
                    String titile = key.substring(0,1).toUpperCase() + key.substring(1).toLowerCase();
                    textViewsList[i].setText(titile);
                }
            } catch (Exception e) {
                //Toast.makeText(mcontext, R.string.login_error_process, Toast.LENGTH_SHORT).show();
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
