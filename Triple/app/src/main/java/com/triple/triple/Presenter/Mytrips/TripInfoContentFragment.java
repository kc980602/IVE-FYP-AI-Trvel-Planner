package com.triple.triple.Presenter.Mytrips;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.triple.triple.Helper.DateTimeHelper;
import com.triple.triple.Helper.Token;
import com.triple.triple.Interface.ApiInterface;
import com.triple.triple.Model.Article;
import com.triple.triple.Model.Attraction;
import com.triple.triple.Model.Trip;
import com.triple.triple.Model.TripDetail;
import com.triple.triple.R;
import com.triple.triple.Sync.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripInfoContentFragment extends Fragment {
    private List<Article> articles;
    private NestedScrollView layout_nsv;
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    private int tripid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tripid = (int) getArguments().getInt("tripid");
        View view = inflater.inflate(R.layout.fragment_trip_info_content, container, false);
        layout_nsv = (NestedScrollView) view.findViewById(R.id.layout_nsv);
        initView();
        return view;
    }

    private void initView() {
        requestTripItinerary();

    }

    public void requestTripItinerary() {
        String token = "Bearer ";
        token += Token.getToken(getContext());
        Call<List<Article>> call = apiService.getTripArticle(token, 371);
        call.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                if (response.body() != null) {
                    try {
                        articles = response.body();
                        afterGetData();
                    } catch (Exception e) {
                        Log.e("loadDataToView", "catch");
                    }
                } else {
                    Log.e("loadDataToView", "else");
                }
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Log.e("loadDataToView", "onFailure");
            }
        });
    }

    private void afterGetData() {
        for (int i = 0; i < articles.size(); i++) {
            Article article = articles.get(i);
            Log.e("loadDataToView", article.toString());
            Log.e("loadDataToView", article.getPhotos().size() + "is");
            LayoutInflater mInflater = LayoutInflater.from(getContext());
            View view = mInflater.inflate(R.layout.listitem_trip_info, layout_nsv, false);
            ImageView image = (ImageView) view.findViewById(R.id.image);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_desc = (TextView) view.findViewById(R.id.tv_desc);

            if (article.getPhotos().get(0) != null) {
                Picasso.with(getContext())
                        .load(article.getPhotos().get(0))
                        .fit().centerCrop()
                        .placeholder(R.drawable.image_null_tran)
                        .into(image);
            }

            tv_name.setText(article.getName());
            tv_desc.setText(article.getDescription());
            layout_nsv.addView(view);
        }
    }

}
