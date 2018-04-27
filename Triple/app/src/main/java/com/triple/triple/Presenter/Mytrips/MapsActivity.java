package com.triple.triple.Presenter.Mytrips;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.config.GoogleDirectionConfiguration;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.triple.triple.Model.TripItinerary;
import com.triple.triple.Model.TripItineraryNode;
import com.triple.triple.R;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, DirectionCallback  {

    private GoogleMap googleMap;
    private String serverKey = "AIzaSyAhUiOpN5cILuciEpCWvCWNss60owhV_vs";
    private TripItinerary itinerary;
    private List<TripItineraryNode> nodes;
    private Toolbar toolbar;
    SupportMapFragment mapFragment;

    private LatLng start;
    private LatLng end;
    private ArrayList<LatLng> places = new ArrayList<LatLng>();
    private String startName, endName;
    private ArrayList<String> names = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        findView();
        initView();

    }

    private void findView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
    }

    private void initView(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        itinerary = (TripItinerary) bundle.getSerializable("itinerary");
        nodes = itinerary.getNodes();
        setSupportActionBar(toolbar);
        mapFragment.getMapAsync(this);
        start = new LatLng(nodes.get(0).getAttraction().getLatitude(),nodes.get(0).getAttraction().getLongitude());
        startName = nodes.get(0).getAttraction().getName();
        end = new LatLng(nodes.get((nodes.size()-1)).getAttraction().getLatitude(),nodes.get((nodes.size()-1)).getAttraction().getLongitude());
        endName = nodes.get((nodes.size()-1)).getAttraction().getName();
        for(int i = 1; i < nodes.size(); i++){
            places.add(new LatLng(nodes.get(i).getAttraction().getLatitude(),nodes.get(i).getAttraction().getLongitude()));
            names.add(nodes.get(i).getAttraction().getName());
        }
        names.add(0,startName);
        names.add(endName);
        Log.e("size", String.valueOf(nodes.size()));

        requestDirection();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public void requestDirection() {
        GoogleDirectionConfiguration.getInstance().setLogEnabled(true);
        GoogleDirection.withServerKey(serverKey)
                .from(start)
                .and(places)
                .to(end)
                .transportMode(TransportMode.DRIVING)
                .execute((DirectionCallback) this);
    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {

        if (direction.isOK()) {
            Route route = direction.getRouteList().get(0);
            int legCount = route.getLegList().size();
            for (int index = 0; index < legCount; index++) {
                Leg leg = route.getLegList().get(index);
                googleMap.addMarker(new MarkerOptions().position(leg.getStartLocation().getCoordination()).title( (index + 1) + ". "+ names.get(index))).showInfoWindow();
                if (index == legCount - 1) {
                    googleMap.addMarker(new MarkerOptions().position(leg.getEndLocation().getCoordination()));
                }
                List<Step> stepList = leg.getStepList();
                ArrayList<PolylineOptions> polylineOptionList = DirectionConverter.createTransitPolyline(this, stepList, 5, Color.RED, 3, Color.BLUE);
                for (PolylineOptions polylineOption : polylineOptionList) {
                    googleMap.addPolyline(polylineOption);
                }
            }
            setCameraWithCoordinationBounds(route);
        }
    }


    @Override
    public void onDirectionFailure(Throwable t) {

    }

    private void setCameraWithCoordinationBounds(Route route) {
        LatLng southwest = route.getBound().getSouthwestCoordination().getCoordination();
        LatLng northeast = route.getBound().getNortheastCoordination().getCoordination();
        LatLngBounds bounds = new LatLngBounds(southwest, northeast);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                super.onBackPressed();
                break;
        }
        return true;
    }
}
