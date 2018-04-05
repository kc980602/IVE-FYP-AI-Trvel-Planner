package com.triple.triple.Presenter.Mytrips;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.triple.triple.Adapter.FragmentAdapter;
import com.triple.triple.Adapter.TripAdapter;
import com.triple.triple.Helper.Constant;
import com.triple.triple.Helper.UserDataHelper;
import com.triple.triple.Model.Trip;
import com.triple.triple.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MytripsFragment extends Fragment {
    private View view;
    private MytripsFragment fragment = this;
    private AVLoadingIndicatorView avi;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TripAdapter adapter;

    private List<Trip> trips = new ArrayList<>();
    private RecyclerView rv_trips;

    public MytripsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mytrips, container, false);
        avi = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        rv_trips = (RecyclerView) view.findViewById(R.id.rv_trips);
        initView();
        setHasOptionsMenu(true);
        return view;
    }

    private void initView() {
        String indicator = getActivity().getIntent().getStringExtra("indicator");
        avi.setIndicator(indicator);

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                trips.clear();
                requestTrip();
            }
        });

        if (UserDataHelper.checkTokenExist(getContext())) {
            requestTrip();
        } else {
            View view = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
            Snackbar.make(view, "Login or Register First", Snackbar.LENGTH_LONG).setAction(getString(R.string.snackbar_refersh), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            }).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Snackbar.make(view, "Trip removed", Snackbar.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(true);
                trips.clear();
                requestTrip();
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_mytrips, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(getContext(), TripCreateActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    public void requestTrip(){
        startAnim();
        String token = "Bearer ";
        token += UserDataHelper.getToken(getContext());
        Call<List<Trip>> call = Constant.apiService.listTrip(token);
        call.enqueue(new Callback<List<Trip>>() {
            @Override
            public void onResponse(Call<List<Trip>> call, Response<List<Trip>> response) {
                if (response.body() != null) {
                    List<Trip> newTrips = response.body();
                    trips.addAll(newTrips);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    rv_trips.setHasFixedSize(true);
                    rv_trips.setLayoutManager(mLayoutManager);
                    rv_trips.setItemAnimator(new DefaultItemAnimator());
                    adapter = new TripAdapter(fragment, trips, "false", UserDataHelper.getUserInfo(getContext()).getId());
                    rv_trips.setAdapter(adapter);
                } else {
                    requestFail();
                    Log.e("onResponse", "Null");
                }
            }
            @Override
            public void onFailure(Call<List<Trip>> call, Throwable t) {
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
        View view = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(view, getString(R.string.mytrips_error), Snackbar.LENGTH_LONG).setAction(getString(R.string.snackbar_refersh), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestTrip();
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
