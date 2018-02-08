package com.triple.triple.Sync;

import android.content.Context;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Kevin on 2018/2/6.
 */

public class ForgetPassword {
    private final OkHttpClient client = new OkHttpClient();

    public String run(String url, String username) throws Exception {

        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }

    }
}
