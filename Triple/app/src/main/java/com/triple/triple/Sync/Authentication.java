package com.triple.triple.Sync;

import android.util.Log;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Kevin on 2018/1/26.
 */

public class Authentication {
    private final OkHttpClient client = new OkHttpClient();

    public String run(String url, String username, String password) throws Exception {
        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                return "Error";
            } else {
                return response.body().string();
            }
        }

    }
}
