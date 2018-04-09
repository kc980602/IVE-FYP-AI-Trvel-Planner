package com.triple.triple.Presenter.Account;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.triple.triple.Helper.CheckLogin;
import com.triple.triple.Helper.Constant;
import com.triple.triple.Helper.UserDataHelper;
import com.triple.triple.Model.KeyValue;
import com.triple.triple.Model.User;
import com.triple.triple.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private Context mcontext = ProfileActivity.this;
    private Toolbar toolbar;
    private ImageView iv_avatar;
    private TextView tv_email;
    private TextView tv_username;
    private TextView tv_fullname;
    private AVLoadingIndicatorView avi;
    private ImageView[] imageViewsList = new ImageView[4];
    private TextView[] textViewsList = new TextView[4];
    private List<KeyValue> keyValueList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (!UserDataHelper.checkTokenExist(this)) {
            CheckLogin.directLogin(this);
        } else {
            findView();
            getPreferences();
            initView();
        }
    }

    private void findView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        iv_avatar = (ImageView) findViewById(R.id.iv_avatar);
        tv_fullname = (TextView) findViewById(R.id.tv_fullname);
        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_email = (TextView) findViewById(R.id.tv_email);

        int[] imageViewIdList = {R.id.iv_1, R.id.iv_2, R.id.iv_3, R.id.iv_4};
        int[] textViewIdList = {R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4};
        for (int i = 0; i <= 3; i++) {
            imageViewsList[i] = (ImageView) findViewById(imageViewIdList[i]);
            textViewsList[i] = (TextView) findViewById(textViewIdList[i]);
        }
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        String indicator = getIntent().getStringExtra("indicator");
        avi.setIndicator(indicator);
    }


    private void initView() {
        toolbar.setTitle(R.string.title_profile);
        setSupportActionBar(toolbar);

        final User user = UserDataHelper.getUserInfo(mcontext);
        TextDrawable drawable = TextDrawable.builder()
                .buildRoundRect(String.valueOf(user.getFirst_name().charAt(0)), getResources().getColor(Constant.GETCOLOR()), 10);
        iv_avatar.setImageDrawable(drawable);
        tv_fullname.setText(user.getFirst_name() + " " + user.getLast_name());
        tv_fullname.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Drawable img = getResources().getDrawable(user.getGender().equals("M") ? R.drawable.ic_male : R.drawable.ic_female);
                        img.setTint(getResources().getColor(user.getGender().equals("M") ? R.color.m300_blue : R.color.m300_pink));
                        img.setBounds(0, 0, img.getIntrinsicWidth() * tv_fullname.getMeasuredHeight() / img.getIntrinsicHeight(), tv_fullname.getMeasuredHeight());
                        tv_fullname.setCompoundDrawables(null, null, img, null);
                        tv_fullname.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
        tv_username.setText(user.getUsername());
        tv_email.setText(user.getEmail());
    }

    public void getPreferences() {
        startAnim();
        String token = "Bearer ";
        token += UserDataHelper.getToken(mcontext);
        Call<List<KeyValue>> call = Constant.apiService.getPreferences(token);
        call.enqueue(new Callback<List<KeyValue>>() {
            @Override
            public void onResponse(Call<List<KeyValue>> call, Response<List<KeyValue>> response) {
                if (response.body() != null) {
                    keyValueList = response.body();
                    afterGetDate();
                } else {
                    requestFail();
                    Log.e("onResponse", "Null");
                }
            }
            @Override
            public void onFailure(Call<List<KeyValue>> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
                requestFail();
            }
        });
        stopAnim();
    }

    private void afterGetDate() {
        for (int i = 0; i <= 3; i++) {
            String filename = "preference_" + keyValueList.get(i).getKey();
            if (keyValueList.get(i).getKey().equals("60+_traveller")) {
                filename = "preference_60_traveller";
            }
            Log.e("filename", filename);
            imageViewsList[i].setImageResource(mcontext.getResources().getIdentifier(filename, "drawable", getPackageName()));
            String key = keyValueList.get(i).getKey().replace('_', ' ');
            String titile = key.substring(0,1).toUpperCase() + key.substring(1).toLowerCase();
            textViewsList[i].setText(titile);
        }
    }


    private void requestFail() {
        View view = getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(view, getString(R.string.mytrips_error), Snackbar.LENGTH_LONG).setAction(getString(R.string.snackbar_refersh), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPreferences();
            }
        }).show();
    }

    public void startAnim() {
        avi.show();
    }

    public void stopAnim() {
        avi.hide();
    }


}
