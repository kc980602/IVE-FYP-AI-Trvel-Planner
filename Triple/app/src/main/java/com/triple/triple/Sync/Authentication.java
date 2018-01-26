package com.triple.triple.Sync;

import com.triple.triple.R;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Kevin on 2018/1/26.
 */

public class Authentication {
    private final OkHttpClient client = new OkHttpClient();

    public String run(String url) throws Exception {
        RequestBody formBody = new FormBody.Builder()
                .add("username", "testing0")
                .add("password", "123456789")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        }

    }
}
