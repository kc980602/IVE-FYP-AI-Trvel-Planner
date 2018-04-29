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

import com.squareup.picasso.Picasso;
import com.triple.triple.Helper.BitmapTransform;
import com.triple.triple.Helper.Constant;
import com.triple.triple.Model.KeyValue;
import com.triple.triple.Model.TripDay;
import com.triple.triple.Model.TripDetail;
import com.triple.triple.Presenter.Mytrips.ItineraryActivity;
import com.triple.triple.R;

import java.util.List;

/**
 * Created by Kevin on 2018/2/14.
 */
public class PreferenceAdapter extends RecyclerView.Adapter<PreferenceAdapter.PreferenceViewHolder> {

    Context mcontext;
    List<KeyValue> keyValues;

    public PreferenceAdapter(Context mcontext, List<KeyValue> keyValues) {
        this.mcontext = mcontext;
        this.keyValues = keyValues;
    }

    public class PreferenceViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView tv_preference;

        public PreferenceViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            tv_preference = (TextView) itemView.findViewById(R.id.tv_preference);
        }
    }


    @Override
    public PreferenceViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycleviewitem_preference, viewGroup, false);
        return new PreferenceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder, int position) {
        KeyValue keyValue = keyValues.get(position);

        Picasso.with(mcontext)
                .load(mcontext.getResources().getIdentifier(keyValue.getValue(), "drawable", mcontext.getPackageName()))
                .fit().centerCrop()
                .transform(new BitmapTransform(Constant.IMAGE_M_WIDTH, Constant.IMAGE_M_HEIGHT))
                .placeholder(R.drawable.ic_image_null_h)
                .into(holder.image);

        holder.tv_preference.setText(keyValue.getKey());
    }

    @Override
    public int getItemCount() {
        return keyValues.size();
    }

}


