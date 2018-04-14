package com.triple.triple.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.triple.triple.Helper.BitmapTransform;
import com.triple.triple.Helper.Constant;
import com.triple.triple.Model.Article;
import com.triple.triple.Model.Attraction;
import com.triple.triple.Model.DataMeta;
import com.triple.triple.Presenter.Attraction.AttractionDetailActivity;
import com.triple.triple.R;

import java.util.List;

/**
 * Created by Kevin on 2018/2/14.
 */

public class CityAttractionAdapter extends RecyclerView.Adapter<CityAttractionAdapter.CityAttractionViewHolder> {

    Context context;
    DataMeta dataMeta;
    List<Attraction> attractions;

    public CityAttractionAdapter(Context context, DataMeta dataMeta) {
        this.context = context;
        this.dataMeta = dataMeta;
        this.attractions = dataMeta.getAttractions();
    }

    @Override
    public CityAttractionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerviewitem_attraction, parent, false);
        return new CityAttractionViewHolder(itemView);
    }

    public class CityAttractionViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView tv_attId;
        public TextView tv_name;
        public TextView tv_desc;

        public CityAttractionViewHolder(View itemView) {
            super(itemView);
            image = (RoundedImageView) itemView.findViewById(R.id.image);
            tv_attId = itemView.findViewById(R.id.tv_attId);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_desc = itemView.findViewById(R.id.tv_desc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    int attId = Integer.valueOf(tv_attId.getText().toString());
                    Bundle bundle = new Bundle();
                    bundle.putInt("attractionId", attId);
                    Intent indent = new Intent(context, AttractionDetailActivity.class);
                    indent.putExtras(bundle);
                    context.startActivity(indent);
                }
            });

        }
    }


    @Override
    public void onBindViewHolder(CityAttractionViewHolder holder, int i) {
        Attraction attraction = attractions.get(i);
        if (attraction.getPhotos().size() != 0) {
            Picasso.with(context)
                    .load(attraction.getPhotos().get(0))
                    .fit().centerCrop()
                    .transform(new BitmapTransform(Constant.IMAGE_M_WIDTH, Constant.IMAGE_M_HEIGHT))
                    .placeholder(R.drawable.ic_image_null_h)
                    .into(holder.image);
        }
        holder.tv_name.setText(attraction.getName());
        holder.tv_desc.setText(attraction.getDescription());
        holder.tv_attId.setText(String.valueOf(attraction.getId()));
    }

    @Override
    public int getItemCount() {
        return attractions.size();
    }

}


