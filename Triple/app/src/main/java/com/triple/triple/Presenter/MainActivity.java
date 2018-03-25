package com.triple.triple.Presenter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
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
import android.widget.ImageView;

import com.google.gson.Gson;
import com.securepreferences.SecurePreferences;
import com.triple.triple.Helper.CheckLogin;
import com.triple.triple.Helper.Constant;
import com.triple.triple.Helper.Token;
import com.triple.triple.Interface.ApiInterface;
import com.triple.triple.Model.SystemProperty;
import com.triple.triple.Presenter.Attraction.AttractionImageActivity;
import com.triple.triple.Presenter.Home.HomeFragment;
import com.triple.triple.Presenter.Mytrips.MytripsFragment;
import com.triple.triple.R;
import com.triple.triple.Sync.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    private Toolbar toolbar;
    private ImageView iv_iconText;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeNoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        requestData();
        findView();
        initView();
        if (!Token.checkTokenExist(this)) {
            CheckLogin.directLogin(this);
        }
    }

    private void findView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        iv_iconText = (ImageView) findViewById(R.id.iv_iconText);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    private void initView() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

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
        return item.getItemId() == R.id.action_search || super.onOptionsItemSelected(item);
    }


    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_home:
                toolbaHome();
                fragment = new HomeFragment();
                break;
            case R.id.nav_mytrips:
                toolbarNormal(R.string.title_mytrips);
                fragment = new MytripsFragment();
                break;
            case R.id.nav_help:
                Intent intent = new Intent(MainActivity.this, AttractionImageActivity.class);
                startActivity(intent);
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

    private void toolbarNormal(int title) {
        getSupportActionBar().setTitle(getString(title));
        iv_iconText.setVisibility(View.GONE);
    }

    private void toolbaHome() {
        getSupportActionBar().setTitle("");
        iv_iconText.setVisibility(View.VISIBLE);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }

    public void requestData() {
        Call<SystemProperty> call = apiService.getProperty();
        call.enqueue(new Callback<SystemProperty>() {
            @Override
            public void onResponse(Call<SystemProperty> call, Response<SystemProperty> response) {
                SystemProperty sp = response.body();
                Gson gson = new Gson();
                SharedPreferences data = getSharedPreferences(Constant.SharedPreferences, 0);
                data.edit()
                        .putString("systemProperty", gson.toJson(sp))
                        .commit();
            }

            @Override
            public void onFailure(Call<SystemProperty> call, Throwable t) {
                Log.e("getSystemProperty", t.getMessage());
            }
        });
    }
}
