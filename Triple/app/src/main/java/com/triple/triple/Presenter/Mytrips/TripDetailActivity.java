package com.triple.triple.Presenter.Mytrips;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.squareup.picasso.Picasso;
import com.triple.triple.Adapter.TripDayAdapter;
import com.triple.triple.Helper.BitmapTransform;
import com.triple.triple.Helper.Constant;
import com.triple.triple.Helper.DateTimeHelper;
import com.triple.triple.Helper.UserDataHelper;
import com.triple.triple.Interface.ApiInterface;
import com.triple.triple.Model.TripDay;
import com.triple.triple.Model.TripDetail;
import com.triple.triple.Model.TripItinerary;
import com.triple.triple.Model.TripItineraryNode;
import com.triple.triple.R;
import com.triple.triple.Sync.ApiClient;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripDetailActivity extends AppCompatActivity {
    private static final String TAG = "TripDetailActivity";

    private Context mcontext = TripDetailActivity.this;
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    private BottomNavigationViewEx bottomNavigationViewEx, bottomNavigationViewEx_all, bottomNavigationViewEx_saved;
    private List<TripDay> tripdays = new ArrayList<>();
    private TripDayAdapter adapter;
    private TripDetail tripDetail;

    private TextView tv_tripdate, tv_tripdaysleftMessage, tv_tripdaysleft;
    private AVLoadingIndicatorView avi;
    private ImageView image;
    private ActionBar ab;
    private int tripid;
    private TextView tv_tripdestination;
    private Boolean isNew = false;
    private RecyclerView rv_tripdaylist;
    private NestedScrollView layout_main;
    private CardView layout_ongo;
    private TextView tv_tag;
    private TextView tv_time;
    private TextView tv_name;
    private TextView tv_address;
    private int displayNodeIndex;
    private TripItineraryNode displayNode;
    private boolean isBefore;
    private boolean isDone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytrips_detail);
        findView();
        initView();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        isNew = (Boolean) bundle.getBoolean("isNew");
        if (!isNew) {
            tripid = bundle.getInt("tripid");
            findView();
            initView();
            requestTripItinerary();
        } else {
            tripDetail = (TripDetail) bundle.getSerializable("tripDetail");
            findView();
            initView();
            afterGetData();
        }
    }

    private void findView() {
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.nav_trip_card);
        tv_tripdate = (TextView) findViewById(R.id.tv_tripdate);
        tv_tripdaysleftMessage = (TextView) findViewById(R.id.tv_tripdaysleftMessage);
        tv_tripdaysleft = (TextView) findViewById(R.id.tv_tripdaysleft);
        rv_tripdaylist = (RecyclerView) findViewById(R.id.rv_tripdaylist);
        tv_tripdestination = (TextView) findViewById(R.id.tv_tripdestination);
        image = (ImageView) findViewById(R.id.image);
        layout_main = (NestedScrollView) findViewById(R.id.layout_main);
        layout_ongo = (CardView) findViewById(R.id.layout_ongo);
        tv_tag = (TextView) findViewById(R.id.tv_tag);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_address = (TextView) findViewById(R.id.tv_address);
    }

    private void initView() {
        ab = getSupportActionBar();
        ab.setElevation(0);
        String indicator = getIntent().getStringExtra("indicator");

        layout_main.setVisibility(View.INVISIBLE);

        avi.setIndicator(indicator);
        stopAnim();

        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(bottomNavigationViewExListener);
        Menu menu = bottomNavigationViewEx.getMenu();
    }

    private ListView.OnItemClickListener lv_tripdaylistListener = new ListView.OnItemClickListener() {
        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_mytrips_detail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                AlertDialog.Builder alert = new AlertDialog.Builder(mcontext);
                alert.setTitle(R.string.mytrips_detail_delete_title);
                alert.setMessage(R.string.mytrips_detail_delete_content);
                alert.setPositiveButton(R.string.mytrips_detail_delete_yes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        fetchRemoveTrip();
                    }
                });
                alert.setNegativeButton(R.string.mytrips_detail_delete_no, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
                break;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                finish();
                break;
        }
        return true;
    }

    private BottomNavigationViewEx.OnNavigationItemSelectedListener bottomNavigationViewExListener = new BottomNavigationViewEx.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Bundle bundle = new Bundle();
            Intent intent = new Intent();
            switch (item.getItemId()) {
                case R.id.action_info:
                    bundle.putSerializable("tripDetail", tripDetail);
                    intent.setClass(mcontext, TripInfoActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case R.id.action_itenary:
                    bundle.putSerializable("tripDetail", tripDetail);
                    intent.setClass(mcontext, ItineraryActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case android.R.id.home:
                    NavUtils.navigateUpFromSameTask(TripDetailActivity.this);
                    setResult(Activity.RESULT_OK);
                    finish();
                    break;
            }
            return false;
        }
    };

    private void fetchRemoveTrip() {
        String token = "Bearer ";
        token += UserDataHelper.getToken(mcontext);
        Call call = apiService.removeTrip(token, tripid);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.code() == 204) {
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    requestFail();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                requestFail();
            }
        });
    }

    public void requestTripItinerary() {
        startAnim();
        String token = "Bearer ";
        token += UserDataHelper.getToken(mcontext);
        Call<TripDetail> call = apiService.listTripByUser(token, tripid);
        call.enqueue(new Callback<TripDetail>() {
            @Override
            public void onResponse(Call<TripDetail> call, Response<TripDetail> response) {
                tripDetail = response.body();
                stopAnim();
                afterGetData();
            }

            @Override
            public void onFailure(Call<TripDetail> call, Throwable t) {
                stopAnim();
                requestFail();
            }
        });
    }

    private void afterGetData() {
        ab.setTitle(tripDetail.getTitle());

        Picasso.with(mcontext)
                .load(tripDetail.getCity().getPhoto())
                .fit().centerCrop()
                .transform(new BitmapTransform(Constant.IMAGE_M_WIDTH, Constant.IMAGE_M_HEIGHT))
                .placeholder(R.drawable.ic_image_null_h)
                .into(image);

        List<TripItinerary> itineraryList = tripDetail.getItinerary();
        for (int i = 0; i < itineraryList.size(); i++) {
            TripDay tripday = new TripDay();
            TripItinerary itinerary = itineraryList.get(i);
            tripday.setId(itinerary.getId());
            tripday.setName("Day" + (i + 1));
            tripday.setDesc(DateTimeHelper.castDateToLocaleFull(itinerary.getVisit_date()));
            tripdays.add(tripday);

            if (DateTimeHelper.isToday(itinerary.getVisit_date()) && !isDone) {
                Log.e("isToday", "true");
                List<TripItineraryNode> tripItineraryNode = itinerary.getNodes();
                for (int j = 0; j < tripItineraryNode.size(); j++) {
                    TripItineraryNode node = tripItineraryNode.get(j);
                    if (node.getType() == null) {
                        node.setType("");
                    }
                    if (DateTimeHelper.isCurrentORBefore(itinerary.getVisit_date(), node.getVisit_time(), node.getDuration()) && !node.getType().equals("LODGING")) {
                        displayNode = tripItineraryNode.get(j);
                        displayNodeIndex = j;
                        isBefore = DateTimeHelper.isBefore(node.getVisit_time());
                        isDone = true;
                        break;
                    }
                }
            } else if(DateTimeHelper.isTmr(itinerary.getVisit_date()) || !isDone){
                List<TripItineraryNode> tripItineraryNode = itinerary.getNodes();
                for (int j = 0; j <  tripItineraryNode.size(); j++) {
                    TripItineraryNode node = tripItineraryNode.get(j);
                    if (node.getType() == null) {
                        node.setType("");
                    }
                    if (!node.getType().equals("LODGING")) {
                        displayNode = tripItineraryNode.get(1);
                        displayNodeIndex = j;
                        isBefore = true;
                        isDone = true;
                        break;
                    }
                }
            }
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mcontext.getApplicationContext());
        rv_tripdaylist.setHasFixedSize(true);
        rv_tripdaylist.setLayoutManager(mLayoutManager);
        rv_tripdaylist.setItemAnimator(new DefaultItemAnimator());
        TripDayAdapter adapter = new TripDayAdapter(mcontext, tripdays, tripDetail);
        rv_tripdaylist.setAdapter(adapter);

        tv_tripdate.setText(DateTimeHelper.castDateToLocale(tripDetail.getVisit_date()) + " - " + DateTimeHelper.castDateToLocale(DateTimeHelper.endDate(tripDetail.getVisit_date(), tripDetail.getVisit_length())));
        int dayLeft = DateTimeHelper.daysLeft(tripDetail.getVisit_date());
        if (dayLeft == 0) {
            if (displayNode != null) {
                tv_tripdaysleftMessage.setText(getString(R.string.mytrips_detail_daysleft_after));
                tv_name.setText(displayNode.getAttraction().getName());
                tv_address.setText(displayNode.getAttraction().getAddress());
                tv_time.setText(DateTimeHelper.removeSec(displayNode.getVisit_time()) + "-" + DateTimeHelper.endTime(displayNode.getVisit_time(), displayNode.getDuration()));
                tv_tag.setText(getString(isBefore ? R.string.mytrips_detail_next : R.string.mytrips_detail_now));
            } else {
                layout_ongo.setVisibility(View.GONE);
            }

        } else {
            tv_tripdaysleftMessage.setText(getString(R.string.mytrips_detail_daysleft_before));
            layout_ongo.setVisibility(View.GONE);
        }
        tv_tripdestination.setText(tripDetail.getCity().getName() + ", " + tripDetail.getCity().getCountry());
        tv_tripdaysleft.setText(String.valueOf(dayLeft));

        layout_main.setVisibility(View.VISIBLE);
    }

    private void requestFail() {
        View view = getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(view, "Something went wrong! (Error:1103)", Snackbar.LENGTH_LONG);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void clickTest(View view) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("itinerary", tripDetail.getItinerary().get(0));
        intent.setClass(this, NewTripDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onGoClick(View view) {
        Uri gmmIntentUri = Uri.parse(String.format("geo:%f,%f?z=%d&q=%f,%f(%s)", displayNode.getAttraction().getLatitude(), displayNode.getAttraction().getLongitude(), 17, displayNode.getAttraction().getLatitude(), displayNode.getAttraction().getLongitude(), displayNode.getAttraction().getName()));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void startAnim() {
        avi.show();
    }

    public void stopAnim() {
        avi.hide();
    }

}
