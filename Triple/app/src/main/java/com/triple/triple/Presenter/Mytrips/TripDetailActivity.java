package com.triple.triple.Presenter.Mytrips;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itheima.roundedimageview.RoundedImageView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.securepreferences.SecurePreferences;
import com.squareup.picasso.Picasso;
import com.triple.triple.Adapter.TripDayAdapter;
import com.triple.triple.Helper.DateTimeHelper;
import com.triple.triple.Helper.Token;
import com.triple.triple.Interface.ApiInterface;
import com.triple.triple.Model.Trip;
import com.triple.triple.Model.TripDay;
import com.triple.triple.Model.TripDetail;
import com.triple.triple.Model.TripItinerary;
import com.triple.triple.R;
import com.triple.triple.Sync.ApiClient;
import com.triple.triple.Sync.GetTrip;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripDetailActivity extends AppCompatActivity {
    private static final String TAG = "TripCreateActivity";

    private Context mcontext = TripDetailActivity.this;


    private ListView lv_tripdaylist;
    private BottomNavigationViewEx bottomNavigationViewEx, bottomNavigationViewEx_all, bottomNavigationViewEx_saved;
    private List<TripDay> tripdays = new ArrayList<>();
    private TripDayAdapter adapter;
    private Trip trip;
    private TripDetail tripDetail;

    private TextView tv_tripdate, tv_tripdaysleftMessage, tv_tripdaysleft;
    private AVLoadingIndicatorView avi;
    private Boolean isSaved;
    private CardView cv_trip;
    private ImageView image;
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytrips_detail);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        isSaved = (Boolean) bundle.getBoolean("isSaved");
        trip = (Trip) bundle.getSerializable("trip");

        findView();
        initView();
