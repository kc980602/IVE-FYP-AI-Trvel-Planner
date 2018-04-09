package com.triple.triple.Presenter.Profile;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.triple.triple.Adapter.TripAdapter;
import com.triple.triple.Helper.Constant;
import com.triple.triple.Helper.UserDataHelper;
import com.triple.triple.Interface.ApiInterface;
import com.triple.triple.Model.KeyValue;
import com.triple.triple.Model.SystemProperty;
import com.triple.triple.Model.Trip;
import com.triple.triple.Presenter.Account.LoginActivity;
import com.triple.triple.R;
import com.triple.triple.Sync.ApiClient;
import com.wang.avi.AVLoadingIndicatorView;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TravelStyleFragment extends Fragment {

    private AVLoadingIndicatorView avi;
    private ImageView[] imageViewsList = new ImageView[5];
    private TextView[] textViewsList = new TextView[5];
    private ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travel_style, container, false);
        setHasOptionsMenu(true);
        int[] imageViewIdList = {R.id.iv_1, R.id.iv_2, R.id.iv_3, R.id.iv_4};
        int[] textViewIdList = {R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4};
        for (int i = 0; i <= 3; i++) {
            imageViewsList[i] = (ImageView) view.findViewById(imageViewIdList[i]);
            textViewsList[i] = (TextView) view.findViewById(textViewIdList[i]);
        }

        avi = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
        String indicator = getActivity().getIntent().getStringExtra("indicator");
        avi.setIndicator(indicator);
        if (UserDataHelper.checkTokenExist(getContext())) {
            getPreferences();
        } else {
            view = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
            Snackbar.make(view, "Login or Register First", Snackbar.LENGTH_LONG).setAction(getString(R.string.snackbar_refersh), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            }).show();
        }

        return view;
    }

    public void getPreferences(){
        startAnim();
        String token = "Bearer ";
        token += UserDataHelper.getToken(getContext());
        Call<List<KeyValue>> call = Constant.apiService.getPreferences(token);
        call.enqueue(new Callback<List<KeyValue>>() {
            @Override
            public void onResponse(Call<List<KeyValue>> call, Response<List<KeyValue>> response) {
                if (response.body() != null) {
                    List<KeyValue> keyValueListSorted = new ArrayList<>();
                    keyValueListSorted = response.body();
                    Log.e("onResponse", keyValueListSorted.get(0).getKey());
                } else {
                    //requestFail();
                    Log.e("onResponse", "Null");
                }
            }
            @Override
            public void onFailure(Call<List<KeyValue>> call, Throwable t) {
                Log.e("onFailure", t.toString());
                //requestFail();
            }
        });
        stopAnim();
    }

    private void requestFail() {
        View view = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_plain, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }


}
