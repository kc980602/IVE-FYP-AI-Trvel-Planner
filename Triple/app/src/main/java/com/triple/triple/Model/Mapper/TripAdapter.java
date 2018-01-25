package com.triple.triple.Model.Mapper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
 * Created by KC on 1/25/2018.
 */
public class TripAdapter extends BaseAdapter {

    Activity activity;
    List<Trip> trips;
    LayoutInflater inflater;

    public TripAdapter(Activity activity, List<Trip> trips) {
        this.activity = activity;
        this.trips = trips;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return trips.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.listviewitem_mytrips, parent, false);

            holder = new ViewHolder();

            holder.tv_tripid = (TextView) convertView.findViewById(R.id.tv_tripid);
            holder.tv_tripname = (TextView) convertView.findViewById(R.id.tv_tripname);
            holder.tv_owner = (TextView) convertView.findViewById(R.id.tv_owner);
            holder.tv_tripdate = (TextView) convertView.findViewById(R.id.tv_tripdate);
            holder.image1 = (ImageView) convertView.findViewById(R.id.image1);
            holder.image2 = (ImageView) convertView.findViewById(R.id.image2);

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        Trip trip = trips.get(position);

        holder.tv_tripid.setText(String.valueOf(trip.getId()));
        holder.tv_tripname.setText(trip.getName());
        holder.tv_owner.setText(trip.getOwner());
        String date = trip.getVisit_date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, trip.getVisit_length());
        date += " - " + sdf.format(c.getTime());
        holder.tv_tripdate.setText(date);
        Picasso.with(activity)
                .load(trip.getImages()[0].getImages())
                .into(holder.image1);
        Picasso.with(activity)
                .load(trip.getImages()[1].getImages())
                .into(holder.image2);

        return convertView;
    }

    public class ViewHolder {
        TextView tv_tripid, tv_tripname, tv_owner, tv_tripdate;
        ImageView image1, image2;
    }
}

