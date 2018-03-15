package com.triple.triple.Presenter.Attraction;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.squareup.picasso.Picasso;
import com.triple.triple.Helper.SystemPropertyHelper;
import com.triple.triple.Interface.ApiInterface;
import com.triple.triple.Model.Attraction;
import com.triple.triple.Model.City;
import com.triple.triple.Model.DataMeta;
import com.triple.triple.Presenter.Mytrips.TripCreateActivity;
import com.triple.triple.R;
import com.triple.triple.Sync.ApiClient;
import com.triple.triple.Sync.CreateTrip;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityDetailActivity extends AppCompatActivity {

    private Context mcontext = CityDetailActivity.this;
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    private Toolbar toolbar;
    private LinearLayout layout_cityname;
    private BottomNavigationViewEx nav_bar;
    private LinearLayout layout_attraction;
    private List<Attraction> attractions;
    private int cityid;
    private City city;
    private ImageView image;
    private TextView tv_city, tv_country;

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
    }

    private void findViews() {
        nav_bar = (BottomNavigationViewEx) findViewById(R.id.nav_bar);
        layout_attraction = (LinearLayout) findViewById(R.id.layout_attraction);
        image = (ImageView) findViewById(R.id.image);
        tv_city = (TextView) findViewById(R.id.tv_city);
        tv_country = (TextView) findViewById(R.id.tv_country);
    }

    private void initView() {
        city = SystemPropertyHelper.getSystemPropertyByCityId(mcontext, cityid);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle(city.getName());
        ab.setElevation(0);

        Picasso.with(mcontext)
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
        Menu menu = nav_bar.getMenu();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationViewEx.OnNavigationItemSelectedListener nav_barListener = new BottomNavigationViewEx.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent = new Intent();
            switch (item.getItemId()) {
                case R.id.action_discover:

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

                    break;
            }

            return false;
        }
    };

    private void loadDataToView() {

        for (int i = 0; i < attractions.size(); i++) {
            Attraction attraction = attractions.get(i);
            Log.e("loadDataToView", attraction.toString());
            Log.e("loadDataToView", attraction.getPhotos().size() + "is");
            LayoutInflater mInflater = LayoutInflater.from(this);
            View view = mInflater.inflate(R.layout.listitem_city_attraction, layout_attraction, false);
            ImageView image = (ImageView) view.findViewById(R.id.image);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_rate_review = (TextView) view.findViewById(R.id.tv_rate_review);

//            if (attraction.getPhotos()!=null) {
//                Picasso.with(mcontext)
//                        .load(attraction.getPhotos().get(1))
//                        .fit().centerCrop()
//                        .placeholder(R.drawable.image_null_tran)
//                        .into(image);
//            }


            tv_name.setText(attraction.getName());
            tv_rate_review.setText(String.format("%.1f", attraction.getRating()) + "/10 - " + attraction.getComment_count() + " Reviews");
            layout_attraction.addView(view);
            if (i == 9) {
                break;
            }
        }
    }

    public void getCityDetail() {

        Call<DataMeta> call = apiService.getAttractions(cityid);
        call.enqueue(new Callback<DataMeta>() {
            @Override
            public void onResponse(Call<DataMeta> call, Response<DataMeta> response) {
                if (response.body() != null) {
                    attractions = response.body().getAttractions();
                    Log.e("onResponse", response.body().getAttractions().toString());
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

}
