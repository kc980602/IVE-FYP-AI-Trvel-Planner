package com.triple.triple.Presenter.Attraction;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.triple.triple.Adapter.AttractionAdapter;
import com.triple.triple.Helper.Constant;
import com.triple.triple.Helper.SystemPropertyHelper;
import com.triple.triple.Helper.UserDataHelper;
import com.triple.triple.Model.Attraction;
import com.triple.triple.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityBookmarksActivity extends AppCompatActivity {

    private Context mcontext = CityBookmarksActivity.this;
    private AVLoadingIndicatorView avi;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rv_attractions;
    private int cityid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_bookmarks);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        cityid = bundle.getInt("cityid");
        findView();
        initView();
    }

    private void findView() {
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        rv_attractions = (RecyclerView) findViewById(R.id.rv_attractions);
    }

    private void initView() {
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(getString(R.string.city_detail_favorites));
            ab.setElevation(0);
        }


        String indicator = getIntent().getStringExtra("indicator");
        avi.setIndicator(indicator);

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestBookmarks();
            }
        });

        if (UserDataHelper.checkTokenExist(mcontext)) {
            requestBookmarks();
        } else {
            View view = getWindow().getDecorView().findViewById(android.R.id.content);
            Snackbar.make(view, "Login or Register First", Snackbar.LENGTH_LONG).setAction(getString(R.string.snackbar_refersh), new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            }).show();
        }

    }

    public void requestBookmarks() {
        startAnim();
        String token = "Bearer ";
        token += UserDataHelper.getToken(mcontext);
        Call<List<Attraction>> call = Constant.apiService.getBookmark(token, cityid);
        call.enqueue(new Callback<List<Attraction>>() {
            @Override
            public void onResponse(Call<List<Attraction>> call, Response<List<Attraction>> response) {                if (response.body() != null) {
                    List<Attraction> attractions = response.body();
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mcontext.getApplicationContext());
                    rv_attractions.setHasFixedSize(true);
                    rv_attractions.setLayoutManager(mLayoutManager);
                    rv_attractions.setItemAnimator(new DefaultItemAnimator());
                    AttractionAdapter adapter = new AttractionAdapter(mcontext, attractions, true);
                    rv_attractions.setAdapter(adapter);
                } else {
                    requestFail();
                    Log.e("onResponse", "Null");
                }
            }

            @Override
            public void onFailure(Call<List<Attraction>> call, Throwable t) {
                Log.e("onFailure", t.toString());
                requestFail();
            }
        });
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        stopAnim();
    }

    private void requestFail() {
        View view = getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(view, getString(R.string.mytrips_error), Snackbar.LENGTH_LONG).setAction(getString(R.string.snackbar_refersh), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestBookmarks();
            }
        }).show();
    }

    public void startAnim() {
        avi.show();
    }

    public void stopAnim() {
        avi.hide();
    }
}
