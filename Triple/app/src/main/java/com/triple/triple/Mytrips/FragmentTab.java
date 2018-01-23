package com.triple.triple.Mytrips;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.triple.triple.R;
import com.triple.triple.Model.TripPlanDetail;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kevin on 2018/1/22.
 */
public class FragmentTab extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mytrips_detail_tab, container, false);
        ListView lv_tripDetail = (ListView) v.findViewById(R.id.lv_tripDetail);

        Bundle arguments = getArguments();
        TripPlanDetail listDataObject = (TripPlanDetail) arguments.getSerializable("listDataObject");
        ArrayList<HashMap<String, Object>> listData = listDataObject.getListData();
        SimpleAdapter show = new SimpleAdapter(v.getContext(), listData, R.layout.listviewitem_mytrips_details,
                new String[]{"image1", "tv_attId", "tv_attName", "iv_rate", "tv_attReview", "tv_attAddress", "tv_attType"},
                new int[]{R.id.image1, R.id.tv_attId, R.id.tv_attName, R.id.iv_rate, R.id.tv_attReview, R.id.tv_attAddress, R.id.tv_attType});
        lv_tripDetail.setAdapter(show);
        return v;
    }

}