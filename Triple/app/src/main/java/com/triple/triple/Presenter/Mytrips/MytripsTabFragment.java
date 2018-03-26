//package com.triple.triple.Presenter.Mytrips;
//
//
//import android.os.Bundle;
//import android.support.design.widget.Snackbar;
//import android.support.v4.app.Fragment;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.triple.triple.Adapter.TripAdapter;
//import com.triple.triple.Helper.UserDataHelper;
//import com.triple.triple.Interface.ApiInterface;
//import com.triple.triple.Model.Trip;
//import com.triple.triple.R;
//import com.triple.triple.Sync.ApiClient;
//import com.wang.avi.AVLoadingIndicatorView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class MytripsTabFragment extends Fragment {
//
//    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//    private AVLoadingIndicatorView avi;
//    private SwipeRefreshLayout swipeRefreshLayout;
//    private TripAdapter adapter;
//
//    private List<Trip> trips = new ArrayList<>();
//    private RecyclerView rv_trips;
//
//    public MytripsTabFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_mytrips_tab, container, false);
//        avi = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
//        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
//        rv_trips = (RecyclerView) view.findViewById(R.id.rv_trips);
//        initView();
//        requestTrip();
//        return view;
//    }
//
//    private void initView() {
//
//        String indicator = getActivity().getIntent().getStringExtra("indicator");
//        avi.setIndicator(indicator);
//
//        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                requestTrip();
//            }
//        });
//
//        adapter = new TripAdapter(getActivity(), trips, "false", UserDataHelper.getUserInfo(getContext()).getId());
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
//        rv_trips.setHasFixedSize(true);
//        rv_trips.setLayoutManager(mLayoutManager);
//        rv_trips.setItemAnimator(new DefaultItemAnimator());
//        rv_trips.setAdapter(adapter);
//    }
//
//    public void requestTrip(){
//        startAnim();
//        String token = "Bearer ";
//        token += UserDataHelper.getToken(getContext());
//        Call<List<Trip>> call = apiService.listTrip(token);
//        call.enqueue(new Callback<List<Trip>>() {
//            @Override
//            public void onResponse(Call<List<Trip>> call, Response<List<Trip>> response) {
//                if (response.body() != null) {
//                    List<Trip> newTrips = response.body();
//                } else {
//                    Log.e("onResponse", "Null");
//                }
//            }
//            @Override
//            public void onFailure(Call<List<Trip>> call, Throwable t) {
//                Log.e("onFailure", t.toString());
//                View view = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
//                Snackbar.make(view, getString(R.string.mytrips_error), Snackbar.LENGTH_LONG).setAction(getString(R.string.snackbar_ok), new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {}}).show();
//            }
//        });
//        if (swipeRefreshLayout.isRefreshing()) {
//            swipeRefreshLayout.setRefreshing(false);
//        }
//        stopAnim();
//    }
//
//    public void startAnim() {
//        avi.show();
//    }
//
//    public void stopAnim() {
//        avi.hide();
//    }
//}
