package com.triple.triple.Presenter.Home;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.google.gson.Gson;
import com.securepreferences.SecurePreferences;
import com.triple.triple.Adapter.CityAdapter;
import com.triple.triple.Helper.Constant;
import com.triple.triple.Helper.SystemPropertyHelper;
import com.triple.triple.Interface.ApiInterface;
import com.triple.triple.Model.City;
import com.triple.triple.Model.SystemProperty;
import com.triple.triple.R;
import com.triple.triple.Sync.ApiClient;
import com.triple.triple.Sync.GetSystemProperty;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kevin on 2018/2/7.
 */

public class HomeFragment extends Fragment {
    private ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    private RecyclerView rv_cities;
    private CityAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rv_cities = (RecyclerView) view.findViewById(R.id.rv_cities);
        requestData();
        setHasOptionsMenu(true);
        return view;
    }

    private void initView() {
        adapter = new CityAdapter(getActivity(), SystemPropertyHelper.getSystemProperty(getContext()).getCity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rv_cities.setHasFixedSize(true);
        rv_cities.setLayoutManager(mLayoutManager);
        rv_cities.setItemAnimator(new DefaultItemAnimator());
        rv_cities.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public void requestData(){
        Call<SystemProperty> call = apiService.getProperty();
        call.enqueue(new Callback<SystemProperty>() {
            @Override
            public void onResponse(Call<SystemProperty> call, Response<SystemProperty> response) {
                SystemProperty sp = response.body();
                Gson gson = new Gson();
                SharedPreferences data = getActivity().getSharedPreferences(Constant.SharedPreferences, 0);
                data.edit()
                        .putString("systemProperty", gson.toJson(sp))
                        .commit();
                initView();
            }

            @Override
            public void onFailure(Call<SystemProperty> call, Throwable t) {
                Log.e("getSystemProperty", t.getMessage());
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
//                Intent intent = new Intent(getContext(), TripCreateActivity.class);
//                startActivity(intent);
                break;

        }
        return true;
    }

}
