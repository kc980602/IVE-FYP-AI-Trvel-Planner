package com.triple.triple.Presenter.Attraction;

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
import com.triple.triple.Model.City;
import com.triple.triple.Presenter.Mytrips.TripInfoActivity;
import com.triple.triple.R;
import com.triple.triple.Sync.ApiClient;
import com.triple.triple.UILibrary.VerticalVPOnTouchListener;
import com.triple.triple.UILibrary.VerticalViewPager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityInfoContentFragment extends Fragment {
    private List<Article> articles;
    private LinearLayout layout_card;
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    private City city;
    private NestedScrollView layout_nsv;
    private RecyclerView recyclerView;
    private Context mcontext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        city = (City) getArguments().getSerializable("city");
        View view = inflater.inflate(R.layout.fragment_city_info_content, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.content_list);
        initView();
        return view;
    }

    private void initView() {

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
