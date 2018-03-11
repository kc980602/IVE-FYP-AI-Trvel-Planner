package com.triple.triple.Presenter.Attraction;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ScrollingView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mypopsy.maps.StaticMap;
import com.squareup.picasso.Picasso;
import com.triple.triple.Helper.AppBarStateChangeListener;
import com.triple.triple.Model.Attraction;
import com.triple.triple.Model.Trip;
import com.triple.triple.R;
import com.triple.triple.Sync.GetAttractionDetail;
import com.triple.triple.Sync.GetTrip;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
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
    private LinearLayout layout_gallery;
    private LayoutInflater mInflater;
    private AVLoadingIndicatorView avi;
    private CoordinatorLayout layout_main;
    private TextView tv_intro, tv_attInfo_phone, tv_attInfo_website, tv_attInfo_address;
    private Attraction attraction;
    private String url;
    private Integer attractionId;
    private String attractionName = "";
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_detail);

        Intent intent = getIntent();
        String appLinkAction = intent.getAction();
        Uri appLinkData = intent.getData();
        if (appLinkData != null) {
            attractionId = Integer.valueOf(appLinkData.getLastPathSegment());
        } else {
            Bundle bundle = intent.getExtras();
            attractionId = (Integer) bundle.getInt("attractionId");
        }

        findViews();
        initView();
        new AttractionDetailActivity.RequestAttractionDetail().execute();
    }

    private void findViews() {
        mInflater = LayoutInflater.from(this);
        layout_main = (CoordinatorLayout) findViewById(R.id.layout_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        layout_appbar = (AppBarLayout) findViewById(R.id.layout_appbar);
        layout_collapsing = (CollapsingToolbarLayout) findViewById(R.id.layout_collapsing);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        image_map = (ImageView) findViewById(R.id.image_map);
        insertPoint = (LinearLayout) findViewById(R.id.layout_info);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_intro = (TextView) findViewById(R.id.tv_intro);
        tv_attInfo_phone = (TextView) findViewById(R.id.tv_attInfo_phone);
        tv_attInfo_website = (TextView) findViewById(R.id.tv_attInfo_website);
        tv_attInfo_address = (TextView) findViewById(R.id.tv_attInfo_address);
    }

    private void initView() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        layout_collapsing.setTitle("");
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
        image_map.setOnClickListener(image_mapListener);

        layout_main.setVisibility(View.INVISIBLE);
        layout_main.invalidate();
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
                super.onBackPressed();
                finish();
                break;
        }
        return true;
    }

    View.OnClickListener image_mapListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Uri gmmIntentUri = Uri.parse(String.format("geo:%f,%f?z=%d", attraction.getLatitude(), attraction.getLongitude(), 17));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
    };

    private String prepareShareMessage() {
        String link = getResources().getString(R.string.local_api_prefix) + getResources().getString(R.string.api_attraction) + "/" + attraction.getId();
        String message = String.format(getResources().getString(R.string.intent_share_message_attraction), attraction.getName(), link);
        return message;
    }

    private void loadDataToView() {
        attractionName = attraction.getName();
        layout_collapsing.setTitle(attractionName);
        tv_title.setText(attraction.getName());
        if (!attraction.getPhone().equals("")) {
            tv_attInfo_phone.setText(attraction.getPhone().toString());
            tv_attInfo_phone.setVisibility(View.VISIBLE);
        }
        if (!attraction.getWebsite().equals("")) {
            tv_attInfo_website.setText(attraction.getWebsite().toString());
            tv_attInfo_website.setVisibility(View.VISIBLE);
        }
        if (!attraction.getAddress().equals("")) {
            tv_attInfo_address.setText(attraction.getAddress().toString());
            tv_attInfo_address.setVisibility(View.VISIBLE);
        }

        image_map.getViewTreeObserver().addOnGlobalLayoutListener(new MyGlobalListenerClass());

        layout_gallery = (LinearLayout) findViewById(R.id.layout_gallery);

        int[] data = new int[]{R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a6, R.drawable.a7};
        for (int i = 0; i < data.length; i++) {
            View view = mInflater.inflate(R.layout.listitem_gallery, layout_gallery, false);
            ImageView img = (ImageView) view.findViewById(R.id.image_gallery_item);
            Picasso.with(mcontext)
                    .load(data[i])
                    .placeholder(R.color.white)
                    .into(img);
            layout_gallery.addView(view);
            if (i == 9) {
                break;
            }
        }
        View view = mInflater.inflate(R.layout.listitem_gallery_more, layout_gallery, false);
        layout_gallery.addView(view);
        layout_main.setVisibility(View.VISIBLE);
        layout_main.invalidate();
    }

    private class RequestAttractionDetail extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            startAnim();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String respone = "Error";
            try {
//                url = getResources().getString(R.string.api_prefix) + getResources().getString(R.string.api_attraction) + "/" + attractionId;
//                respone = new GetAttractionDetail().run(url);
                url = getResources().getString(R.string.api_prefix);
                respone = new GetAttractionDetail().run(url, attractionId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return respone;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject data = new JSONObject(result);
                Type type = new TypeToken<Attraction>() {
                }.getType();
                Gson gson = new Gson();
                attraction = (Attraction) gson.fromJson(data.toString(), type);
                loadDataToView();
            } catch (Exception e) {
                Log.d("Error", e.toString());
                Log.e("ErroronPostExecute", "exception", e);
//                new AttractionDetailActivity.RequestAttractionDetail().execute();
                View view = getWindow().getDecorView().findViewById(android.R.id.content);
                Snackbar.make(view, getString(R.string.mytrips_error), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.snackbar_ok), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();
            }
            stopAnim();
        }
    }

    public void startAnim() {
        avi.show();
    }

    public void stopAnim() {
        avi.hide();
    }

    class MyGlobalListenerClass implements ViewTreeObserver.OnGlobalLayoutListener {
        @Override
        public void onGlobalLayout() {
            View v = (View) findViewById(R.id.image_map);
            StaticMap map = new StaticMap()
                    .center(new StaticMap.GeoPoint(attraction.getLatitude(), attraction.getLongitude()))
                    .size(v.getWidth() / 2, v.getHeight() / 2)
                    .zoom(18)
                    .scale(2).marker(new StaticMap.GeoPoint(attraction.getLatitude(), attraction.getLongitude()));
            try {
                Picasso.with(mcontext)
                        .load(String.valueOf(map.toURL()))
                        .into(image_map);
            } catch (MalformedURLException e) {
                View view = getWindow().getDecorView().findViewById(android.R.id.content);
                Snackbar.make(view, getString(R.string.mytrips_error), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.snackbar_ok), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();
            }
        }
    }
}
