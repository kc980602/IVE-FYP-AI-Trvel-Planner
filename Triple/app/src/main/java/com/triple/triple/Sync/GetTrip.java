package com.triple.triple.Sync;

/**
 * Created by Kevin on 2018/1/24.
 */

import android.content.Context;
import android.util.Log;

import com.triple.triple.R;
import com.triple.triple.helper.GetToken;

import java.io.IOException;
import java.net.URL;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public final class GetTrip {
    private final OkHttpClient client = new OkHttpClient();

    public String run(String url, Context mcontext) throws Exception {
        String token = "Bearer ";
        token += GetToken.getToken(mcontext);
        Request request = new Request.Builder()
                .header("Authorization", token)
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String re = response.body().string();
            response.body().close();
            return re;
        }
    }
}