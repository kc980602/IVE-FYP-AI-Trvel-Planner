package com.triple.triple.Presenter.Mytrips;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.triple.triple.Helper.DateTimeHelper;
import com.triple.triple.Model.Trip;
import com.triple.triple.Model.TripDetail;
import com.triple.triple.R;

public class TripInfoCoverFragment extends Fragment {

    private ImageView image;
    private TextView tv_tripdate, tv_days, tv_city, tv_tripname;
    private TripDetail tripDetail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tripDetail = (TripDetail) getArguments().getSerializable("tripDetail");
        View view = inflater.inflate(R.layout.fragment_trip_info_cover, container, false);
        image = (ImageView) view.findViewById(R.id.image);
        tv_tripdate = (TextView) view.findViewById(R.id.tv_tripdate);
        tv_days = (TextView) view.findViewById(R.id.tv_days);
        tv_city = (TextView) view.findViewById(R.id.tv_city);
        tv_tripname = (TextView) view.findViewById(R.id.tv_tripname);
        initView();
        return view;
    }

    private void initView() {
        Picasso.with(getContext())
                .load(tripDetail.getCity().getPhoto())
                .fit().centerCrop()
                .placeholder(R.drawable.image_null_tran)
                .into(image);
        String date = DateTimeHelper.castDateToLocale(tripDetail.getVisit_date()) + " - " + DateTimeHelper.castDateToLocale(DateTimeHelper.endDate(tripDetail.getVisit_date(), tripDetail.getVisit_length()));
        tv_tripdate.setText(date);
        tv_days.setText(tripDetail.getVisit_length() + " " + getString(R.string.mytrips_info_days));
        tv_city.setText(tripDetail.getCity().getName() + ", " + tripDetail.getCity().getCountry());
        tv_tripname.setText(tripDetail.getTitle());

    }
}
