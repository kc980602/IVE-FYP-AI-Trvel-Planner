package com.triple.triple.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.triple.triple.Helper.CalendarHelper;
import com.triple.triple.Interface.onMoveAndSwipedListener;
import com.triple.triple.Model.Trip;
import com.triple.triple.Model.TripItinerary;
import com.triple.triple.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by Kevin on 2018/2/18.
 */

public class TripItineraryAdapter extends RecyclerView.Adapter<TripItineraryAdapter.TripItineraryViewHolder> implements onMoveAndSwipedListener {

    private Activity activity;
    private List<TripItinerary> tripItineraries;
    private String isSaved;

    public TripItineraryAdapter(Activity activity, List<TripItinerary> tripItineraries) {
        this.activity = activity;
        this.tripItineraries = tripItineraries;
//        this.isSaved = isSaved;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(tripItineraries, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

//    @Override
//    public void onItemDismiss(int position) {
////        tripItineraries.remove(position);
////        notifyItemRemoved(position);
////
////        Snackbar.make(parentView, context.getString(R.string.item_swipe_dismissed), Snackbar.LENGTH_SHORT)
////                .setAction(context.getString(R.string.item_swipe_undo), new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        addItem(position, mItems.get(position));
////                    }
////                }).show();
//    }

    public class TripItineraryViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name;
        public TextView tv_duration;
        public TextView tv_tags;
//        public RoundedImageView image1;

        public TripItineraryViewHolder(View itemView) {
            super(itemView);
//            image1 = (RoundedImageView) itemView.findViewById(R.id.image1);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_duration = (TextView) itemView.findViewById(R.id.tv_duration);
            tv_tags = (TextView) itemView.findViewById(R.id.tv_tags);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=25.033965,121.564472");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    activity.startActivity(mapIntent);
                }
            });
        }
    }

    public TripItineraryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerviewitem_mytrips_itinerary, viewGroup, false);
        return new TripItineraryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TripItineraryViewHolder holder, int i) {
        TripItinerary itinerary = tripItineraries.get(i);

//        holder.tv_name.setText(itinerary.getName());
//        holder.tv_name.setSelected(true);
//        holder.tv_duration.setText(itinerary.getDuration());
//        holder.tv_tags.setText(itinerary.getTags());
//        Picasso.with(activity)
//                .load("http://cdn-image.travelandleisure.com/sites/default/files/styles/1600x1000/public/1446143216/tokyo-header-dg1015.jpg?itok=nOef-qJm")
//                .into(holder.image1);
    }

    public int getItemCount() {
        return tripItineraries.size();
    }

}


