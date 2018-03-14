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
import com.triple.triple.Interface.ApiInterface;
import com.triple.triple.Model.Attraction;
import com.triple.triple.R;
import com.triple.triple.Sync.ApiClient;
import com.triple.triple.Sync.ApiClientTestingDemo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityDetailActivity extends AppCompatActivity {

    private Context mcontext = CityDetailActivity.this;
    ApiInterface apiService = ApiClientTestingDemo.getClient().create(ApiInterface.class);
    private Toolbar toolbar;
    private LinearLayout layout_cityname;
    private BottomNavigationViewEx nav_bar;
    private LinearLayout layout_attraction;
    private LayoutInflater mInflater;
    private List<Attraction> attractions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);
        findViews();
        initView();
        getUserDetails();
    }

    private void findViews() {
        mInflater = LayoutInflater.from(this);
        nav_bar = (BottomNavigationViewEx) findViewById(R.id.nav_bar);
        layout_attraction = (LinearLayout) findViewById(R.id.layout_attraction);
    }

    private void initView() {
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle("Tokyo");
        ab.setElevation(0);

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
                    break;
                case R.id.action_favorities:

                    break;
            }

            return false;
        }
    };

    private void loadDataToView() {
        int[] data = new int[]{R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a6, R.drawable.a7};
        for (int i = 0; i < attractions.size(); i++) {
            Attraction attraction = attractions.get(i);
            View view = mInflater.inflate(R.layout.listitem_city_attraction, layout_attraction, false);
            ImageView image = (ImageView) view.findViewById(R.id.image);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_rate_review = (TextView) findViewById(R.id.tv_rate_review);

            Picasso.with(mcontext)
                    .load(data[i])
                    .placeholder(R.drawable.image_null_square)
                    .into(image);
            tv_name.setText(attraction.getName());
            tv_rate_review.setText(String.format("%.1f", attraction.getRating()) + "/10 " + attraction.getComment_count() + "Reviews");
            layout_attraction.addView(view);
            if (i == 9) {
                break;
            }
        }
    }

    public void getUserDetails() {

        Call<List<Attraction>> call = apiService.getAttractions();
        call.enqueue(new Callback<List<Attraction>>() {
            @Override
            public void onResponse(Call<List<Attraction>> call, Response<List<Attraction>> response) {
                if (response.body() != null) {
                    attractions = response.body();
                    loadDataToView();
                } else {
                    Log.d("onResponse", "Null respone");
                }
            }

            @Override
            public void onFailure(Call<List<Attraction>> call, Throwable t) {
                Log.e("onFailure", t.toString());
            }

        });

    }

}
