package com.triple.triple.Sync;

/**
 * Created by Kevin on 2018/1/24.
 */

import android.util.Log;

import java.io.IOException;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public final class SynchronousGet {
    private final OkHttpClient client = new OkHttpClient();

    public String run() throws Exception {
        Request request = new Request.Builder()
                .url("http://www.json-generator.com/api/json/get/bTXWAGkHIi?indent=2")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }
            return response.body().string();
        }
    }

}