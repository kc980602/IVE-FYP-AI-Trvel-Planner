package com.triple.triple.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.triple.triple.Model.TripDay;
import com.triple.triple.R;

import java.util.List;

/**
 * Created by Kevin on 2018/2/14.
 */

public class TripDayAdapter extends RecyclerView.Adapter<TripDayAdapter.TripDayViewHolder> {

    private Activity activity;
    private List<TripDay> tripdays;

    public TripDayAdapter(Activity activity, List<TripDay> tripdays) {
        this.activity = activity;
        this.tripdays = tripdays;
    }

    public class TripDayViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_id;
        public TextView tv_day;
        public TextView tv_desc;

        public TripDayViewHolder(View itemView) {
            super(itemView);
            tv_id = (TextView) itemView.findViewById(R.id.tv_id);
            tv_day = (TextView) itemView.findViewById(R.id.tv_day);
            tv_desc = (TextView) itemView.findViewById(R.id.tv_desc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                }
            });
        }
    }

    @Override
    public TripDayViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listviewitem_mytrips_detail_day, viewGroup, false);
        return new TripDayViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TripDayViewHolder holder, int i) {
        TripDay tripday = tripdays.get(i);

        holder.tv_day.setText(tripday.getName());
        holder.tv_desc.setText(tripday.getDesc());
    }

    @Override
    public int getItemCount() {
        return tripdays.size();
    }
}
