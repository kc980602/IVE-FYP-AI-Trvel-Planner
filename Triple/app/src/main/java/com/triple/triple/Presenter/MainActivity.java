package com.triple.triple.Presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.securepreferences.SecurePreferences;
import com.triple.triple.Helper.CheckLogin;
import com.triple.triple.Helper.DrawerUtil;
import com.triple.triple.Helper.SystemPropertyHelper;
import com.triple.triple.Helper.Token;
import com.triple.triple.Interface.ApiInterface;
import com.triple.triple.Model.SystemProperty;
import com.triple.triple.Presenter.Home.HomeFragment;
import com.triple.triple.Presenter.Mytrips.MyTripFragment;
import com.triple.triple.Presenter.Mytrips.MytripsActivity;
import com.triple.triple.Presenter.Profile.ProfileActivity;
import com.triple.triple.Presenter.Profile.TravelStyleActivity;
import com.triple.triple.Presenter.Search.SearchActivity;
import com.triple.triple.R;
import com.triple.triple.Sync.ApiClient;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    private Context mcontext = MainActivity.this;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestData();
        initView();

//        if (isShowPageStart) {
//            relative_main.setVisibility(View.VISIBLE);
//            mHandler.sendEmptyMessageDelayed(MESSAGE_SHOW_START_PAGE, 100);
//            isShowPageStart = false;
//        }
//
//        if (!Token.checkTokenExist(mcontext)) {
//            mHandler.sendEmptyMessageDelayed(MESSAGE_SHOW_LOGIN, 0);
//        }
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        displaySelectedScreen(R.id.nav_home);
        requestData();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_mytrips:
                fragment = new MyTripFragment();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_layout_main, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }

    public void requestData(){
        Call<SystemProperty> call = apiService.getProperty();
        call.enqueue(new Callback<SystemProperty>() {
            @Override
            public void onResponse(Call<SystemProperty> call, Response<SystemProperty> response) {
                SystemProperty sp = response.body();
                Gson gson = new Gson();
                SharedPreferences data = new SecurePreferences(mcontext);
                SharedPreferences.Editor editor = data.edit();
                editor.putString("systemProperty", gson.toJson(sp));
                editor.commit();
            }

            @Override
            public void onFailure(Call<SystemProperty> call, Throwable t) {

            }
        });
    }
}
