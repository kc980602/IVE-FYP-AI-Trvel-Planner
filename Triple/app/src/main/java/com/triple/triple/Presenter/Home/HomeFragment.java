package com.triple.triple.Presenter.Home;

import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.google.gson.Gson;
import com.nineoldandroids.view.ViewHelper;
import com.triple.triple.Adapter.CityCompactAdapter;
import com.triple.triple.Adapter.TripAdapter;
import com.triple.triple.Helper.Constant;
import com.triple.triple.Helper.SpacesItemDecoration;
import com.triple.triple.Helper.SystemPropertyHelper;
import com.triple.triple.Helper.UserDataHelper;
import com.triple.triple.Interface.ApiInterface;
import com.triple.triple.Model.City;
import com.triple.triple.Model.SystemProperty;
import com.triple.triple.Model.Trip;
import com.triple.triple.R;
import com.triple.triple.Sync.ApiClient;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kevin on 2018/2/7.
 */

public class HomeFragment extends Fragment implements
        ObservableScrollViewCallbacks,
        DiscreteScrollView.OnItemChangedListener,
        View.OnClickListener {
    private ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    private HomeFragment fragment = this;
    private ObservableScrollView layout_scroll;
    private ImageView image;
    private int mParallaxImageHeight;
    private Toolbar toolbar;
    private RecyclerView rv_all;

    private SharedPreferences sharedPreferences;
    private Context mcontext;
    private DiscreteScrollView dsv_trips;
    private List<Trip> trips = new ArrayList<>();
    private TripAdapter tripAdapter;
    private CityCompactAdapter cityCompactAdapter;
    private InfiniteScrollAdapter infiniteAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        sharedPreferences =  PreferenceManager.getDefaultSharedPreferences(getContext());
        layout_scroll = (ObservableScrollView) view.findViewById(R.id.layout_scroll);
        image = (ImageView) view.findViewById(R.id.image);
        rv_all = (RecyclerView) view.findViewById(R.id.rv_all);
        dsv_trips = (DiscreteScrollView) view.findViewById(R.id.dsv_trips);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.colorPrimary))));
        layout_scroll.setScrollViewCallbacks(this);
        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);
        requestSystemProperty();
        requestTrip();
        setHasOptionsMenu(true);
        return view;
    }

    private void initCity() {
        int numberOfColumns = 3;
        City city = new City();
        city.setId(-10);
        city.setPhoto("https://s3.amazonaws.com/spoonflower/public/design_thumbnails/0589/7283/stripesloopsbig_solidgrey_shop_preview.png");
        city.setName("MORE?");
        List<City> countries = SystemPropertyHelper.getSystemProperty(mcontext).getCity();
        countries.add(city);
        rv_all.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        cityCompactAdapter = new CityCompactAdapter(getContext(), countries);
        rv_all.setAdapter(cityCompactAdapter);
        rv_all.addItemDecoration(new SpacesItemDecoration(10));
    }

    private void initTrip() {
        tripAdapter = new TripAdapter((Fragment) fragment, trips, true, UserDataHelper.getUserInfo(mcontext).getId());
        dsv_trips.setOrientation(DSVOrientation.HORIZONTAL);
        dsv_trips.addOnItemChangedListener(this);
        infiniteAdapter = InfiniteScrollAdapter.wrap(tripAdapter);
        dsv_trips.setAdapter(infiniteAdapter);
        dsv_trips.setItemTransitionTimeMillis(150);
        dsv_trips.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());
    }

    public void requestSystemProperty() {
        Call<SystemProperty> call = apiService.getProperty();
        call.enqueue(new Callback<SystemProperty>() {
            @Override
            public void onResponse(Call<SystemProperty> call, Response<SystemProperty> response) {
                SystemProperty sp = response.body();
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constant.SP_SYSTEMPROPERTY, gson.toJson(sp));
                editor.apply();
                initCity();
            }

            @Override
            public void onFailure(Call<SystemProperty> call, Throwable t) {
                Log.e("getSystemProperty", t.getMessage());
            }
        });
    }

    public void requestTrip(){
        String token = "Bearer ";
        token += UserDataHelper.getToken(getContext());
        Call<List<Trip>> call = Constant.apiService.listTrip(token);
        call.enqueue(new Callback<List<Trip>>() {
            @Override
            public void onResponse(Call<List<Trip>> call, Response<List<Trip>> response) {
                if (response.body() != null) {
                    List<Trip> newTrips = response.body();
                    trips.clear();
                    trips.addAll(newTrips);
                    initTrip();
                } else {
                    requestFail();
                    Log.e("onResponse", "Null");
                }
            }
            @Override
            public void onFailure(Call<List<Trip>> call, Throwable t) {
                Log.e("onFailure", t.toString());
                requestFail();
            }
        });

    }


    private void requestFail() {
        View view = fragment.getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(view, getString(R.string.mytrips_error), Snackbar.LENGTH_LONG).setAction(getString(R.string.snackbar_refersh), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestTrip();
            }
        }).show();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        onScrollChanged(layout_scroll.getCurrentScrollY(), false, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
//                Intent intent = new Intent(getContext(), TripCreateActivity.class);
//                startActivity(intent);
                break;

        }
        return true;
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.primary);
        float alpha = Math.min(1, (float) scrollY / mParallaxImageHeight);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ScrollUtils.getColorWithAlpha(alpha, baseColor)));
        ViewHelper.setTranslationY(image, scrollY / 2);
    }


    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mcontext = context;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {
        int positionInDataSet = infiniteAdapter.getRealPosition(adapterPosition);
//        onItemChanged(data.get(positionInDataSet));
    }

    private void onItemChanged(ClipData.Item item) {
//        changeRateButtonState(item);
    }
}
