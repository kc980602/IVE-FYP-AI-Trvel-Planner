package com.triple.triple.Presenter.Attraction;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.triple.triple.Model.City;
import com.triple.triple.R;

/**
 * Created by Kevin on 2018/3/13.
 */

public class CityInfoOverviewFragment extends Fragment{

    private TextView tv_city, tv_country, tv_description;
    private ImageView image_main;
    private City city;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_info_overview, container, false);
        city = (City) getArguments().getSerializable("city");
        tv_city = (TextView) view.findViewById(R.id.tv_city);
        tv_country = (TextView) view.findViewById(R.id.tv_country);
        tv_description = (TextView) view.findViewById(R.id.tv_description);
        image_main = (ImageView) view.findViewById(R.id.image_main);
        initView();
        return view;
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
    }
}
