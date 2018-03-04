package com.triple.triple.Presenter.Attraction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ScrollingView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mypopsy.maps.StaticMap;
import com.squareup.picasso.Picasso;
import com.triple.triple.Helper.AppBarStateChangeListener;
import com.triple.triple.R;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

public class AttractionDetailActivity extends AppCompatActivity {

    private Context mcontext = AttractionDetailActivity.this;

    private Toolbar toolbar;
    private Menu menu;
    private AppBarLayout abl_appbar;
    private CollapsingToolbarLayout ctl_collapsing;
    private FloatingActionButton fab;
    private ScrollView layout_scrollview;
    private AppBarLayout layout_appbar;
    private CollapsingToolbarLayout layout_collapsing;
    private MapView mapView;
    private GoogleMap map;
    private ImageView image_map;
    private LinearLayout insertPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_detail);
        findViews();
        initView();
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        layout_appbar = (AppBarLayout) findViewById(R.id.layout_appbar);
        layout_collapsing = (CollapsingToolbarLayout) findViewById(R.id.layout_collapsing);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        image_map = (ImageView) findViewById(R.id.image_map);
        insertPoint = (LinearLayout) findViewById(R.id.layout_info);
    }

    private void initView() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Home Hotel");
        }
        layout_appbar.addOnOffsetChangedListener(new AppBarStateChangeListener() {

            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    hideOption(R.id.action_bookmark);
                } else if (state == State.COLLAPSED) {
                    showOption(R.id.action_bookmark);
                } else {
                    hideOption(R.id.action_bookmark);
                }
            }
        });

        image_map.getViewTreeObserver().addOnGlobalLayoutListener(new MyGlobalListenerClass());

        String[] info = new String[2];
        info[0] = "info 1";
        info[1] = "info 2";

        LayoutInflater layoutInflator = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        List<View> views = new ArrayList();

        HashMap infoMap = new HashMap();
        infoMap.put("phone", "+886287890111");
        infoMap.put("website", "http://www.homehotel.com.tw");
        infoMap.put("phone", "+886287890111");
        Iterator it = infoMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            View view = layoutInflator.inflate(R.layout.listitem_attraction_info, null);
            TextView textView = (TextView) view.findViewById(R.id.tv_attInfo);
            textView.setText(pair.getValue().toString());
            views.add(view);
            it.remove();
        }

        for(int i = 0; i < views.size(); i++)
            insertPoint.addView((View) views.get(i));
    }

    class MyGlobalListenerClass implements ViewTreeObserver.OnGlobalLayoutListener {
        @Override
        public void onGlobalLayout() {
            View v = (View) findViewById(R.id.image_map);
            StaticMap map = new StaticMap()
                    .center(new StaticMap.GeoPoint(25.0348923, 121.5676676))
                    .size(v.getWidth() / 2, v.getHeight() / 2)
                    .zoom(18)
                    .scale(2).marker(new StaticMap.GeoPoint(25.0348923, 121.5676676));

            try {
                Picasso.with(mcontext)
                        .load(String.valueOf(map.toURL()))
                        .into(image_map);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.toolbar_attraction_detail, menu);
        hideOption(R.id.action_bookmark);
        return true;
    }

    private void hideOption(int id) {
        if (menu != null) {
            MenuItem item = menu.findItem(id);
            item.setVisible(false);
        }
    }

    private void showOption(int id) {
        if (menu != null) {
            MenuItem item = menu.findItem(id);
            item.setVisible(true);
        }
    }
//
//    public void onClick(View view) {
//        TextView tv = (TextView) view;
//        Log.d("aac", tv.getText().toString());
//    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, prepareShareMessage());
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, getString(R.string.intent_share_header)));
                break;
            case R.id.action_bookmark:

                break;
            case android.R.id.home:
                //super.onBackPressed();
                break;
        }
        return true;
    }

    private String prepareShareMessage() {
        String message = String.format(getResources().getString(R.string.intent_share_message_attraction), "Home Hotel", "http://tripleapi-env.ap-southeast-1.elasticbeanstalk.com/attraction/1643");
        return message;
    }
}
