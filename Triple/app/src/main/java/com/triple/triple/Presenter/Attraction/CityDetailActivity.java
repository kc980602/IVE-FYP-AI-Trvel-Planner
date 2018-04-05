package com.triple.triple.Presenter.Attraction;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.squareup.picasso.Picasso;
import com.triple.triple.Helper.Constant;
import com.triple.triple.Helper.SystemPropertyHelper;
import com.triple.triple.Interface.ApiInterface;
//import com.triple.triple.Interface.WeatherInterface;
import com.triple.triple.Interface.WeatherInterface;
import com.triple.triple.Model.Attraction;
import com.triple.triple.Model.City;
import com.triple.triple.Model.DataMeta;
import com.triple.triple.Presenter.Mytrips.TripCreateActivity;
import com.triple.triple.R;
import com.triple.triple.Sync.ApiClient;
//import com.triple.triple.Sync.ApiWeather;
import com.triple.triple.Sync.ApiWeather;
import com.triple.triple.Sync.CreateTrip;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityDetailActivity extends AppCompatActivity {

    private Context mcontext = CityDetailActivity.this;
    WeatherInterface weatherApi = ApiWeather.getClient().create(WeatherInterface.class);
    private Toolbar toolbar;
    private LinearLayout layout_cityname;
    private BottomNavigationViewEx nav_bar;
    private LinearLayout layout_attraction;
    private List<Attraction> attractions;
    private DataMeta dataMeta, attraction, hotel, restaurant;
    private int cityid;
    private City city;
    private ImageView image;
    private TextView tv_city, tv_country, tv_time, tv_weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        cityid = bundle.getInt("cityid");
        findViews();
        initView();
        getCityDetail();
        getTime();
        getWeather();
    }

    private void findViews() {
        nav_bar = (BottomNavigationViewEx) findViewById(R.id.nav_bar);
        layout_attraction = (LinearLayout) findViewById(R.id.layout_attraction);
        image = (ImageView) findViewById(R.id.image);
        tv_city = (TextView) findViewById(R.id.tv_city);
        tv_country = (TextView) findViewById(R.id.tv_country);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_weather = (TextView) findViewById(R.id.tv_weather);
    }

    private void initView() {
        city = SystemPropertyHelper.getSystemPropertyByCityId(this, cityid);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(city.getName());
            ab.setElevation(0);
        }

        Picasso.with(this)
                .load(city.getPhoto())
                .fit().centerCrop()
                .placeholder(R.drawable.image_null)
                .into(image);
        tv_city.setText(city.getName());
        tv_country.setText(city.getCountry());

        nav_bar.enableAnimation(false);
        nav_bar.enableItemShiftingMode(false);
        nav_bar.enableShiftingMode(false);
        nav_bar.setOnNavigationItemSelectedListener(nav_barListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return item.getItemId() == R.id.action_search || super.onOptionsItemSelected(item);
    }

    private BottomNavigationViewEx.OnNavigationItemSelectedListener nav_barListener = new BottomNavigationViewEx.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            switch (item.getItemId()) {
                case R.id.action_discover:
                    bundle.putSerializable("city", city);
                    bundle.putSerializable("dataMeta", dataMeta);
                    bundle.putSerializable("attraction", attraction);
                    bundle.putSerializable("hotel", hotel);
                    bundle.putSerializable("restaurants", restaurant);
                    intent.setClass(mcontext, AttractionListActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case R.id.action_info:
                    intent.setClass(mcontext, CityInfoActivity.class);
                    startActivity(intent);
                    break;
                case R.id.action_plan:
                    intent.setClass(mcontext, TripCreateActivity.class);
                    startActivity(intent);
                    break;
                case R.id.action_favorities:
                    bundle.putInt("cityid", cityid);
                    intent.setClass(mcontext, CityBookmarksActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
            }

            return false;
        }
    };

    private void loadDataToView() {
        LayoutInflater mInflater = LayoutInflater.from(this);
        for (final Attraction attraction : attractions) {
            View view = mInflater.inflate(R.layout.listitem_city_attraction, layout_attraction, false);
            CardView cardView = view.findViewById(R.id.cv_trip);
            ImageView image = view.findViewById(R.id.image);
            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_rate_review = view.findViewById(R.id.tv_rate_review);
            Picasso.with(mcontext)
                    .load(attraction.getBestPhoto())
                    .fit().centerCrop()
                    .placeholder(R.drawable.image_null_tran)
                    .error(R.drawable.image_null_tran)
                    .into(image);
            tv_name.setText(attraction.getName());
            tv_rate_review.setText(String.format(Locale.ENGLISH, "%.1f/10 - %d Reviews", attraction.getRating(), attraction.getComment_count()));
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("attractionId", attraction.getId());
                    Intent intent = new Intent(context, AttractionDetailActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
            layout_attraction.addView(view);
        }
    }

    public void getCityDetail() {
        Call<DataMeta> call = Constant.apiService.getAttractions(cityid,10);
        call.enqueue(new Callback<DataMeta>() {
            @Override
            public void onResponse(Call<DataMeta> call, Response<DataMeta> response) {
                if (response.body() != null) {
                    dataMeta = response.body();
                    attractions = response.body().getAttractions();
                    loadDataToView();
                } else {
                    Log.d("onResponse", "Null respone");
                }
            }

            @Override
            public void onFailure(Call<DataMeta> call, Throwable t) {
                Log.e("onFailure", t.toString());
            }
        });
    }

    public void getAttractions() {
        Call<DataMeta> call = Constant.apiService.getCityAttractions(cityid);
        call.enqueue(new Callback<DataMeta>() {
            @Override
            public void onResponse(Call<DataMeta> call, Response<DataMeta> response) {
                if (response.body() != null) {
                    attraction = response.body();
                } else {
                    Log.d("onResponse", "Null respone");
                }
            }

            @Override
            public void onFailure(Call<DataMeta> call, Throwable t) {
                Log.e("onFailure", t.toString());
            }
        });

        call = Constant.apiService.getCityHotels(cityid);
        call.enqueue(new Callback<DataMeta>() {
            @Override
            public void onResponse(Call<DataMeta> call, Response<DataMeta> response) {
                if (response.body() != null) {
                    hotel = response.body();
                } else {
                    Log.d("onResponse", "Null respone");
                }
            }

            @Override
            public void onFailure(Call<DataMeta> call, Throwable t) {
                Log.e("onFailure", t.toString());
            }
        });

        call = Constant.apiService.getCityRestaurants(cityid);
        call.enqueue(new Callback<DataMeta>() {
            @Override
            public void onResponse(Call<DataMeta> call, Response<DataMeta> response) {
                if (response.body() != null) {
                    restaurant = response.body();
                } else {
                    Log.d("onResponse", "Null respone");
                }
            }

            @Override
            public void onFailure(Call<DataMeta> call, Throwable t) {
                Log.e("onFailure", t.toString());
            }
        });
    }

    public void getTime() {
        DateTime dateTime = new DateTime(DateTimeZone.forID(city.getTimezone()));
        LocalTime localTime = dateTime.toLocalTime();
        String hour = localTime.getHourOfDay() < 10 ? "0" + String.valueOf(localTime.getHourOfDay()): String.valueOf(localTime.getHourOfDay());
        String min = localTime.getMinuteOfHour() < 10 ? "0" + String.valueOf(localTime.getMinuteOfHour()) : String.valueOf(localTime.getMinuteOfHour());
        tv_time.setText(String.format(Locale.ENGLISH, "%s:%s", hour, min));
    }

    public void getWeather() {
        Call<ResponseBody> call = weatherApi.getWeather(city.getLatitude(), city.getLongitude(), "51595da2afec13ba782f96a781ac158a");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    String result = "";
                    String output, icon = "weather";
                    try {
                        result = response.body().string();
                        JSONObject jsonObject = new JSONObject(result);
                        output = String.format("%.1f", (Double.parseDouble(jsonObject.getJSONObject("main").getString("temp")) - 273.15)) + "°C";
                        icon += jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon");
                        tv_weather.setText(output);
                        int imgId = getResources().getIdentifier(icon, "drawable", getPackageName());
                        final int finalImgId = imgId;
                        tv_weather.getViewTreeObserver()
                                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                    @Override
                                    public void onGlobalLayout() {
                                        Drawable img = getResources().getDrawable(finalImgId);
                                        img.setBounds(0, 0, img.getIntrinsicWidth() * tv_weather.getMeasuredHeight() / img.getIntrinsicHeight(), tv_weather.getMeasuredHeight());
                                        tv_weather.setCompoundDrawables(img, null, null, null);
                                        tv_weather.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                    }
                                });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d("onResponse", "Null response");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure", t.toString());
            }

        });
    }

}
