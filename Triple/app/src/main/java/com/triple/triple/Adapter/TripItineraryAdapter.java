package com.triple.triple.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Kevin on 2018/2/18.
 */

public class TripItineraryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_DEFAULT = 0;
    private static final int TYPE_ACCOMMODATION = 1;
    private static final int TYPE_MEAL = 2;
    DecimalFormat format = new DecimalFormat("##.0");

    private Activity activity;
    private List<TripItineraryNode> itineraryNodes;
    private String isSaved;

    public TripItineraryAdapter(Activity activity, List<TripItineraryNode> itineraryNodes) {
        this.activity = activity;
        this.itineraryNodes = itineraryNodes;
//        this.isSaved = isSaved;
    }

    @Override
    public int getItemViewType(int position) {
        TripItineraryNode tripItineraryNode = (TripItineraryNode) itineraryNodes.get(position);

        if (tripItineraryNode.getType() == null) {
            return TYPE_DEFAULT;
        } else {
            switch (tripItineraryNode.getType()) {
                case "LODGING":
                    return TYPE_ACCOMMODATION;
                case "MEAL":
                    return TYPE_MEAL;
                default:
                    break;
            }
        }
        return TYPE_DEFAULT;
    }

    public class TripItineraryViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_attId;
        private TextView tv_name;
        private TextView tv_time;
        private TextView tv_duration;
        private TextView tv_address;
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
            tv_address = (TextView) itemView.findViewById(R.id.tv_address);
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

    public class TripItineraryViewHolderAccommodation extends RecyclerView.ViewHolder {

        private TextView tv_attId;
        private TextView tv_name;
        private TextView tv_time;
        private RelativeLayout layout_direction;
        private TextView tv_distance;
        private TextView tv_traveltime;

        public TripItineraryViewHolderAccommodation(View itemView) {
            super(itemView);
            tv_attId = (TextView) itemView.findViewById(R.id.tv_attId);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
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

    public class TripItineraryViewHolderMeal extends RecyclerView.ViewHolder {

        private TextView tv_attId;
        private TextView tv_name;
        private TextView tv_time;
        private TextView tv_duration;
        private TextView tv_address;
        private RelativeLayout layout_direction;
        private TextView tv_distance;
        private TextView tv_traveltime;
        private RoundedImageView image1;

        public TripItineraryViewHolderMeal(View itemView) {
            super(itemView);
            tv_attId = (TextView) itemView.findViewById(R.id.tv_attId);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_duration = (TextView) itemView.findViewById(R.id.tv_duration);
            tv_address = (TextView) itemView.findViewById(R.id.tv_address);
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

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = null;
        switch (i) {
            case TYPE_ACCOMMODATION:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerviewitem_mytrips_itinerary_accommodation, viewGroup, false);
                return new TripItineraryViewHolderAccommodation(itemView);
            case TYPE_MEAL:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerviewitem_mytrips_itinerary_meal, viewGroup, false);
                return new TripItineraryViewHolderMeal(itemView);
            default:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerviewitem_mytrips_itinerary, viewGroup, false);
                return new TripItineraryViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_ACCOMMODATION:
                initLayoutAccommodation((TripItineraryViewHolderAccommodation) holder, position);
                break;
            case TYPE_MEAL:
                initLayoutMeal((TripItineraryViewHolderMeal) holder, position);
                break;
            default:
                initLayoutDefault((TripItineraryViewHolder) holder, position);
                break;
        }
    }

    private void initLayoutDefault(TripItineraryViewHolder holder, int pos) {
        TripItineraryNode tripItineraryNode = itineraryNodes.get(pos);

        holder.tv_attId.setText(String.valueOf(tripItineraryNode.getAttraction().getId()));
        holder.tv_name.setText(tripItineraryNode.getAttraction().getName());
        holder.tv_name.setSelected(true);
        holder.tv_time.setText(DateTimeHelper.removeSec(tripItineraryNode.getVisit_time()) + "-" + DateTimeHelper.endTime(tripItineraryNode.getVisit_time(), tripItineraryNode.getDuration()));
        holder.tv_duration.setText(DateTimeHelper.secondToHourMinutes(tripItineraryNode.getDuration(), activity.getString(R.string.mytrips_detail_itinerary_hour), activity.getString(R.string.mytrips_detail_itinerary_minutes)));
        holder.tv_address.setText(tripItineraryNode.getAttraction().getAddress());

        if ((pos + 1) != getItemCount()) {
            TripItineraryNode tripItineraryNodeNext = itineraryNodes.get(pos + 1);
            if (tripItineraryNodeNext.getDistance() >= 1000) {
                holder.tv_distance.setText(String.format("%.1f", tripItineraryNodeNext.getDistance() / 1000.0) + activity.getString(R.string.mytrips_detail_itinerary_kilometers));
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

        List<String> photos = tripItineraryNode.getAttraction().getPhotos();
        Picasso.with(activity)
                .load(!photos.isEmpty() ? photos.get(0) : null)
                .placeholder(R.drawable.ic_image_null_square)
                .into(holder.image1);
    }

    private void initLayoutAccommodation(TripItineraryViewHolderAccommodation holder, int pos) {
        TripItineraryNode tripItineraryNode = itineraryNodes.get(pos);
        holder.tv_attId.setText(String.valueOf(tripItineraryNode.getAttraction().getId()));
        holder.tv_name.setText(tripItineraryNode.getAttraction().getName());
        holder.tv_name.setSelected(true);
        holder.tv_time.setText(activity.getString(R.string.mytrips_detail_itinerary_departure) + DateTimeHelper.removeSec(tripItineraryNode.getVisit_time()));

        if ((pos + 1) != getItemCount()) {
            TripItineraryNode tripItineraryNodeNext = itineraryNodes.get(pos + 1);
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
            holder.tv_time.setText(activity.getString(R.string.mytrips_detail_itinerary_return) + DateTimeHelper.removeSec(tripItineraryNode.getVisit_time()));
            holder.layout_direction.setVisibility(View.INVISIBLE);
        }
    }

    private void initLayoutMeal(TripItineraryViewHolderMeal holder, int pos) {
        TripItineraryNode tripItineraryNode = itineraryNodes.get(pos);

        holder.tv_attId.setText(String.valueOf(tripItineraryNode.getAttraction().getId()));
        holder.tv_name.setText(tripItineraryNode.getAttraction().getName());
        holder.tv_name.setSelected(true);
        holder.tv_time.setText(DateTimeHelper.removeSec(tripItineraryNode.getVisit_time()) + "-" + DateTimeHelper.endTime(tripItineraryNode.getVisit_time(), tripItineraryNode.getDuration()));
        holder.tv_duration.setText(DateTimeHelper.secondToHourMinutes(tripItineraryNode.getDuration(), activity.getString(R.string.mytrips_detail_itinerary_hour), activity.getString(R.string.mytrips_detail_itinerary_minutes)));
        holder.tv_address.setText(tripItineraryNode.getAttraction().getAddress());


        if ((pos + 1) != getItemCount()) {
            TripItineraryNode tripItineraryNodeNext = itineraryNodes.get(pos + 1);
            if (tripItineraryNodeNext.getDistance() >= 1000) {
                holder.tv_distance.setText(String.format("%.1f", tripItineraryNodeNext.getDistance() / 1000.0) + activity.getString(R.string.mytrips_detail_itinerary_kilometers));
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

    }

    public int getItemCount() {
        return itineraryNodes.size();
    }

}


