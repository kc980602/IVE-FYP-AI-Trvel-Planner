package com.triple.triple.Presenter.Home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.triple.triple.Adapter.CityAdapter;
import com.triple.triple.Helper.SystemPropertyHelper;
import com.triple.triple.Model.City;
import com.triple.triple.R;
import com.triple.triple.Sync.GetSystemProperty;

import java.util.List;

/**
 * Created by Kevin on 2018/2/7.
 */

public class HomeFragment extends Fragment {

    private RecyclerView rv_cities;
    private CityAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rv_cities = (RecyclerView) view.findViewById(R.id.rv_cities);
        initView();
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


}
