package com.triple.triple.Presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.triple.triple.Helper.GetToken;
import com.triple.triple.Presenter.Account.LoginActivity;
import com.triple.triple.Presenter.Mytrips.MytripsActivity;
import com.triple.triple.Presenter.Profile.ProfileActivity;
import com.triple.triple.Presenter.Profile.TravelStyleActivity;
import com.triple.triple.Presenter.Search.SearchActivity;
import com.triple.triple.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private FloatingActionButton fab;
    private FrameLayout mFrame;
    private RelativeLayout relative_main;
    private ImageView img_page_start;

    private Context mcontext = MainActivity.this;
    private static boolean isShowPageStart = true;
    private final int MESSAGE_SHOW_LOGIN = 0x001;
    private final int MESSAGE_SHOW_START_PAGE = 0x002;

    public Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_SHOW_LOGIN:
                    Intent intent = new Intent();
                    intent.setClass(mcontext, LoginActivity.class);
                    startActivity(intent);
                    break;
                case MESSAGE_SHOW_START_PAGE:
                    AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                    alphaAnimation.setDuration(300);
                    alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            relative_main.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    relative_main.startAnimation(alphaAnimation);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initViewPager();

        if (isShowPageStart) {
            relative_main.setVisibility(View.VISIBLE);
            mHandler.sendEmptyMessageDelayed(MESSAGE_SHOW_START_PAGE, 2000);
//            if (token.equals(DEFAULT)) {
//                mHandler.sendEmptyMessageDelayed(MESSAGE_SHOW_START_PAGE, 2000);
//            } else {
//                mHandler.sendEmptyMessageDelayed(MESSAGE_SHOW_START_PAGE, 1000);
//            }
            isShowPageStart = false;
        }

        if (GetToken.getToken(mcontext).equals(GetToken.DEFAULT)) {
            mHandler.sendEmptyMessageDelayed(MESSAGE_SHOW_LOGIN, 2000);
        }
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        relative_main = findViewById(R.id.relative_main);
        img_page_start = findViewById(R.id.img_page_start);
    }

    private void initViewPager() {
//        Fragment fragment = new HomeFragment();
//            Fragment newFragment = new DebugExampleTwoFragment();
//            FragmentTransaction ft = getFragmentManager().beginTransaction();
//            ft.add(R.id.frame_layout_main, fragment).commit();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent = new Intent();
        int id = item.getItemId();
        if (id == R.id.nav_mytrips) {
            intent.setClass(this, MytripsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_search) {
            intent.setClass(this, SearchActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_travelstyle) {
            intent.setClass(this, TravelStyleActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_settings) {
            intent.setClass(this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
