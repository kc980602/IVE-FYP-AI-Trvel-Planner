package com.triple.triple.Presenter.Attraction;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.triple.triple.Helper.UserDataHelper;
import com.triple.triple.Interface.ApiInterface;
import com.triple.triple.Model.Attraction;
import com.triple.triple.R;
import com.triple.triple.Sync.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HaYYY on 2018/4/8.
 */

public class AttractionReviewActivity extends AppCompatActivity {

    private Context mcontext = AttractionReviewActivity.this;
    private Attraction attraction;
    private TextInputEditText et_title, et_rating, et_content;
    private Button btn_review_submit;
    private String title, content;
    private int rating;
    private ProgressDialog progressDialog;
    private Toolbar toolbar;
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_review);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        attraction = (Attraction) bundle.getSerializable("attraction");
        progressDialog = new ProgressDialog(mcontext);
        progressDialog.setMessage(getString(R.string.dialog_progress_title));
        findViews();
        initView();
        toolbar.setTitle(R.string.attraction_comment_title);
        setSupportActionBar(toolbar);
        //setupActionBar();
    }

//    private void setupActionBar() {
//        android.support.v7.app.ActionBar ab = getSupportActionBar();
//        ab.setTitle(R.string.attraction_comment_title);
//    }

    private void findViews(){
        et_title = (TextInputEditText) findViewById(R.id.et_title);
        et_rating = (TextInputEditText) findViewById(R.id.et_rating);
        et_content = (TextInputEditText) findViewById(R.id.et_content);
        btn_review_submit = (Button) findViewById(R.id.btn_review_submit);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        et_rating.setOnClickListener(et_ratingListener);
    }

    private void initView(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
    }

    View.OnClickListener et_ratingListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final String[] singleChoiceItems = getResources().getStringArray(R.array.rating);
            int itemSelected = 0;
            new AlertDialog.Builder(mcontext)
                    .setTitle(getString(R.string.comment_rating_guide))
                    .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            et_rating.setText(singleChoiceItems[i].toString());
                        }
                    })
                    .setNegativeButton(getString(R.string.dialog_cancel), null)
                    .show();
        }
    };

    public void onSubmitClick(View view){
        Boolean isSuccess = false;
        if (TextUtils.isEmpty(et_title.getText())) {
            et_title.setError(getResources().getString(R.string.register_error_required));
        } else if (et_rating.getText().toString().equals("")) {
            et_rating.setError(getResources().getString(R.string.register_error_required));
        } else if (TextUtils.isEmpty(et_content.getText())) {
            et_content.setError(getResources().getString(R.string.register_error_required));
        } else {
            isSuccess = true;
        }

        if (isSuccess) {
            title = et_title.getText().toString();
            rating = Integer.parseInt(et_rating.getText().toString());
            content = et_content.getText().toString();
            placeReview();
        }
    }

    public void placeReview() {
        progressDialog.show();
        String token = "Bearer ";
        token += UserDataHelper.getToken(mcontext);
        Call<Void> call = apiService.placeReview(token, attraction.getId(), title, content, rating);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                progressDialog.dismiss();
                finish();
                Log.d("ReturnResponse", String.valueOf(response.code()));
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("ReturnResponse", t.getMessage());
                View view = getWindow().getDecorView().findViewById(android.R.id.content);
                Snackbar.make(view, getString(R.string.mytrips_create_error_process), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.snackbar_ok), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();
                progressDialog.dismiss();
            }
        });
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

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
