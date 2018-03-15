package com.triple.triple.Presenter.Mytrips;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.triple.triple.Helper.DragLayout;
import com.triple.triple.Helper.SystemPropertyHelper;
import com.triple.triple.Model.TripDetail;
import com.triple.triple.R;

import java.io.Serializable;

public class TripInfoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TripInfoCoverFragment fragment_cover;
    private TripInfoContentFragment fragment_content;
    private DragLayout draglayout;
    private TripDetail tripDetail;

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
        draglayout = (DragLayout) findViewById(R.id.draglayout);
    }

    private void initView() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        fragment_cover = new TripInfoCoverFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("tripDetail", (Serializable) tripDetail);
        fragment_cover.setArguments(bundle);
        fragment_content = new TripInfoContentFragment();
        Bundle bundle2 = new Bundle();
        bundle.putInt("tripid", tripDetail.getId());
        fragment_content.setArguments(bundle2);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.first, fragment_cover).add(R.id.second, fragment_content)
                .commit();

        DragLayout.ShowNextPageNotifier nextIntf = new DragLayout.ShowNextPageNotifier() {
            @Override
            public void onDragNext() {
            }
        };
        
        draglayout.setNextPageListener(nextIntf);

    }



}
