package com.triple.triple.Presenter.Mytrips;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.triple.triple.Adapter.ItineraryFragmentAdapter;
import com.triple.triple.Model.TripDetail;
import com.triple.triple.R;
import com.triple.triple.UILibrary.VerticalViewPager;

import java.io.Serializable;

public class TripInfoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TripInfoCoverFragment fragment_cover;
    private TripInfoContentFragment fragment_content;
    private TripDetail tripDetail;
    private VerticalViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytrips_info);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        tripDetail = (TripDetail) bundle.getSerializable("tripDetail");
        findViews();
        initView();
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (VerticalViewPager) findViewById(R.id.vertical_viewpager);
    }

    private void initView() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        initViewPager();

    }

    private void initViewPager() {
        fragment_cover = new TripInfoCoverFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("tripDetail", (Serializable) tripDetail);
        bundle.putInt("position", 1);
        bundle.putSerializable("viewpager", viewPager);
        fragment_cover.setArguments(bundle);

        fragment_content = new TripInfoContentFragment();
        bundle = new Bundle();
        bundle.putInt("tripid", tripDetail.getId());
        bundle.putInt("position", 2);
        bundle.putSerializable("viewpager", viewPager);
        fragment_content.setArguments(bundle);

        viewPager.setAdapter(new ItineraryFragmentAdapter.Holder(getSupportFragmentManager())
                .add(fragment_cover)
                .add(fragment_content)
                .set());

        viewPager.setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                Drawable backArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
                switch (position) {
                    case 0:
                        backArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                        getSupportActionBar().setHomeAsUpIndicator(backArrow);
                        break;
                    case 1:
                        backArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
                        getSupportActionBar().setHomeAsUpIndicator(backArrow);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    public void setUnlock() {
        TripInfoCoverFragment fragment = (TripInfoCoverFragment) getSupportFragmentManager().findFragmentByTag(fragment_cover.getTag());
        fragment.setUnlock();
    }

}
