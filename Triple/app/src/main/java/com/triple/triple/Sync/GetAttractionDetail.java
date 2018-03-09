package com.triple.triple.Sync;

import android.content.Context;

import com.triple.triple.Interface.GitHubService;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by Kevin on 2018/3/6.
 */

public class GetAttractionDetail {

    private final OkHttpClient client = new OkHttpClient();

    String responseString;

    public String run(String url) throws Exception {
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            return response.body().string();
//        }
        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl(url)
                .build();
        GitHubService client = retrofit.create(GitHubService.class);
        Call<String> call = client.getInfo(1);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                responseString = response.body();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

        return "responseString";
    }
}
