package com.triple.triple.Sync;

import android.content.Context;

import com.triple.triple.Helper.UserDataHelper;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Kevin on 2018/1/29.
 */

public class GetPreference {
    private final OkHttpClient client = new OkHttpClient();

    public String run(String url, Context mcontext) throws Exception {
        String token = "Bearer ";
        token += UserDataHelper.getToken(mcontext);
        Request request = new Request.Builder()
                .header("Authorization", token)
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        }
    }
}
