package com.triple.triple.Presenter.Attraction;

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
import com.triple.triple.Model.City;
import com.triple.triple.Model.TripDetail;
import com.triple.triple.R;
import com.triple.triple.UILibrary.VerticalVPOnTouchListener;
import com.triple.triple.UILibrary.VerticalViewPager;
import com.wang.avi.AVLoadingIndicatorView;

public class CityInfoCoverFragment extends Fragment {

    private ImageView image;
    private TextView tv_city, tv_country;
    private City city;
    private View layout_relative;
    private ImageView iv_arrow;
    private AnimatedVectorDrawable icon;
    private AVLoadingIndicatorView avi;
    private VerticalVPOnTouchListener verticalVPOnTouchListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        city = (City) getArguments().getSerializable("city");
        View view = inflater.inflate(R.layout.fragment_city_info_cover, container, false);
        layout_relative = view.findViewById(R.id.layout_relative);
        image = (ImageView) view.findViewById(R.id.image);
        tv_city = (TextView) view.findViewById(R.id.tv_city);
        tv_country = (TextView) view.findViewById(R.id.tv_country);
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
                .load(city.getPhoto())
                .fit().centerCrop()
                .transform(new BitmapTransform(Constant.IMAGE_M_WIDTH, Constant.IMAGE_M_HEIGHT))
                .into(image);
        tv_city.setText(city.getName());
        tv_country.setText(city.getCountry());

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
