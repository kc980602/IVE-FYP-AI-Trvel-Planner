package com.triple.triple.Presenter.Mytrips;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.triple.triple.Adapter.TripArticleAdapter;
import com.triple.triple.Helper.DateTimeHelper;
import com.triple.triple.Helper.RecycleViewPaddingHelper;
import com.triple.triple.Helper.Token;
import com.triple.triple.Interface.ApiInterface;
import com.triple.triple.Model.Article;
import com.triple.triple.Model.Attraction;
import com.triple.triple.Model.Trip;
import com.triple.triple.Model.TripDetail;
import com.triple.triple.R;
import com.triple.triple.Sync.ApiClient;
import com.triple.triple.UILibrary.DummyViewPager;
import com.triple.triple.UILibrary.VerticalVPOnTouchListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripInfoContentFragment extends Fragment {
    private List<Article> articles;
    private LinearLayout layout_card;
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    private int tripid;
    private NestedScrollView layout_nsv;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tripid = (int) getArguments().getInt("tripid");
        View view = inflater.inflate(R.layout.fragment_trip_info_content, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.content_list);
        initView();
        return view;
    }

    private void initView() {
        requestArticle();

    }

    public void requestArticle() {
        String token = "Bearer ";
        token += Token.getToken(getContext());
        Call<List<Article>> call = apiService.getTripArticle(token, tripid);
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
                Log.e("loadDataToView", t.getMessage());
            }
        });
    }

    private void afterGetData() {

        recyclerView.setOnTouchListener(new VerticalVPOnTouchListener((DummyViewPager) getArguments().getSerializable("viewpager")));//set the vertical scroll controller
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new TripArticleAdapter(getContext(), articles));
        RecyclerView.ItemDecoration dividerItemDecoration = new RecycleViewPaddingHelper(150);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    public String getTitle() {
        return getArguments().getString("title");
    }

    public int getPosition() {
        return getArguments().getInt("position");
    }

}
