package com.triple.triple.Adapter;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.triple.triple.Model.TripDay;
import com.triple.triple.Model.TripDetail;
import com.triple.triple.Presenter.Attraction.AttractionDetailActivity;
import com.triple.triple.Presenter.Mytrips.ItineraryActivity;
import com.triple.triple.R;

import java.util.List;

/**
 * Created by Kevin on 2018/2/14.
 */

public class TripDayAdapter extends BaseAdapter {

    Context context;
    List<TripDay> tripDays;
    TripDetail tripDetail;

    public TripDayAdapter(Context context, List<TripDay> tripDays) {
        this.context = context;
        this.tripDays = tripDays;
    }

    public void setTripDetail (TripDetail tripDetail){
        this.tripDetail = tripDetail;
    }

    @Override
    public int getCount() {
        return tripDays.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return  tripDays.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listviewitem_mytrips_detail_day, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final TripDay tripDay = tripDays.get(position);
        holder.tv_tripdayid.setText(String.valueOf(tripDay.getId()));
        holder.tv_day.setText(tripDay.getName());
        holder.tv_desc.setText(tripDay.getDesc());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tripday = holder.tv_day.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putSerializable("tripday", tripday);
                bundle.putSerializable("tripDetail", tripDetail);
                Intent intent = new Intent(context, ItineraryActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        ImageView image1;
        TextView tv_tripdayid;
        TextView tv_day;
        TextView tv_desc;

        public ViewHolder(View view) {
            image1 = (ImageView)  view.findViewById(R.id.image1);
            tv_tripdayid = (TextView) view.findViewById(R.id.tv_tripdayid);
            tv_day = (TextView) view.findViewById(R.id.tv_day);
            tv_desc = (TextView) view.findViewById(R.id.tv_desc);
        }
    }

}


