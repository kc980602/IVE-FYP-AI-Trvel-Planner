package com.triple.triple.Presenter.Mytrips;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
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
import com.wang.avi.AVLoadingIndicatorView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripDetailActivity extends AppCompatActivity {
    private static final String TAG = "TripDetailActivity";

    private Context mcontext = TripDetailActivity.this;
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    private ListView lv_tripdaylist;
    private BottomNavigationViewEx bottomNavigationViewEx, bottomNavigationViewEx_all, bottomNavigationViewEx_saved;
    private List<TripDay> tripdays = new ArrayList<>();
    private TripDayAdapter adapter;
    private TripDetail tripDetail;

    private TextView tv_tripdate, tv_tripdaysleftMessage, tv_tripdaysleft;
    private AVLoadingIndicatorView avi;
    private Boolean isSaved;
    private CardView cv_trip;
    private ImageView image;
    private ActionBar ab;
    private int tripid;
    private TextView tv_tripdestination;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytrips_detail);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        isSaved = (Boolean) bundle.getBoolean("isSaved");
        tripid = bundle.getInt("tripid");
        findView();
        initView();
        if (isSaved) {
//            getTripFromLocal();
            requestTripItinerary();
        } else {
            requestTripItinerary();
        }
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
        tv_tripdestination = (TextView) findViewById(R.id.tv_tripdestination);
        image = (ImageView) findViewById(R.id.image);
    }

    private void initView() {
        ab = getSupportActionBar();
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

        adapter = new TripDayAdapter(mcontext, tripdays);
        lv_tripdaylist.setAdapter(adapter);
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
                break;
            case R.id.action_delete:

                break;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
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
        List<TripDetail> savedTrips;
        Type type = new TypeToken<List<Trip>>() {
        }.getType();
        Gson gson = new Gson();
        SharedPreferences data = new SecurePreferences(mcontext);
        SharedPreferences.Editor editor = data.edit();
        String jsonTripList = data.getString("savedTrips", null);
        if (jsonTripList == null || jsonTripList.equals("[]")) {
            savedTrips = new ArrayList<>();
            savedTrips.add(tripDetail);
        } else {
            savedTrips = (List<TripDetail>) gson.fromJson(jsonTripList.toString(), type);
            Boolean isFound = false;
            for (TripDetail tmpTripDetail : savedTrips) {
                if (tmpTripDetail.getId() == tripDetail.getId()) {
                    savedTrips.remove(tmpTripDetail);
                    savedTrips.add(tmpTripDetail);
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                savedTrips.add(tripDetail);
            }
        }
        String json = gson.toJson(savedTrips);
        editor.putString("savedTrips", json);
        editor.commit();
    }

    private void getTripFromLocal() {
//        List<TripDetail> savedTrips;
//        Type type = new TypeToken<List<Trip>>() {
//        }.getType();
//        Gson gson = new Gson();
//        SharedPreferences data = new SecurePreferences(mcontext);
//        String json = data.getString("savedTrips", "empty");
//        if (!json.equals("empty")) {
//            savedTrips = (List<TripDetail>) gson.fromJson(json.toString(), type);
//            for (TripDetail tmpTripDetail : savedTrips) {
//                if (tmpTripDetail.getId() == tripDetail.getId()) {
//                    tripDetail = tmpTripDetail;
//                    break;
//                }
//            }
//        } else {
//            View view = getWindow().getDecorView().findViewById(android.R.id.content);
//            Snackbar.make(view, getString(R.string.mytrips_error), Snackbar.LENGTH_LONG)
//                    .setAction(getString(R.string.snackbar_ok), new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                        }
//                    }).show();
//        }
    }

    public void requestTripItinerary() {
        startAnim();
        String token = "Bearer ";
        token += Token.getToken(mcontext);
        Call<TripDetail> call = apiService.listTripByUser(token, tripid);
        call.enqueue(new Callback<TripDetail>() {
            @Override
            public void onResponse(Call<TripDetail> call, Response<TripDetail> response) {
                if (response.body() != null) {
                    try {
                        tripDetail = response.body();
                        afterGetData();
                    } catch (Exception e) {
                        View view = getWindow().getDecorView().findViewById(android.R.id.content);
                        Snackbar.make(view, getString(R.string.mytrips_error), Snackbar.LENGTH_LONG)
                                .setAction(getString(R.string.snackbar_ok), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                    }
                                }).show();
                    }
                } else {
                    View view = getWindow().getDecorView().findViewById(android.R.id.content);
                    Snackbar.make(view, getString(R.string.mytrips_error), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.snackbar_ok), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            }).show();
                }
            }

            @Override
            public void onFailure(Call<TripDetail> call, Throwable t) {
                View view = getWindow().getDecorView().findViewById(android.R.id.content);
                Snackbar.make(view, getString(R.string.mytrips_error), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.snackbar_ok), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        }).show();

            }
        });
        stopAnim();
    }

    private void afterGetData() {
        List<TripItinerary> itineraryList = tripDetail.getItinerary();
        for (int i = 0; i < itineraryList.size(); i++) {
            TripDay tripday = new TripDay();
            tripday.setId(itineraryList.get(i).getId());
            tripday.setName("Day" + (i + 1));
            tripday.setDesc(DateTimeHelper.castDateToLocaleFull(itineraryList.get(i).getVisit_date()));
            tripdays.add(tripday);
        }
        adapter.notifyDataSetChanged();
        String addOn = isSaved ? " (" + getString(R.string.mytrips_offline) + ")" : "";
        ab.setTitle(tripDetail.getTitle() + addOn);
        tv_tripdate.setText(DateTimeHelper.castDateToLocale(tripDetail.getVisit_date()) + " - " + DateTimeHelper.castDateToLocale(DateTimeHelper.endDate(tripDetail.getVisit_date(), tripDetail.getVisit_length())));
        int dayLeft = DateTimeHelper.daysLeft(tripDetail.getVisit_date());
        if (dayLeft == 0) {
            tv_tripdaysleftMessage.setText(getString(R.string.mytrips_detail_daysleft_after));
        } else {
            tv_tripdaysleftMessage.setText(getString(R.string.mytrips_detail_daysleft_before));
        }
        tv_tripdestination.setText(tripDetail.getCity().getName() + ", " + tripDetail.getCity().getCountry());
        tv_tripdaysleft.setText(String.valueOf(dayLeft));
        Picasso.with(mcontext)
                .load(tripDetail.getCity().getPhoto())
                .placeholder(R.drawable.image_null_tran)
                .into(image);
        cv_trip.setVisibility(View.VISIBLE);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void startAnim() {
        avi.show();
    }

    public void stopAnim() {
        avi.hide();
    }
}
