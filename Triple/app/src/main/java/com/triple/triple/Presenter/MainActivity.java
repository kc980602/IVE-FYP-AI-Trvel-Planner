package com.triple.triple.Presenter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import com.triple.triple.Helper.CheckLogin;
import com.triple.triple.Helper.DrawerUtil;
import com.triple.triple.Helper.SystemPropertyHelper;
import com.triple.triple.Helper.Token;
import com.triple.triple.Model.SystemProperty;
import com.triple.triple.Presenter.Home.HomeFragment;
import com.triple.triple.Presenter.Mytrips.MytripsActivity;
import com.triple.triple.Presenter.Profile.ProfileActivity;
import com.triple.triple.Presenter.Profile.TravelStyleActivity;
import com.triple.triple.Presenter.Search.SearchActivity;
import com.triple.triple.R;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
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
                    CheckLogin.directLogin(mcontext);
                    break;
                case MESSAGE_SHOW_START_PAGE:
                    AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                    alphaAnimation.setDuration(100);
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
        new MainActivity.RequestData().execute();
        initView();
        initViewPager();

        if (isShowPageStart) {
            relative_main.setVisibility(View.VISIBLE);
            mHandler.sendEmptyMessageDelayed(MESSAGE_SHOW_START_PAGE, 100);
            isShowPageStart = false;
        }

        if (!Token.checkTokenExist(mcontext)) {
            mHandler.sendEmptyMessageDelayed(MESSAGE_SHOW_LOGIN, 0);
        }
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        DrawerUtil.getDrawer(this, toolbar);

        relative_main = findViewById(R.id.relative_main);
        img_page_start = findViewById(R.id.img_page_start);
    }

    private void initViewPager() {
        Fragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("cities", (Serializable) SystemPropertyHelper.getSystemProperty(mcontext).getCity());
        fragment.setArguments(bundle);
        if (fragment != null) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_layout_main, fragment).commit();
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

    private class RequestData extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String respone = "Error";
            try {
                SystemPropertyHelper.refreshData(mcontext);
                respone = "Success";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return respone;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }
    }
}
