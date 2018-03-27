package com.triple.triple.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itheima.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.triple.triple.Model.Attraction;
import com.triple.triple.Presenter.Attraction.AttractionDetailActivity;
import com.triple.triple.R;

import java.util.List;

/**
 * Created by KC on 2/8/2018.
 */

public class AttractionAdapter extends RecyclerView.Adapter<AttractionAdapter.AttractionViewHolder> {

    private Context mcontext;
    private List<Attraction> attractions;
    private Boolean isSaved;

    public AttractionAdapter(Context mcontext, List<Attraction> attractions, Boolean isSaved) {
        this.mcontext = mcontext;
        this.attractions = attractions;
        this.isSaved = isSaved;
    }

    public class AttractionViewHolder extends RecyclerView.ViewHolder {
        public RoundedImageView image;
        public TextView tv_attId;
        public TextView tv_name;
        public TextView tv_rate_review;
        public RelativeLayout layout_bookmark;


        public AttractionViewHolder(View itemView) {
            super(itemView);
            image = (RoundedImageView) itemView.findViewById(R.id.image);
            tv_attId = (TextView) itemView.findViewById(R.id.tv_attId);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_rate_review = (TextView) itemView.findViewById(R.id.tv_rate_review);
            layout_bookmark = (RelativeLayout) itemView.findViewById(R.id.layout_bookmark);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int attractionId = Integer.parseInt(tv_attId.getText().toString());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("attractionId", attractionId);
                    Intent intent = new Intent(mcontext, AttractionDetailActivity.class);
                    intent.putExtras(bundle);
                    mcontext.startActivity(intent);
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
    public void onBindViewHolder(AttractionViewHolder holder, int position) {
        Attraction attraction = attractions.get(position);
        Picasso.with(mcontext)
                .load(attraction.getBestPhoto())
                .fit().centerCrop()
                .placeholder(R.drawable.image_null_tran)
                .into(holder.image);

        holder.tv_attId.setText(String.valueOf(attraction.getId()));
        holder.tv_name.setText(attraction.getName());
        holder.tv_rate_review.setText(String.format("%.1f/10 - %d Reviews", attraction.getRating(), attraction.getComment_count()));
        if (isSaved) {
            holder.layout_bookmark.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return attractions.size();
    }

}
