package com.triple.triple.Presenter.Account;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.triple.triple.Helper.CheckLogin;
import com.triple.triple.Helper.Constant;
import com.triple.triple.Helper.UserDataHelper;
import com.triple.triple.Model.User;
import com.triple.triple.R;

public class ProfileActivity extends AppCompatActivity {

    private Context mcontext = ProfileActivity.this;
    private Toolbar toolbar;
    private ImageView iv_avatar;
    private TextView tv_email;
    private TextView tv_username;
    private TextView tv_fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (!UserDataHelper.checkTokenExist(this)) {
            CheckLogin.directLogin(this);
        } else {
            findView();
            initView();
        }
    }

    private void findView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        iv_avatar = (ImageView) findViewById(R.id.iv_avatar);
        tv_fullname = (TextView) findViewById(R.id.tv_fullname);
        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_email = (TextView) findViewById(R.id.tv_email);
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

}
