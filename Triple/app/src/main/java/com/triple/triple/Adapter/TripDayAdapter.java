package com.triple.triple.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.triple.triple.Model.TripDay;
import com.triple.triple.Model.TripDetail;
import com.triple.triple.Presenter.Mytrips.ItineraryActivity;
import com.triple.triple.R;

import java.util.List;

/**
 * Created by Kevin on 2018/2/14.
 */
public class TripDayAdapter extends RecyclerView.Adapter<TripDayAdapter.TripDayViewHolder> {

    Context mcontext;
    List<TripDay> tripDays;
    TripDetail tripDetail;

    public TripDayAdapter(Context mcontext, List<TripDay> tripDays, TripDetail tripDetail) {
        this.mcontext = mcontext;
        this.tripDays = tripDays;
        this.tripDetail = tripDetail;
    }

    public class TripDayViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tripdayid;
        TextView tv_day;
        TextView tv_desc;

        public TripDayViewHolder(View itemView) {
            super(itemView);
            tv_tripdayid = (TextView) itemView.findViewById(R.id.tv_tripdayid);
            tv_day = (TextView) itemView.findViewById(R.id.tv_day);
            tv_desc = (TextView) itemView.findViewById(R.id.tv_desc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tripday = tv_day.getText().toString();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("tripday", tripday);
                    bundle.putSerializable("tripDetail", tripDetail);
                    Intent intent = new Intent(mcontext, ItineraryActivity.class);
                    intent.putExtras(bundle);
                    mcontext.startActivity(intent);
                }
            });
        }
    }


    @Override
    public TripDayViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycleviewitem_mytrips_detail_day, viewGroup, false);
        return new TripDayViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TripDayViewHolder holder, int position) {
        TripDay tripDay = tripDays.get(position);
        holder.tv_tripdayid.setText(String.valueOf(tripDay.getId()));
        holder.tv_day.setText(tripDay.getName());
        holder.tv_desc.setText(tripDay.getDesc());
    }

    @Override
    public int getItemCount() {
        return tripDays.size();
    }

}


