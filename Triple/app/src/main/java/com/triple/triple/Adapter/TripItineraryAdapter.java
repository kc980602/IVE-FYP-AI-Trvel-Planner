package com.triple.triple.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itheima.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.triple.triple.Helper.DateTimeHelper;
import com.triple.triple.Model.TripItineraryNode;
import com.triple.triple.Presenter.Attraction.AttractionDetailActivity;
import com.triple.triple.R;

import java.util.List;

/**
 * Created by Kevin on 2018/2/18.
 */

public class TripItineraryAdapter extends RecyclerView.Adapter<TripItineraryAdapter.TripItineraryViewHolder> {

    private static final int TYPE_DEFAULT = 0;
    private static final int TYPE_ACCOMMODATION = 1;
    private static final int TYPE_MEAL = 2;

    private Activity activity;
    private List<TripItineraryNode> itineraryNodes;
    private String isSaved;

    public TripItineraryAdapter(Activity activity, List<TripItineraryNode> itineraryNodes) {
        this.activity = activity;
        this.itineraryNodes = itineraryNodes;
//        this.isSaved = isSaved;
    }

//    @Override
//    public boolean onItemMove(int fromPosition, int toPosition) {
//        Collections.swap(itinerary.getNodes(), fromPosition, toPosition);
//        notifyItemMoved(fromPosition, toPosition);
//        return true;
//    }

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

        private TextView tv_attId;
        private TextView tv_name;
        private TextView tv_time;
        private TextView tv_duration;
        private TextView tv_tags;
        private RelativeLayout layout_direction;
        private TextView tv_distance;
        private TextView tv_traveltime;
        private RoundedImageView image1;

        public TripItineraryViewHolder(View itemView) {
            super(itemView);
            tv_attId = (TextView) itemView.findViewById(R.id.tv_attId);
            image1 = (RoundedImageView) itemView.findViewById(R.id.image1);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_duration = (TextView) itemView.findViewById(R.id.tv_duration);
            tv_tags = (TextView) itemView.findViewById(R.id.tv_tags);
            layout_direction = (RelativeLayout) itemView.findViewById(R.id.layout_direction);
            tv_distance = (TextView) itemView.findViewById(R.id.tv_distance);
            tv_traveltime = (TextView) itemView.findViewById(R.id.tv_traveltime);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    int attractionId = Integer.parseInt(tv_attId.getText().toString());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("attractionId", attractionId);
                    Intent intent = new Intent(activity, AttractionDetailActivity.class);
                    intent.putExtras(bundle);
                    activity.startActivity(intent);
                }
            });
        }
    }

    public TripItineraryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 1) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerviewitem_mytrips_itinerary_accommodation, viewGroup, false);
            return new TripItineraryViewHolder(itemView);
        } else if (i == 2) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerviewitem_mytrips_itinerary_accommodation, viewGroup, false);
            return new TripItineraryViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerviewitem_mytrips_itinerary, viewGroup, false);
            return new TripItineraryViewHolder(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        TripItineraryNode tripItineraryNode = itineraryNodes.get(position);

        switch (tripItineraryNode.getType()) {
            case "LODGING":
                return 1;
            case "MEAL":
                return 2;
            default:
                return 0;
        }
    }

    @Override
    public void onBindViewHolder(TripItineraryViewHolder holder, int i) {
        TripItineraryNode tripItineraryNode = itineraryNodes.get(i);

        holder.tv_attId.setText(String.valueOf(tripItineraryNode.getAttraction().getId()));
        holder.tv_name.setText(tripItineraryNode.getAttraction().getName());
        holder.tv_name.setSelected(true);
        holder.tv_time.setText(DateTimeHelper.removeSec(tripItineraryNode.getVisit_time()) + "-" + DateTimeHelper.endTime(tripItineraryNode.getVisit_time(), tripItineraryNode.getDuration()));
        holder.tv_duration.setText(DateTimeHelper.secondToHourMinutes(tripItineraryNode.getDuration(), activity.getString(R.string.mytrips_detail_itinerary_hour), activity.getString(R.string.mytrips_detail_itinerary_minutes)));
        holder.tv_tags.setText(tripItineraryNode.getType());


        if ((i + 1) != getItemCount()) {
            TripItineraryNode tripItineraryNodeNext = itineraryNodes.get(i + 1);
            if (tripItineraryNodeNext.getDistance() >= 1000) {
                holder.tv_distance.setText(String.valueOf(tripItineraryNodeNext.getDistance() / 1000.0) + activity.getString(R.string.mytrips_detail_itinerary_kilometers));
            } else {
                holder.tv_distance.setText(String.valueOf(tripItineraryNodeNext.getDistance()) + activity.getString(R.string.mytrips_detail_itinerary_meters));
            }

            if (tripItineraryNodeNext.getMode() != null) {
                switch (tripItineraryNodeNext.getMode()) {
                    case "walking":
                        holder.tv_traveltime.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_directions_walk, 0, 0, 0);
                        break;
                }
            }
            holder.tv_traveltime.setText(DateTimeHelper.secondToHourMinutes(tripItineraryNodeNext.getTravel_duration(), activity.getString(R.string.mytrips_detail_itinerary_hour), activity.getString(R.string.mytrips_detail_itinerary_minutes)));
        } else {
            holder.layout_direction.setVisibility(View.INVISIBLE);
        }


//        if (!tripItineraryNode.getImage().isEmpty() && tripItineraryNode.getImage() != null) {
//            Picasso.with(activity)
//                    .load(tripItineraryNode.getImage())
//                    .into(holder.image1);
//        }

    }

    public int getItemCount() {
        return itineraryNodes.size();
    }

}


