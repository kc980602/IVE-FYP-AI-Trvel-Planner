package com.triple.triple.Presenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.triple.triple.Helper.CheckLogin;
import com.triple.triple.Helper.Constant;
import com.triple.triple.Helper.UserDataHelper;
import com.triple.triple.Interface.ApiInterface;
import com.triple.triple.Model.User;
import com.triple.triple.Presenter.Account.SettingFragment;
import com.triple.triple.Presenter.Home.HomeFragment;
import com.triple.triple.Presenter.Mytrips.MytripsFragment;
import com.triple.triple.Presenter.Account.ProfileActivity;
import com.triple.triple.Presenter.Profile.TravelStyleFragment;
import com.triple.triple.R;
import com.triple.triple.Sync.ApiClient;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    private Context mcontext = MainActivity.this;

    private Toolbar toolbar;
    private ImageView iv_iconText;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FrameLayout frame_layout_main;
    private ImageView iv_avatar;
    private TextView tv_username;
    private TextView tv_email;
    private LinearLayout layout_nav_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeNoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        initView();
    }

    private void findView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        iv_iconText = (ImageView) findViewById(R.id.iv_iconText);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        frame_layout_main = (FrameLayout) findViewById(R.id.frame_layout_main);
        View navView = navigationView.getHeaderView(0);
        layout_nav_header = (LinearLayout) navView.findViewById(R.id.layout_nav_header);
        iv_avatar = (ImageView) navView.findViewById(R.id.iv_avatar);
        tv_username = (TextView) navView.findViewById(R.id.tv_username);
        tv_email = (TextView) navView.findViewById(R.id.tv_email);
    }

    private void initView() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (!UserDataHelper.checkTokenExist(this)) {
            CheckLogin.directLogin(this);
        } else {
            initNavHeader();
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        displaySelectedScreen(R.id.nav_home);
    }

    private void initNavHeader() {
        layout_nav_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, ProfileActivity.class);
                startActivity(intent);
            }
        });
        User user = UserDataHelper.getUserInfo(mcontext);
        TextDrawable drawable = TextDrawable.builder()
                .buildRoundRect(String.valueOf(user.getFirst_name().charAt(0)), getResources().getColor(Constant.GETCOLOR()), 1000);
        iv_avatar.setImageDrawable(drawable);
        tv_username.setText(user.getFirst_name() + " " + user.getLast_name());
        tv_email.setText(user.getEmail());
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
            case R.id.nav_settings:
                toolbarNormal(R.string.title_settings);
                fragment = new SettingFragment();
                break;
            case R.id.nav_travelstyle:
                toolbarNormal(R.string.title_travelstyle);
                fragment = new TravelStyleFragment();
                break;
            case R.id.nav_help:
                Intent intent = new Intent(MainActivity.this, NewMainActivity.class);
                startActivity(intent);
        }

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
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        iv_iconText.setVisibility(View.GONE);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) frame_layout_main.getLayoutParams();
        params.setMargins(0, getActionBarSize(), 0, 0);
        frame_layout_main.setLayoutParams(params);
    }

    private void toolbaHome() {
        getSupportActionBar().setTitle("");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.colorPrimary))));
        iv_iconText.setVisibility(View.VISIBLE);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) frame_layout_main.getLayoutParams();
        params.setMargins(0, 0, 0, 0);
        frame_layout_main.setLayoutParams(params);
    }

    public int getActionBarSize() {
        TypedArray styledAttributes = getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        int actionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return actionBarSize;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }
}
