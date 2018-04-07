package com.triple.triple.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.triple.triple.Model.Attraction;
import com.triple.triple.Model.DataMeta;
import com.triple.triple.Presenter.Attraction.AttractionDetailActivity;
import com.triple.triple.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class AttractionListAdapter extends RecyclerView.Adapter<AttractionListAdapter.AttractionViewHolder>{

    private Activity activity;
    private List<Attraction> attractions;

    public AttractionListAdapter(Activity activity, DataMeta dataMeta) {
        this.activity = activity;
        if(dataMeta == null){
            this.attractions = new ArrayList<>();
        } else {
            this.attractions = dataMeta.getAttractions();
        }
    }


    public class AttractionViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_attId, tv_attName, tv_attAddress, tv_attType, tv_attReview, tv_rating;
        public ImageView image1;

        public AttractionViewHolder(View itemView) {
            super(itemView);
            image1 = (ImageView) itemView.findViewById(R.id.image1);
            tv_attId = (TextView) itemView.findViewById(R.id.tv_attId);
            tv_attName = (TextView) itemView.findViewById(R.id.tv_attName);
            tv_attType = (TextView) itemView.findViewById(R.id.tv_attType);
            tv_rating = (TextView) itemView.findViewById(R.id.tv_attRating);
            tv_attAddress = (TextView) itemView.findViewById(R.id.tv_attAddress);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    int attId = Integer.valueOf(tv_attId.getText().toString());
                    Bundle bundle = new Bundle();
                    bundle.putInt("attractionId", attId);
                    Intent indent = new Intent(activity, AttractionDetailActivity.class);
                    indent.putExtras(bundle);
                    activity.startActivity(indent);
                }
            });
            tv_attReview = (TextView) itemView.findViewById(R.id.tv_attReview);
        }
    }


    @Override
    public AttractionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listviewitem_attraction, viewGroup, false);
        return new AttractionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AttractionViewHolder holder, int i) {
        Attraction attraction = attractions.get(i);
        Picasso.with(activity)
                .load(attraction.getBestPhoto())
                .fit().centerCrop()
                .into(holder.image1);

        holder.tv_attId.setText(String.valueOf(attraction.getId()));
        holder.tv_attName.setText(attraction.getName());
        holder.tv_attReview.setText(attraction.getComment_count() + " Reviews");
        holder.tv_rating.setText(String.valueOf(attraction.getRating()) + " / 10.0");
        holder.tv_attAddress.setText(attraction.getAddress());
        String[] category_id =  activity.getResources().getStringArray(R.array.category_id);
        String[] category_name =  activity.getResources().getStringArray(R.array.category_name);
        for(int n = 0; n < category_id.length;n++){
            if(((List<String>)attraction.getTags()).get(0).equals(category_id[n])){
                holder.tv_attType.setText(category_name[n]);
            }
        }
    }

    @Override
    public int getItemCount() {
        return attractions.size();
    }


    public void setFilter(List<Attraction> attractionList) {
        if(attractionList == null){
            notifyDataSetChanged();
        } else {
            attractions = new ArrayList<>();
            attractions.addAll(attractionList);
            notifyDataSetChanged();
        }
    }

}
