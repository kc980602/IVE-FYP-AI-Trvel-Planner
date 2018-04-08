package com.triple.triple.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itheima.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.triple.triple.Helper.BitmapTransform;
import com.triple.triple.Helper.Constant;
import com.triple.triple.Model.Attraction;
import com.triple.triple.Model.AttractionComment;
import com.triple.triple.Presenter.Attraction.AttractionDetailActivity;
import com.triple.triple.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HaYYY on 2018/4/8.
 */

public class AttractionCommentAdapter extends RecyclerView.Adapter<AttractionCommentAdapter.AttractionViewHolder> {

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

    public class AttractionViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_commentId, tv_comment_username, tv_comment_content;


        public AttractionViewHolder(View itemView) {
            super(itemView);
            tv_commentId = (TextView) itemView.findViewById(R.id.tv_commentId);
            tv_comment_username = (TextView) itemView.findViewById(R.id.tv_comment_username);
            tv_comment_content = (TextView) itemView.findViewById(R.id.tv_comment_content);
        }
    }


    @Override
    public AttractionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerviewitem_attraction_comments, viewGroup, false);
        return new AttractionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AttractionViewHolder holder, int position) {
        AttractionComment attractionComment = attractionComments.get(position);
        holder.tv_commentId.setText(attractionComment.getId());
        holder.tv_comment_username.setText(attractionComment.getUser().getUsername());
        holder.tv_comment_content.setText(attractionComment.getContent());
    }

    @Override
    public int getItemCount() {
        return attractionComments.size();
    }

}
