package com.triple.triple.Presenter.Mytrips;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.triple.triple.Adapter.TripItineraryAdapter;
import com.triple.triple.Helper.DateTimeHelper;
import com.triple.triple.Helper.RecycleViewPaddingHelper;
import com.triple.triple.Model.TripDetail;
import com.triple.triple.Model.TripItinerary;
import com.triple.triple.R;

/**
 * Created by Kevin on 2018/1/22.
 */
public class ItineraryFragment extends Fragment {


    private RecyclerView rv_itinerary;
    private TripItineraryAdapter adapter;
    private TripItinerary itinerary;
    private TextView tv_daytag;

    public ItineraryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_itinerary, container, false);
        tv_daytag = (TextView) view.findViewById(R.id.tv_daytag);
        rv_itinerary = (RecyclerView) view.findViewById(R.id.rv_itinerary);
        itinerary = (TripItinerary) getArguments().getSerializable("itinerary");
        initView();
        return view;
    }


    private void initView() {
        tv_daytag.setText(DateTimeHelper.castDateToLocaleFull(itinerary.getVisit_date()));
        adapter = new TripItineraryAdapter(getActivity(), itinerary.getNodes());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rv_itinerary.setHasFixedSize(true);
        rv_itinerary.setLayoutManager(mLayoutManager);
        rv_itinerary.setItemAnimator(new DefaultItemAnimator());
        rv_itinerary.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        RecyclerView.ItemDecoration dividerItemDecoration = new RecycleViewPaddingHelper(90);
        rv_itinerary.addItemDecoration(dividerItemDecoration);

    }

}