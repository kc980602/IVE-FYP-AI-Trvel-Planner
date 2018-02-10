package com.triple.triple.Adapter;

/**
 * Created by Shade on 5/9/2016.
 */

import android.app.Activity;
import android.support.design.widget.Snackbar;
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



public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private String[] titles = {"Chapter One",
            "Chapter Two",
            "Chapter Three",
            "Chapter Four",
            "Chapter Five",
            "Chapter Six",
            "Chapter Seven",
            "Chapter Eight"};

    private String[] details = {"Item one details",
            "Item two details", "Item three details",
            "Item four details", "Item file details",
            "Item six details", "Item seven details",
            "Item eight details"};

    private int[] images = { R.drawable.a1,
            R.drawable.a2,
            R.drawable.a3,
            R.drawable.a4,
            R.drawable.a5,
            R.drawable.a6,
            R.drawable.a1,
            R.drawable.a2 };

    private Activity activity;
    private List<Trip> trips;

    public RecyclerAdapter(Activity activity, List<Trip> trips) {
        this.activity = activity;
        this.trips = trips;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_tripid;
        public TextView tv_tripname;
        public TextView tv_owner;
        public TextView tv_tripdate;
        public ImageView image1;

        public ViewHolder(View itemView) {
            super(itemView);
            image1 = (ImageView)itemView.findViewById(R.id.image1);
            tv_tripname = (TextView)itemView.findViewById(R.id.tv_tripname);
            tv_tripdate =
                    (TextView)itemView.findViewById(R.id.tv_tripdate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();

                    Snackbar.make(v, "Click detected on item " + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerviewitem_mytrips, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.tv_tripname.setText(titles[i]);
        viewHolder.tv_tripdate.setText(details[i]);
        viewHolder.image1.setImageResource(images[i]);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}