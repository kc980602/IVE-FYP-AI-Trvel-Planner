package com.triple.triple.Presenter.Mytrips;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.triple.triple.Adapter.TripItineraryAdapter;
import com.triple.triple.R;

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
//        adapter = new TripItineraryAdapter(getActivity(), initData());
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
//        rv_itinerary.setHasFixedSize(true);
//        rv_itinerary.setLayoutManager(mLayoutManager);
//        rv_itinerary.setItemAnimator(new DefaultItemAnimator());
//        rv_itinerary.setAdapter(adapter);
//        RecyclerView.ItemDecoration dividerItemDecoration = new RecycleViewPaddingHelper(30);
//        rv_itinerary.addItemDecoration(dividerItemDecoration);
//        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(adapter);
//        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
//        mItemTouchHelper.attachToRecyclerView(rv_itinerary);
    }

}