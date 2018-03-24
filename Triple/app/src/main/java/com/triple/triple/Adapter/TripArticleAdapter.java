package com.triple.triple.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.triple.triple.Model.Article;
import com.triple.triple.R;

import java.util.List;

/**
 * Created by Kevin on 2018/2/14.
 */

public class TripArticleAdapter extends RecyclerView.Adapter<TripArticleAdapter.TripArticleViewHolder> {

    Context context;
    List<Article> articles;

    public TripArticleAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public TripArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerviewitem_trip_article, parent, false);
        return new TripArticleViewHolder(itemView);
    }

    public class TripArticleViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView tv_attId;
        public TextView tv_name;
        public TextView tv_desc;

        public TripArticleViewHolder(View itemView) {
            super(itemView);
            image = (RoundedImageView) itemView.findViewById(R.id.image);
            tv_attId = itemView.findViewById(R.id.tv_attId);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_desc = itemView.findViewById(R.id.tv_desc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
//                    int tripid = Integer.valueOf(tv_tripid.getText().toString());
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("tripid", tripid);
//                    if (tv_saved.getVisibility() == View.VISIBLE) {
//                        bundle.putBoolean("isSaved", true);
//                    }
//                    Intent indent = new Intent(activity, TripDetailActivity.class);
//                    indent.putExtras(bundle);
//                    activity.startActivity(indent);
                }
            });
        }
    }


    @Override
    public void onBindViewHolder(TripArticleViewHolder holder, int position) {
        Article article = articles.get(position);
        if (article.getPhotos().get(0) != null) {
            Picasso.with(context)
                    .load(article.getPhotos().get(0))
                    .fit().centerCrop()
                    .placeholder(R.drawable.image_null_tran)
                    .into(holder.image);
        }
//        holder.tv_attId.setText(article.getId());
        holder.tv_name.setText(article.getName());
        holder.tv_desc.setText(article.getDescription());
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

}


