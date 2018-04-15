package com.triple.triple.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.itheima.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.triple.triple.Helper.BitmapTransform;
import com.triple.triple.Helper.Constant;
import com.triple.triple.Helper.DateTimeHelper;
import com.triple.triple.Model.Attraction;
import com.triple.triple.Model.AttractionComment;
import com.triple.triple.Presenter.Attraction.AttractionDetailActivity;
import com.triple.triple.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by HaYYY on 2018/4/8.
 */

public class AttractionCommentAdapter extends RecyclerView.Adapter<AttractionCommentAdapter.AttractionCommentViewHolder> {

    private Activity activity;
    private List<AttractionComment> attractionComments;

    public AttractionCommentAdapter(Activity activity, List<AttractionComment> attractionComments) {
        this.activity = activity;
        if (attractionComments == null) {
            this.attractionComments = new ArrayList<>();
        } else {
            this.attractionComments = attractionComments;
        }
    }

    public class AttractionCommentViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_commentId, tv_title, tv_name, tv_rate_time, tv_content;
        public ImageView image;

        public AttractionCommentViewHolder(View itemView) {
            super(itemView);
            tv_commentId = (TextView) itemView.findViewById(R.id.tv_commentId);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_rate_time = (TextView) itemView.findViewById(R.id.tv_rate_time);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }


    @Override
    public AttractionCommentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerviewitem_attraction_comments, viewGroup, false);
        return new AttractionCommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AttractionCommentViewHolder holder, int position) {
        AttractionComment ac = attractionComments.get(position);
        holder.tv_commentId.setText(String.valueOf(ac.getId()));
        holder.tv_title.setText(ac.getTitle());
        holder.tv_name.setText(ac.getUser().getFirst_name() + " " + ac.getUser().getLast_name());
        long now = System.currentTimeMillis();
        String date = String.valueOf(DateUtils.getRelativeTimeSpanString(ac.getCreated_at(), now, DateUtils.DAY_IN_MILLIS));
        TimeZone.setDefault(TimeZone.getTimeZone("HKT"));
        Date time = new java.util.Date((long)ac.getCreated_at()*1000);
        time.setHours(time.getHours()+8);
        SimpleDateFormat sdFormat = new SimpleDateFormat("MMM dd,yyyy");
        holder.tv_rate_time.setText(String.valueOf(ac.getRating()) + " / 10" + " • " + sdFormat.format(time));
        holder.tv_content.setText(ac.getContent());


        TextDrawable drawable = TextDrawable.builder()
                .buildRoundRect(String.valueOf(ac.getUser().getFirst_name().charAt(0)), activity.getResources().getColor(Constant.GETCOLOR()), 1000);
        holder.image.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return attractionComments.size();
    }

}
