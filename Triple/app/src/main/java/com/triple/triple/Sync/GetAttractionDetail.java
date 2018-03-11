package com.triple.triple.Sync;

import android.util.Log;

import com.google.gson.Gson;
import com.triple.triple.Interface.ApiInterface;
import com.triple.triple.Model.Attraction;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kevin on 2018/3/6.
 */

public class GetAttractionDetail {

    private final OkHttpClient client = new OkHttpClient();


//    public String run(String url) throws Exception {
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            Log.i("onSuccess", response.body().string());
//            return response.body().string();
//        }
//    }


    String responseString = "";
    Attraction att = new Attraction();

    public String run(String url, Integer attractionId) throws Exception {
        Retrofit.Builder builder =new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        ApiInterface client = retrofit.create(ApiInterface.class);
        Call<Attraction> call = client.getInfo(attractionId);

        call.enqueue(new Callback<Attraction>() {
            @Override
            public void onResponse(Call<Attraction> call, retrofit2.Response<Attraction> response) {
                if (response.body() != null){
                    att = response.body();
                    responseString = new Gson().toJson(response.body());
                    Log.i("onSuccess", responseString);
                }else{
                    Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Attraction> call, Throwable throwable) {

            }
        });
        return responseString;
    }
}
