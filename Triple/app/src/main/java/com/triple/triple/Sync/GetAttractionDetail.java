package com.triple.triple.Sync;

import android.content.Context;
import android.util.Log;

import com.triple.triple.Interface.GitHubService;
import com.triple.triple.Model.Attraction;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kevin on 2018/3/6.
 */

public class GetAttractionDetail {

    private final OkHttpClient client = new OkHttpClient();

    String responseString;
    String url2 = "http://tripleapi-env.ap-southeast-1.elasticbeanstalk.com/";

    public String run(String url) throws Exception {
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            return response.body().string();
//        }
        Retrofit.Builder builder =new Retrofit.Builder()
                .baseUrl(url2)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        GitHubService client = retrofit.create(GitHubService.class);
        Call<Attraction> call = client.getInfo(1642);

        call.enqueue(new Callback<Attraction>() {
            @Override
            public void onResponse(Call<Attraction> call, retrofit2.Response<Attraction> response) {
                Attraction attraction = response.body();
                if (response.body() != null){
                    Log.i("onSuccess", response.body().toString());
                    responseString = response.body().toString();
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
