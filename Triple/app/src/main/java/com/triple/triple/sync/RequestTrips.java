package com.triple.triple.sync;

import android.os.AsyncTask;
import android.widget.SimpleAdapter;

import com.triple.triple.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Kevin on 2018/1/23.
 */

public class RequestTrips {
    private class RequestTrip extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... values) {
            String response = getResponseFromWebServer(values[0]);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray DateArray = jsonObject.getJSONArray("data");

                ArrayList<HashMap<String, Object>> listData = new ArrayList<HashMap<String, Object>>();
                HashMap<String, Object> map = null;

//                URL url = new URL("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464");
//                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                imageView.setImageBitmap(bmp);

                int[] img = {R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a5, R.drawable.a6};
                for (int i = 0; i < DateArray.length(); i++) {
                    JSONObject trip = DateArray.getJSONObject(i);

                    String id = String.valueOf(trip.getInt("id"));
                    String title = trip.getString("title");
                    String owner = trip.getString("owner");
                    String date = trip.getString("visit_date");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar c = Calendar.getInstance();
                    c.setTime(sdf.parse(date));
                    c.add(Calendar.DATE, trip.getInt("visit_length"));
                    date += " - " + sdf.format(c.getTime());

                    map = new HashMap<String, Object>();
                    map.put("tv_tripid", id);
                    map.put("tv_tripname", title);
                    map.put("tv_owner", owner);
                    map.put("tv_tripdate", date);
                    map.put("image1", img[(int) Math.ceil(Math.random() * 6)]);
                    map.put("image2", img[(int) Math.ceil(Math.random() * 6)]);
                    listData.add(map);
                }
//                SimpleAdapter show = new SimpleAdapter(this, listData, R.layout.listviewitem_mytrips,
//                        new String[]{"tv_tripname", "tv_owner", "tv_tripdate", "image1", "image2"},
//                        new int[]{R.id.tv_tripname, R.id.tv_owner, R.id.tv_tripdate, R.id.image1, R.id.image2});
//                lv_tripPlan.setAdapter(show);
            } catch (Exception e) {

            }
        }
    }


    public String getResponseFromWebServer(String webLink) {
        InputStream inputStream = null;
        String result = "";
        URL url = null;
        try {
            url = new URL(webLink);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            //Get Request
            con.setRequestMethod("GET");
            con.connect();
            // Get response string from inputstream of the connection
            inputStream = con.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = bufferedReader.readLine()) != null)
                result += line;
            inputStream.close();
        } catch (Exception e) {
            result = "ERROR: " + e.toString();
        }
        return result;
    }
}
