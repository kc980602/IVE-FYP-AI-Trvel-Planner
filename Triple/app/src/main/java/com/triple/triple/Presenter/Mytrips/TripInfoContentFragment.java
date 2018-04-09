package com.triple.triple.Presenter.Mytrips;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.triple.triple.Adapter.TripArticleAdapter;
import com.triple.triple.Helper.UserDataHelper;
import com.triple.triple.Interface.ApiInterface;
import com.triple.triple.Model.Article;
import com.triple.triple.R;
import com.triple.triple.Sync.ApiClient;
import com.triple.triple.UILibrary.VerticalViewPager;
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
    private Context mcontext;

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
        token += UserDataHelper.getToken(mcontext);
        Call<List<Article>> call = apiService.getTripArticle(token, tripid);
        call.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {

                if (response.body() != null) {
                    try {
                        articles = response.body();
                        afterGetData();
                        ((TripInfoActivity) getActivity()).setUnlock();
                    } catch (Exception e) {
                        requestArticle();
                    }
                } else {
                    requestArticle();
                }
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                requestArticle();
            }
        });
    }

    private void afterGetData() {
        VerticalVPOnTouchListener verticalVPOnTouchListener = new VerticalVPOnTouchListener((VerticalViewPager) getArguments().getSerializable("viewpager"));
        recyclerView.setOnTouchListener(verticalVPOnTouchListener);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new TripArticleAdapter(mcontext, articles));
    }

    public String getTitle() {
        return getArguments().getString("title");
    }

    public int getPosition() {
        return getArguments().getInt("position");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mcontext = context;
    }
}
