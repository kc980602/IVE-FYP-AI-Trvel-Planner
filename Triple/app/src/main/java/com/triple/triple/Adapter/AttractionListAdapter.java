package com.triple.triple.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
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

import com.itheima.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.triple.triple.Helper.BitmapTransform;
import com.triple.triple.Helper.Constant;
import com.triple.triple.Model.Attraction;
import com.triple.triple.Model.DataMeta;
import com.triple.triple.Presenter.Attraction.AttractionDetailActivity;
import com.triple.triple.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
        public RoundedImageView image;
        public TextView tv_attId;
        public TextView tv_name;
        public TextView tv_rate_review;
        public TextView tv_tag;

        public AttractionViewHolder(View itemView) {
            super(itemView);
            image = (RoundedImageView) itemView.findViewById(R.id.image);
            tv_attId = (TextView) itemView.findViewById(R.id.tv_attId);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_rate_review = (TextView) itemView.findViewById(R.id.tv_rate_review);
            tv_tag = (TextView) itemView.findViewById(R.id.tv_tag);

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
        }
    }


    @Override
    public AttractionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerviewitem_attraction, viewGroup, false);
        return new AttractionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AttractionViewHolder holder, int i) {
        Attraction attraction = attractions.get(i);
        if (attraction.getPhotos().size() > 0) {
            holder.tv_tag.setTextAppearance(activity, R.style.TextAppearance_AppCompat_Small_Inverse);
            holder.tv_name.setTextAppearance(activity, R.style.TextAppearance_AppCompat_Large_Inverse);
            holder.tv_rate_review.setTextAppearance(activity, R.style.TextAppearance_AppCompat_Small_Inverse);
            Picasso.with(activity)
                    .load(attraction.getPhotos().get(0))
                    .fit().centerCrop()
                    .error(R.drawable.ic_image_null_uw)
                    .transform(new BitmapTransform(Constant.IMAGE_M_WIDTH, Constant.IMAGE_M_HEIGHT))
                    .placeholder(R.drawable.ic_image_null_uw)
                    .into(holder.image);
        } else {
            Picasso.with(activity)
                    .load(R.drawable.ic_image_null_uw)
                    .fit().centerCrop()
                    .error(R.drawable.ic_image_null_uw)
                    .transform(new BitmapTransform(Constant.IMAGE_M_WIDTH, Constant.IMAGE_M_HEIGHT))
                    .placeholder(R.drawable.ic_image_null_uw)
                    .into(holder.image);
            holder.tv_tag.setTextAppearance(activity, R.style.TextAppearance_AppCompat_Small);
            holder.tv_name.setTextAppearance(activity, R.style.TextAppearance_AppCompat_Large);
            holder.tv_rate_review.setTextAppearance(activity, R.style.TextAppearance_AppCompat_Small);
        }
        holder.tv_tag.setTypeface(null, Typeface.BOLD);
        holder.tv_name.setTypeface(null, Typeface.BOLD);
        holder.tv_rate_review.setTypeface(null, Typeface.BOLD);

        holder.tv_attId.setText(String.valueOf(attraction.getId()));
        holder.tv_name.setText(attraction.getName());
        holder.tv_rate_review.setText(String.format("%.1f/10 - %d Reviews", attraction.getRating(), attraction.getComment_count()));

        int resId = activity.getResources().getIdentifier("tag_"+((List<String>)attraction.getTags()).get(0), "string",  activity.getPackageName());
        if (resId!=0) {
            holder.tv_tag.setText(activity.getString(resId));

        }
    }

    @Override
    public int getItemCount() {
        return attractions.size();
    }


    public void setFilter(List<Attraction> attractionList) {
        if(attractionList != null){
            notifyDataSetChanged();
        } else {
            attractions = new ArrayList<>();
            attractions.addAll(attractionList);
            notifyDataSetChanged();
        }
    }

    public void addAttraction (List<Attraction> atts){
        int size = attractions.size();
        if(atts == null){
            notifyDataSetChanged();
        } else {
            for( int i = 0; i < atts.size() ; i++){
                attractions.add(atts.get(i));
                notifyDataSetChanged();
            }
        }
    }

}
