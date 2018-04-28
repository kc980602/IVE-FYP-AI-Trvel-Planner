package com.triple.triple.Presenter.Mytrips;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.triple.triple.Helper.BitmapTransform;
import com.triple.triple.Helper.Constant;
import com.triple.triple.Helper.DateTimeHelper;
import com.triple.triple.Model.TripDetail;
import com.triple.triple.R;
import com.triple.triple.UILibrary.VerticalViewPager;
import com.triple.triple.UILibrary.VerticalVPOnTouchListener;
import com.wang.avi.AVLoadingIndicatorView;

public class TripInfoCoverFragment extends Fragment {

    private ImageView image;
    private TextView tv_tripdate, tv_days, tv_city, tv_tripname;
    private TripDetail tripDetail;
    private View layout_relative;
    private ImageView iv_arrow;
    private AnimatedVectorDrawable icon;
    private AVLoadingIndicatorView avi;
    private VerticalVPOnTouchListener verticalVPOnTouchListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tripDetail = (TripDetail) getArguments().getSerializable("tripDetail");
        View view = inflater.inflate(R.layout.fragment_trip_info_cover, container, false);
        layout_relative = view.findViewById(R.id.layout_relative);
        image = (ImageView) view.findViewById(R.id.image);
        tv_tripdate = (TextView) view.findViewById(R.id.tv_tripdate);
        tv_days = (TextView) view.findViewById(R.id.tv_days);
        tv_city = (TextView) view.findViewById(R.id.tv_city);
        tv_tripname = (TextView) view.findViewById(R.id.tv_tripname);
        iv_arrow = (ImageView) view.findViewById(R.id.iv_arrow);
        avi = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
        initView();
        return view;
    }

    private void initView() {
        iv_arrow.setVisibility(View.INVISIBLE);
        verticalVPOnTouchListener = new VerticalVPOnTouchListener((VerticalViewPager) getArguments().getSerializable("viewpager"));
        verticalVPOnTouchListener.setIsLock(true);
        layout_relative.setOnTouchListener(verticalVPOnTouchListener);
        Picasso.with(getContext())
                .load(tripDetail.getCity().getPhoto())
                .fit().centerCrop()
                .transform(new BitmapTransform(Constant.IMAGE_M_WIDTH, Constant.IMAGE_M_HEIGHT))
                .placeholder(R.drawable.ic_image_null_v)
                .into(image);
        String date = DateTimeHelper.castDateToLocale(tripDetail.getVisit_date()) + " - " + DateTimeHelper.castDateToLocale(DateTimeHelper.endDate(tripDetail.getVisit_date(), tripDetail.getVisit_length()));
        tv_tripdate.setText(date);
        tv_days.setText(tripDetail.getVisit_length() + " " + getString(R.string.mytrips_article_days));
        tv_city.setText(tripDetail.getCity().getName() + ", " + tripDetail.getCity().getCountry());
        tv_tripname.setText(tripDetail.getTitle());

        Drawable drawable = iv_arrow.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }

    public String getTitle() {
        return getArguments().getString("title");
    }

    public int getPosition() {
        return getArguments().getInt("position");
    }

    public void setUnlock() {
        verticalVPOnTouchListener.setIsLock(false);
        avi.hide();
        iv_arrow.setVisibility(View.VISIBLE);
    }

}
