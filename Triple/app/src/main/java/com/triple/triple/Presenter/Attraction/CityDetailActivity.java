package com.triple.triple.Presenter.Attraction;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.squareup.picasso.Picasso;
import com.triple.triple.Adapter.CityAttractionAdapter;
import com.triple.triple.Adapter.TripArticleAdapter;
import com.triple.triple.Helper.BitmapTransform;
import com.triple.triple.Helper.Constant;
import com.triple.triple.Helper.SystemPropertyHelper;
//import com.triple.triple.Interface.WeatherInterface;
import com.triple.triple.Helper.UserDataHelper;
import com.triple.triple.Interface.WeatherInterface;
import com.triple.triple.Model.Attraction;
import com.triple.triple.Model.City;
import com.triple.triple.Model.DataMeta;
import com.triple.triple.Presenter.Mytrips.TripCreateActivity;
import com.triple.triple.R;
//import com.triple.triple.Sync.ApiWeather;
import com.triple.triple.Sync.ApiWeather;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;
import org.json.JSONObject;


import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityDetailActivity extends AppCompatActivity {

    private Context mcontext = CityDetailActivity.this;
    WeatherInterface weatherApi = ApiWeather.getClient().create(WeatherInterface.class);
    private BottomNavigationViewEx nav_bar;
    private List<Attraction> attractions;
    private DataMeta dataMeta;
    private RecyclerView recyclerView;
    private int cityid;
    private City city;
    private CardView cv_city_info;
    private ImageButton img_arrow;
    private ImageView image;
    private TextView tv_city, tv_country, tv_time, tv_weather, tv_description;


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
        image = (ImageView) findViewById(R.id.image);
        tv_city = (TextView) findViewById(R.id.tv_city);
        tv_country = (TextView) findViewById(R.id.tv_country);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_weather = (TextView) findViewById(R.id.tv_weather);
        tv_description = (TextView) findViewById(R.id.tv_description);
        img_arrow = (ImageButton) findViewById(R.id.img_arrow);
        cv_city_info = (CardView) findViewById(R.id.cv_city_info);
        recyclerView = (RecyclerView) findViewById(R.id.content_list);
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
                .transform(new BitmapTransform(Constant.IMAGE_M_WIDTH, Constant.IMAGE_M_HEIGHT))
                .placeholder(R.drawable.ic_image_null_h)
                .into(image);

        if (city.getName().length() > 6) {
            tv_city.setTextAppearance(this, R.style.TextAppearance_AppCompat_Display1);
            tv_city.setTextColor(Color.WHITE);
            tv_city.setTypeface(null, Typeface.BOLD);
        }

        tv_city.setText(city.getName());
        tv_country.setText(city.getCountry());
        tv_description.setText(city.getDescription());

        nav_bar.enableAnimation(false);
        nav_bar.enableItemShiftingMode(false);
        nav_bar.enableShiftingMode(false);
        nav_bar.setOnNavigationItemSelectedListener(nav_barListener);
        img_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(tv_description.getLayoutParams().height == LinearLayout.LayoutParams.WRAP_CONTENT) {
                    tv_description.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200, 0));
                    img_arrow.setImageResource(R.drawable.ic_arrow_down);
                } else {
                    tv_description.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0));
                    img_arrow.setImageResource(R.drawable.ic_arrow_up);
                }

            }
        });
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
                    intent.setClass(mcontext, AttractionListActivity.class);
                    intent.putExtras(bundle);
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
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CityAttractionAdapter(mcontext, dataMeta));
    }

    public void getCityDetail() {
        Call<DataMeta> call = null;
        if (!UserDataHelper.checkTokenExist(this)) {
            call = Constant.apiService.getCityAttractions(cityid,0,5);
        } else {
            String token = "Bearer ";
            token += UserDataHelper.getToken(this);
            call = Constant.apiService.getAttractionByPreference(token, cityid);
        }
        call.enqueue(new Callback<DataMeta>() {
            @Override
            public void onResponse(Call<DataMeta> call, Response<DataMeta> response) {
                if (response.body() != null) {
                    dataMeta = response.body();
                    attractions = response.body().getAttractions();
                    loadDataToView();
                } else {
                    Log.d("onResponse", "Null response");
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
