package com.triple.triple.Presenter.HelpInfo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.triple.triple.Helper.Constant;
import com.triple.triple.R;

public class HelpActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mcontext = HelpActivity.this;

    private ImageView image1, image2, image3, image4;
    private CardView cv_os;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        image4 = (ImageView) findViewById(R.id.image4);

        cv_os = (CardView) findViewById(R.id.cv_os);

        TextDrawable drawable = TextDrawable.builder()
                .buildRoundRect("L", getResources().getColor(Constant.GETCOLOR()), 1000);
        image1.setImageDrawable(drawable);
        drawable = TextDrawable.builder()
                .buildRoundRect("C", getResources().getColor(Constant.GETCOLOR()), 1000);
        image2.setImageDrawable(drawable);
        drawable = TextDrawable.builder()
                .buildRoundRect("C", getResources().getColor(Constant.GETCOLOR()), 1000);
        image3.setImageDrawable(drawable);
        drawable = TextDrawable.builder()
                .buildRoundRect("L", getResources().getColor(Constant.GETCOLOR()), 1000);
        image4.setImageDrawable(drawable);

        cv_os.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.cv_os:
                Intent intent = new Intent(mcontext, OpenSourceActivity.class);
                startActivity(intent);
                break;
        }
    }
}