//        new TripDetailActivity.RequestTripItinerary().execute();
        requestTripItinerary();
    }

    private void findView() {
        cv_trip = (CardView) findViewById(R.id.cv_trip);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        bottomNavigationViewEx_all = (BottomNavigationViewEx) findViewById(R.id.nav_trip_card);
        bottomNavigationViewEx_saved = (BottomNavigationViewEx) findViewById(R.id.nav_trip_card_saved);
        tv_tripdate = (TextView) findViewById(R.id.tv_tripdate);
        tv_tripdaysleftMessage = (TextView) findViewById(R.id.tv_tripdaysleftMessage);
        tv_tripdaysleft = (TextView) findViewById(R.id.tv_tripdaysleft);
        lv_tripdaylist = (ListView) findViewById(R.id.lv_tripdaylist);
        image = (ImageView) findViewById(R.id.image);
    }

    private void initView() {
        String addOn = isSaved ? " (" + getString(R.string.mytrips_offline) + ")" : "";
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle(trip.getName() + addOn);
        ab.setElevation(0);
        String indicator = getIntent().getStringExtra("indicator");

        cv_trip.setVisibility(View.INVISIBLE);

        avi.setIndicator(indicator);
        avi.hide();

        if (isSaved) {
            bottomNavigationViewEx_all.setVisibility(View.INVISIBLE);
            bottomNavigationViewEx_saved.setVisibility(View.VISIBLE);
            bottomNavigationViewEx = bottomNavigationViewEx_saved;
        } else {
            bottomNavigationViewEx_all.setVisibility(View.VISIBLE);
            bottomNavigationViewEx_saved.setVisibility(View.INVISIBLE);
            bottomNavigationViewEx = bottomNavigationViewEx_all;
        }
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(bottomNavigationViewExListener);
        Menu menu = bottomNavigationViewEx.getMenu();

        tv_tripdate.setText(DateTimeHelper.castDateToLocale(trip.getVisit_date()) + " - " + DateTimeHelper.castDateToLocale(DateTimeHelper.endDate(trip.getVisit_date(), trip.getVisit_length())));
        int dayLeft = DateTimeHelper.daysLeft(trip.getVisit_date());
        if (dayLeft == 0) {
            tv_tripdaysleftMessage.setText(getString(R.string.mytrips_detail_daysleft_after));
        } else {
            tv_tripdaysleftMessage.setText(getString(R.string.mytrips_detail_daysleft_before));
        }
        tv_tripdaysleft.setText(String.valueOf(dayLeft));

        adapter = new TripDayAdapter(mcontext, tripdays);
        lv_tripdaylist.setAdapter(adapter);
        Picasso.with(mcontext)
                .load(trip.getImage())
                .placeholder(R.drawable.image_null_tran)
                .into(image);
    }

    private ListView.OnItemClickListener lv_tripdaylistListener = new ListView.OnItemClickListener() {
        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_mytrips_detail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Log.d("TripDetailActivity", tripDetail.toString());
                break;
            case R.id.action_delete:

                break;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
//                setResult(MytripsActivity.MESSAGE_REFRESH_DATA);
                finish();
                break;
        }
        return true;
    }

    private BottomNavigationViewEx.OnNavigationItemSelectedListener bottomNavigationViewExListener = new BottomNavigationViewEx.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_info:
                    break;
                case R.id.action_itenary:
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("tripDetail", tripDetail);
                    Intent i = new Intent(mcontext, ItineraryActivity.class);
                    i.putExtras(bundle);
                    startActivity(i);
                    break;
                case R.id.action_invite:
                    break;
                case R.id.action_save:
                    View view = getWindow().getDecorView().findViewById(android.R.id.content);
                    saveTripToLocal();
                    Snackbar.make(view, getString(R.string.mytrips_detail_tripsaved), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.snackbar_ok), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            }).show();
                    break;
                case android.R.id.home:
                    NavUtils.navigateUpFromSameTask(TripDetailActivity.this);
                    finish();
                    break;
            }
            return false;
        }
    };

    private void saveTripToLocal() {
        List<Trip> savedTrips;
        Type type = new TypeToken<List<Trip>>() {
        }.getType();
        Gson gson = new Gson();
        SharedPreferences data = new SecurePreferences(mcontext);
        SharedPreferences.Editor editor = data.edit();
        String jsonTripList = data.getString("savedTrips", null);
        if (jsonTripList == null || jsonTripList.equals("[]")) {
            savedTrips = new ArrayList<>();
            savedTrips.add(trip);
        } else {
            savedTrips = (List<Trip>) gson.fromJson(jsonTripList.toString(), type);
            Boolean isFound = false;
            for (Trip tmpTrip : savedTrips) {
                if (tmpTrip.getId() == trip.getId()) {
                    savedTrips.remove(tmpTrip);
                    savedTrips.add(trip);
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                savedTrips.add(trip);
            }
        }
        String json = gson.toJson(savedTrips);
        editor.putString("savedTrips", json);
        editor.commit();
    }

    private class RequestTripItinerary extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            startAnim();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String respone = "Error";
            try {
                String url = getResources().getString(R.string.api_prefix) + getResources().getString(R.string.api_trip_get_detail) + trip.getId();
                respone = new GetTrip().run(url, mcontext);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return respone;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                Type type = new TypeToken<TripDetail>() {
                }.getType();
                Gson gson = new Gson();
                tripDetail = (TripDetail) gson.fromJson(jsonObject.toString(), type);
                List<TripItinerary> itineraryList = tripDetail.getItinerary();

                for (int i = 0; i < itineraryList.size(); i++) {
                    TripDay tripday = new TripDay();
                    tripday.setId(itineraryList.get(i).getId());
                    tripday.setName("Day" + (i + 1));
                    tripday.setDesc(DateTimeHelper.castDateToLocaleFull(itineraryList.get(i).getVisit_date()));
                    tripdays.add(tripday);
                }
                adapter.notifyDataSetChanged();
                cv_trip.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                View view = getWindow().getDecorView().findViewById(android.R.id.content);
                Snackbar.make(view, getString(R.string.mytrips_error), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.snackbar_ok), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();
                new TripDetailActivity.RequestTripItinerary().execute();
            }
            stopAnim();
        }
    }

    public void requestTripItinerary(){
        startAnim();

        String token = "Bearer ";
        token += Token.getToken(mcontext);

        Call<TripDetail> call = apiService.listTripByUser(token, trip.getId());
        call.enqueue(new Callback<TripDetail>() {
            @Override
            public void onResponse(Call<TripDetail> call, Response<TripDetail> response) {
                if (response.body() != null) {
                    try{
                        tripDetail = response.body();
                        List<TripItinerary> itineraryList = tripDetail.getItinerary();

                        for (int i = 0; i < itineraryList.size(); i++) {
                            TripDay tripday = new TripDay();
                            tripday.setId(itineraryList.get(i).getId());
                            tripday.setName("Day" + (i + 1));
                            tripday.setDesc(DateTimeHelper.castDateToLocaleFull(itineraryList.get(i).getVisit_date()));
                            tripdays.add(tripday);
                        }
                        adapter.notifyDataSetChanged();
                        cv_trip.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        View view = getWindow().getDecorView().findViewById(android.R.id.content);
                        Snackbar.make(view, getString(R.string.mytrips_error), Snackbar.LENGTH_LONG)
                                .setAction(getString(R.string.snackbar_ok), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                }).show();
                    }
                    stopAnim();
                } else {
                    Log.d("error", "Empty response.");
                    stopAnim();
                }

                stopAnim();
            }

            @Override
            public void onFailure(Call<TripDetail> call, Throwable t) {
                Log.e("error", t.toString());
                stopAnim();
            }
        });
    }

    public void startAnim() {
        avi.smoothToShow();
    }

    public void stopAnim() {
        avi.smoothToHide();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
