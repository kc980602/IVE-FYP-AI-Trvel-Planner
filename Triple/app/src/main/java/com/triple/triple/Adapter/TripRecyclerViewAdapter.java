package com.triple.triple.Adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.triple.triple.Model.Trip;
import com.triple.triple.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by KC on 2/8/2018.
 */

public class TripRecyclerViewAdapter extends RecyclerView.Adapter<TripRecyclerViewAdapter.TripViewHolder> {

    Activity activity;
    List<Trip> trips;

    @Override
    public TripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewitem_mytrips, parent, false);
        TripViewHolder tvh = new TripViewHolder(v);
        return tvh;
    }


    public TripRecyclerViewAdapter(Activity activity, List<Trip> trips) {
        this.activity = activity;
        this.trips = trips;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(TripViewHolder holder, int position) {
        holder.tv_tripid.setText(trips.get(position).getId());
        holder.tv_tripname.setText(trips.get(position).getName());
        holder.tv_owner.setText(trips.get(position).getOwner());

        String date = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(trips.get(position).getVisit_date()));
            date = sdf2.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, trips.get(position).getVisit_length());
        date += " - " + sdf2.format(c.getTime());

        holder.tv_tripdate.setText(date);

        Picasso.with(activity)
                .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTTbrmTGR3UlYoUKYqW6AMyb_9HSfqNxSgV9vWMER4T9w-v-xPx")
                .into(holder.image1);
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    public static class TripViewHolder extends RecyclerView.ViewHolder {

        CardView cv_trip;
        TextView tv_tripid, tv_tripname, tv_owner, tv_tripdate;
        ImageView image1;


        TripViewHolder(View itemView) {
            super(itemView);
            cv_trip = (CardView) itemView.findViewById(R.id.cv_trip);
            tv_tripid = (TextView) itemView.findViewById(R.id.tv_tripid);
            tv_tripname = (TextView) itemView.findViewById(R.id.tv_tripname);
            tv_owner = (TextView) itemView.findViewById(R.id.tv_owner);
            tv_tripdate = (TextView) itemView.findViewById(R.id.tv_tripdate);
            image1 = (ImageView) itemView.findViewById(R.id.image1);
        }
    }
}
