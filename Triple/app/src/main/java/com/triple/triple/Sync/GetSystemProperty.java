package com.triple.triple.Sync;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Kevin on 2018/3/7.
 */

public class GetSystemProperty {

    private final OkHttpClient client = new OkHttpClient();

    public String run(String url) throws Exception {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
