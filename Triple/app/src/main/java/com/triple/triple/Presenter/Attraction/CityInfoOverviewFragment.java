package com.triple.triple.Presenter.Attraction;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.triple.triple.Adapter.AttractionListAdapter;
import com.triple.triple.Adapter.CityAttractionAdapter;
import com.triple.triple.Adapter.TripArticleAdapter;
import com.triple.triple.Helper.Constant;
import com.triple.triple.Helper.RecycleViewPaddingHelper;
import com.triple.triple.Model.Article;
import com.triple.triple.Model.City;
import com.triple.triple.Model.DataMeta;
import com.triple.triple.Presenter.Mytrips.TripInfoActivity;
import com.triple.triple.R;
import com.triple.triple.UILibrary.VerticalVPOnTouchListener;
import com.triple.triple.UILibrary.VerticalViewPager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kevin on 2018/3/13.
 */

public class CityInfoOverviewFragment extends Fragment{

    private TextView tv_city, tv_country, tv_description, tv_lon, tv_lat;
    private ImageView image_main;
    private City city;
    private RecyclerView recyclerView;
    private DataMeta dataMeta;
    private Context mcontext;
    private CityAttractionAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_info_overview, container, false);
        city = (City) getArguments().getSerializable("city");
        tv_city = (TextView) view.findViewById(R.id.tv_city);
        tv_country = (TextView) view.findViewById(R.id.tv_country);
        tv_description = (TextView) view.findViewById(R.id.tv_description);
        tv_lon = (TextView) view.findViewById(R.id.tv_lon);
        tv_lat = (TextView) view.findViewById(R.id.tv_lat);
        image_main = (ImageView) view.findViewById(R.id.image_main);
        recyclerView = (RecyclerView) view.findViewById(R.id.content_list);
        initView();
        getAttractions();
        return view;
    }

    public void getAttractions(){
        Call<DataMeta> call = Constant.apiService.getCityAttractions(city.getId(),0,3);
        call.enqueue(new Callback<DataMeta>() {
            @Override
            public void onResponse(Call<DataMeta> call, Response<DataMeta> response) {

                if (response.body() != null) {
                    try {
                        dataMeta = response.body();
                        afterGetData();
                    } catch (Exception e) {
                        getAttractions();
                    }
                } else {
                    getAttractions();
                }
            }

            @Override
            public void onFailure(Call<DataMeta> call, Throwable t) {
                getAttractions();
            }
        });
    }

    private void afterGetData() {
        adapter = new CityAttractionAdapter(getActivity(), dataMeta);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        RecyclerView.ItemDecoration dividerItemDecoration = new RecycleViewPaddingHelper(90);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initView() {
        Picasso.with(getActivity())
                .load(city.getPhoto())
                .resize(2000,3500).centerCrop()
                .into(image_main);
        tv_city.setText(city.getName());
        tv_country.setText(city.getCountry());
        tv_description.setText(city.getDescription());
        tv_lat.setText(String.valueOf(city.getLatitude()));
        tv_lon.setText(String.valueOf(city.getLongitude()));
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
    }
}
