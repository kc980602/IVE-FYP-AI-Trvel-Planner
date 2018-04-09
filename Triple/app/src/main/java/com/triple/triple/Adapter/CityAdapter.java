package com.triple.triple.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.triple.triple.Helper.BitmapTransform;
import com.triple.triple.Helper.Constant;
import com.triple.triple.Model.City;
import com.triple.triple.Presenter.Attraction.CityDetailActivity;
import com.triple.triple.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 2018/3/10.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private Activity activity;
    private List<City> cities;

    public CityAdapter(Activity activity, List<City> cities) {
        this.activity = activity;
        this.cities = cities;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerviewitem_city, parent, false);
        return new CityViewHolder(itemView);
    }

    public class CityViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_cityid;
        public TextView tv_city;
        public TextView tv_country;
        public ImageView image;

        public CityViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            tv_cityid = (TextView) itemView.findViewById(R.id.tv_cityid);
            tv_city = (TextView) itemView.findViewById(R.id.tv_city);
            tv_country = (TextView) itemView.findViewById(R.id.tv_country);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    int cityid = Integer.parseInt(tv_cityid.getText().toString());
                    Bundle bundle = new Bundle();
                    bundle.putInt("cityid", cityid);
                    Intent intent = new Intent(activity, CityDetailActivity.class);
                    intent.putExtras(bundle);
                    activity.startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        City city = cities.get(position);

        if (city.getPhoto() != null) {
            Picasso.with(activity)
                    .load(city.getPhoto())
                    .centerCrop().fit()
                    .transform(new BitmapTransform(Constant.IMAGE_S_WIDTH, Constant.IMAGE_S_HEIGHT))
                    .placeholder(R.drawable.ic_image_null_h)
                    .into(holder.image);
        }
        holder.tv_cityid.setText(String.valueOf(city.getId()));
        holder.tv_city.setText(city.getName());
        holder.tv_country.setText(city.getCountry());
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

}


