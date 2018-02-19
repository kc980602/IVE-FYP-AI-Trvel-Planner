package com.triple.triple.Presenter.Mytrips;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.triple.triple.Adapter.TripAdapter;
import com.triple.triple.Adapter.TripItineraryAdapter;
import com.triple.triple.Helper.ItemTouchHelperCallback;
import com.triple.triple.Helper.RecycleViewPaddingHelper;
import com.triple.triple.Model.TripItinerary;
import com.triple.triple.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 2018/1/22.
 */
public class ItineraryFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView rv_itinerary;
    private TripItineraryAdapter adapter;

    public ItineraryFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_itinerary, container, false);
        rv_itinerary = (RecyclerView) rootView.findViewById(R.id.rv_itinerary);
        initView();



//        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        return rootView;
    }


    private void initView() {
        adapter = new TripItineraryAdapter(getActivity(), initData());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rv_itinerary.setHasFixedSize(true);
        rv_itinerary.setLayoutManager(mLayoutManager);
        rv_itinerary.setItemAnimator(new DefaultItemAnimator());
        rv_itinerary.setAdapter(adapter);
        RecyclerView.ItemDecoration dividerItemDecoration = new RecycleViewPaddingHelper(30);
        rv_itinerary.addItemDecoration(dividerItemDecoration);
        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(adapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(rv_itinerary);
    }
    private List<TripItinerary> initData() {
        List<TripItinerary> itineraryList = new ArrayList<>();
        for (int i=0; i<=40; i++) {
            TripItinerary itinerary = new TripItinerary();
            itinerary.setName("New York .TempleTempleTempleTempleTempleTempleTempleTempleTempleTempleTempleTempleTempleTempleTempleTempleTempleTempleTempleTemple");
            itinerary.setDuration("3 hours");
            itinerary.setTags("#Landmark  #Foodie #60+");
            itineraryList.add(itinerary);
        }
        return itineraryList;
    }


}