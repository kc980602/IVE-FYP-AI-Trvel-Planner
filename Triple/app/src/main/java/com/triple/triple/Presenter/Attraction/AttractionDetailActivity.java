package com.triple.triple.Presenter.Attraction;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.mypopsy.maps.StaticMap;
import com.squareup.picasso.Picasso;
import com.triple.triple.Adapter.AttractionCommentAdapter;
import com.triple.triple.Helper.AppBarStateChangeListener;
import com.triple.triple.Helper.BitmapTransform;
import com.triple.triple.Helper.Constant;
import com.triple.triple.Helper.RecycleViewPaddingHelper;
import com.triple.triple.Helper.UserDataHelper;
import com.triple.triple.Model.Attraction;
import com.triple.triple.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.net.MalformedURLException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.triple.triple.Helper.CheckLogin.directLogin;

public class AttractionDetailActivity extends AppCompatActivity {

    private static final String TAG = "AttractionDetail";
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
    private ImageView image;
    private RecyclerView rv_attraction_comments;
    private AttractionCommentAdapter adapter;
    private Button btn_attraction_review;


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
        getUserDetails();
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
        image = (ImageView) findViewById(R.id.image);
        rv_attraction_comments = (RecyclerView) findViewById(R.id.rv_attraction_comments);
        btn_attraction_review = (Button) findViewById(R.id.btn_attraction_review);
    }

    private void initView() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        tv_intro.setText(attraction.getDescription());
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

        if (!attraction.getComments().isEmpty()){
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getApplicationContext());
            adapter = new AttractionCommentAdapter(this, attraction.getComments());
            rv_attraction_comments.setHasFixedSize(true);
            rv_attraction_comments.setLayoutManager(mLayoutManager);
            rv_attraction_comments.setItemAnimator(new DefaultItemAnimator());
            rv_attraction_comments.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            RecyclerView.ItemDecoration dividerItemDecoration = new RecycleViewPaddingHelper(90);
            rv_attraction_comments.addItemDecoration(dividerItemDecoration);
            rv_attraction_comments.setNestedScrollingEnabled(false);
        }

        image_map.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        image_map.getViewTreeObserver().removeOnGlobalLayoutListener(this);


                        View v = (View) findViewById(R.id.image_map);
                        StaticMap map = new StaticMap()
                                .center(new StaticMap.GeoPoint(attraction.getLatitude(), attraction.getLongitude()))
                                .size(v.getWidth() / 2, v.getHeight() / 2)
                                .zoom(18)
                                .scale(2).marker(new StaticMap.GeoPoint(attraction.getLatitude(), attraction.getLongitude()));
                        try {
                            Picasso.with(mcontext)
                                    .load(String.valueOf(map.toURL()))
                                    .placeholder(R.drawable.ic_image_null_uw)
                                    .into(image_map);
                        } catch (MalformedURLException e) {
                        }
                        image_map.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    }

                });


        layout_gallery = (LinearLayout) findViewById(R.id.layout_gallery);

        if (attraction.getPhotos().size() > 0) {
            Picasso.with(mcontext)
                    .load(attraction.getPhotos().get(0))
                    .fit().centerCrop()
                    .transform(new BitmapTransform(Constant.IMAGE_M_WIDTH, Constant.IMAGE_M_HEIGHT))
                    .placeholder(R.drawable.ic_image_null_h)
                    .into(image);
            layout_gallery.removeAllViews();
            for (int i = 0; i < attraction.getPhotos().size(); i++) {
                View view = mInflater.inflate(R.layout.listitem_gallery, layout_gallery,
                        false);
                ImageView image = (ImageView) view.findViewById(R.id.image_gallery_item);
                Picasso.with(mcontext)
                        .load(attraction.getPhotos().get(i))
                        .fit().centerCrop()
                        .placeholder(R.drawable.ic_image_null_s)
                        .into(image);
                layout_gallery.addView(view);
            }
        }

        layout_main.setVisibility(View.VISIBLE);
        layout_main.invalidate();
    }

    public void setBookmark() {
        String token = "Bearer ";
        token += UserDataHelper.getToken(mcontext);
        Call<Void> call = Constant.apiService.setBookmark(token, attractionId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    setBookmarkSuccess();
                    fab.setImageResource(R.drawable.ic_bookmark_saved);
                } else {
                    Log.e(TAG, "respone fail");
                    setBookmarkFail();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                setBookmarkFail();
            }
        });
    }

    private void setBookmarkSuccess() {
        View view = getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(view, getString(R.string.attraction_detail_bookmark_success), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.snackbar_ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {}
                }).show();
    }

    private void setBookmarkFail() {
        View view = getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(view, getString(R.string.attraction_detail_bookmark_fail), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.snackbar_ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {}
                }).show();
    }

    public void getUserDetails() {
        startAnim();
        Call<Attraction> call = Constant.apiService.getInfo(attractionId);
        call.enqueue(new Callback<Attraction>() {
            @Override
            public void onResponse(Call<Attraction> call, Response<Attraction> response) {
                if (response.body() != null) {
                    attraction = response.body();
                    loadDataToView();
                } else {
                    Log.d("error", "User details does not exist");
                }
                stopAnim();
            }

            @Override
            public void onFailure(Call<Attraction> call, Throwable t) {
                Log.e("error", t.toString());
                stopAnim();
            }
        });

    }


    public void startAnim() {
        avi.show();
    }

    public void stopAnim() {
        avi.hide();
    }

    public void onImageClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("attraction", attraction);
        Intent intent = new Intent(mcontext, AttractionImageActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onFabClick(View view) {
        setBookmark();
    }

    public void onButtonReviewClick(View view){
        directLogin(mcontext);
        Bundle bundle = new Bundle();
        bundle.putSerializable("attraction", attraction);
        Intent intent = new Intent(mcontext, AttractionReviewActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserDetails();

    }
}
