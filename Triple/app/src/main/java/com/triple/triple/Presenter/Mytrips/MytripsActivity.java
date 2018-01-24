package com.triple.triple.Presenter.Mytrips;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.triple.triple.R;
import com.triple.triple.Sync.SynchronousGet;
import com.triple.triple.helper.BottomNavigationViewHelper;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static java.lang.Thread.sleep;

public class MytripsActivity extends AppCompatActivity {

    private static final String TAG = "MytripsActivity";
    private static final int ACTIVITY_NUM = 2;

    private Context mcontext = MytripsActivity.this;

    private Toolbar myToolbar;
    private ListView lv_tripPlan;

    private AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytrips);

        lv_tripPlan = (ListView) findViewById(R.id.lv_tripPlan);
        lv_tripPlan.setOnItemClickListener(lv_tripPlanListener);

        String indicator = getIntent().getStringExtra("indicator");
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.setIndicator(indicator);

        setupBottomNavigationView();
        setupToolbar();


        new MytripsActivity.RequestTrip().execute();

    }

    /**
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mcontext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    /**
     * Toolbar setup
     */
    private void setupToolbar() {
        Log.d(TAG, "setupToolbar: setting up Toolbar");
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(R.string.title_mytrips);
        myToolbar.setTitleTextColor(Color.WHITE);
    }

    /**
     * Toolbar setup
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_mytrips, menu);
        return true;
    }

    /**
     * Toolbar "+" listener
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent i_create = new Intent(mcontext, TripCreateActivity.class);
            startActivity(i_create);
            return true;
        }
        return true;
    }

    /**
     * ListView Listener : lv_tripPlan
     */
    AdapterView.OnItemClickListener lv_tripPlanListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView tripPlanId = (TextView) view.findViewById(R.id.tv_tripid);
            String tpid = tripPlanId.getText().toString();
            Intent i_tripPlan = new Intent(mcontext, TripDetailActivity.class);
            i_tripPlan.putExtra("tid", tpid);
            mcontext.startActivity(i_tripPlan);
        }
    };

    /**
     * make get request to obtain trip plan related to user
     */
    private void loadData(ArrayList<HashMap<String, Object>> listData) {
        SimpleAdapter show = new SimpleAdapter(this, listData, R.layout.listviewitem_mytrips,
                new String[]{"tv_tripname", "tv_owner", "tv_tripdate", "image1", "image2"},
                new int[]{R.id.tv_tripname, R.id.tv_owner, R.id.tv_tripdate, R.id.image1, R.id.image2});
        lv_tripPlan.setAdapter(show);
    }

    private class RequestTrip extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            startAnim();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                new SynchronousGet().run();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }


        //        protected void onPostEcute(String result) {
//            try {
//                JSONObject jsonObject = new JSONObject(result);
//                JSONArray DateArray = jsonObject.getJSONArray("data");
//
//                ArrayList<HashMap<String, Object>> listData = new ArrayList<HashMap<String, Object>>();
//                HashMap<String, Object> map = null;
//
////                URL url = new URL("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464");
////                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
////                imageView.setImageBitmap(bmp);
//
//                int[] img = {R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a5, R.drawable.a6};
//                for (int i = 0; i < DateArray.length(); i++) {
//                    JSONObject trip = DateArray.getJSONObject(i);
//
//                    String id = String.valueOf(trip.getInt("id"));
//                    String title = trip.getString("title");
//                    String owner = trip.getString("owner");
//                    String date = trip.getString("visit_date");
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    Calendar c = Calendar.getInstance();
//                    c.setTime(sdf.parse(date));
//                    c.add(Calendar.DATE, trip.getInt("visit_length"));
//                    date += " - " + sdf.format(c.getTime());
//
//                    map = new HashMap<String, Object>();
//                    map.put("tv_tripid", id);
//                    map.put("tv_tripname", title);
//                    map.put("tv_owner", owner);
//                    map.put("tv_tripdate", date);
//                    map.put("image1", img[(int) Math.ceil(Math.random() * 6)]);
//                    map.put("image2", img[(int) Math.ceil(Math.random() * 6)]);
//                    listData.add(map);
//                }
//                loadData(listData);
//                stopAnim();
//            } catch (Exception e) {
//
//            }
//        }
    }
//
//    public String getResponseFromWebServer(String webLink) {
//        InputStream inputStream = null;
//        String result = "";
//        URL url = null;
//        try {
//            url = new URL(webLink);
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//            con.connect();
//            inputStream = con.getInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//            String line = "";
//            while ((line = bufferedReader.readLine()) != null)
//                result += line;
//            inputStream.close();
//        } catch (Exception e) {
//            result = "ERROR: " + e.toString();
//        }
//        return result;
//    }

    public void startAnim() {
        avi.show();
        // or avi.smoothToShow();
    }

    public void stopAnim() {
        avi.hide();
        // or avi.smoothToHide();
    }

}