package com.triple.triple.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
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

import java.util.List;

/**
 * Created by Kevin on 2018/3/10.
 */

public class CityCompactAdapter extends RecyclerView.Adapter<CityCompactAdapter.CityCompactViewHolder> {

    private Context context;
    private List<City> cities;

    public CityCompactAdapter(Context context, List<City> cities) {
        this.context = context;
        this.cities = cities;
    }

    @Override
    public CityCompactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerviewitem_city_compact, parent, false);
        return new CityCompactViewHolder(itemView);
    }

    public class CityCompactViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_cityid;
        public TextView tv_city;
        public ImageView image;

        public CityCompactViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            tv_cityid = (TextView) itemView.findViewById(R.id.tv_cityid);
            tv_city = (TextView) itemView.findViewById(R.id.tv_city);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    int cityid = Integer.parseInt(tv_cityid.getText().toString());
                    if (cityid >= 0) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("cityid", cityid);
                        Intent intent = new Intent(context, CityDetailActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(CityCompactViewHolder holder, int position) {
        City city = cities.get(position);

        if (city.getPhoto() != null) {
            Picasso.with(context)
                    .load(city.getPhoto())
                    .fit().centerCrop()
                    .transform(new BitmapTransform(Constant.IMAGE_S_WIDTH, Constant.IMAGE_S_HEIGHT))
                    .placeholder(R.drawable.image_null_tran)
                    .into(holder.image);
        }
        holder.tv_cityid.setText(String.valueOf(city.getId()));
        holder.tv_city.setText(city.getName());
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

}


