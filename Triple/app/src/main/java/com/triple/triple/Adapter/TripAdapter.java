package com.triple.triple.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.triple.triple.Helper.CalendarHelper;
import com.triple.triple.Model.Trip;
import com.triple.triple.Model.TripDay;
import com.triple.triple.Presenter.Mytrips.TripDetailActivity;
import com.triple.triple.R;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by KC on 2/8/2018.
 */

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    private Activity activity;
    private List<Trip> trips;

    public TripAdapter(Activity activity, List<Trip> trips) {
        this.activity = activity;
        this.trips = trips;
    }

    public class TripViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_tripid;
        public TextView tv_tripname;
        public TextView tv_owner;
        public TextView tv_tripdestination;
        public TextView tv_tripdate;
        public RoundedImageView image1;

        public TripViewHolder(View itemView) {
            super(itemView);
            image1 = (RoundedImageView) itemView.findViewById(R.id.image1);
            tv_tripid = (TextView) itemView.findViewById(R.id.tv_tripid);
            tv_tripname = (TextView) itemView.findViewById(R.id.tv_tripname);
            tv_owner = (TextView) itemView.findViewById(R.id.tv_owner);
            tv_tripdestination = (TextView) itemView.findViewById(R.id.tv_tripdestination);
            tv_tripdate = (TextView) itemView.findViewById(R.id.tv_tripdate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    int tripId = Integer.parseInt(tv_tripid.getText().toString());
                    for(Trip trip: trips){
                        if(trip.getId() == tripId){
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("trip", trip);
                            Intent indent = new Intent(activity, TripDetailActivity.class);
                            indent.putExtras(bundle);
                            activity.startActivity(indent);
                            break;
                        }
                    }
                }
            });
        }
    }


    @Override
    public TripViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerviewitem_mytrips, viewGroup, false);
        return new TripViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TripViewHolder holder, int i) {
        Trip trip = trips.get(i);
        String date = CalendarHelper.castDateToLocale(trip.getVisit_date()) + " - " + CalendarHelper.castDateToLocale(CalendarHelper.endDate(trip.getVisit_date(), trip.getVisit_length()));

        if (trip.getImage() == null) {
            holder.image1.setImageResource(R.drawable.image_null);
        } else {
            Picasso.with(activity)
                    .load(trip.getImage())
                    .into(holder.image1);
        }
        holder.tv_tripid.setText(String.valueOf(trip.getId()));
        holder.tv_tripname.setText(trip.getName());
        holder.tv_owner.setText(trip.getOwner());
        holder.tv_tripdate.setText(date);
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

}
