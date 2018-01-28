package com.triple.triple.Sync;

import android.content.Context;
import android.util.Log;

import com.triple.triple.helper.GetToken;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Kevin on 2018/1/25.
 */

public class CreateTrip {
    private final OkHttpClient client = new OkHttpClient();

    public String run(String url, Context mcontext, String tripname, String tripdateStart, String dateCount, String destination, String generate) throws Exception {
        String token = "Bearer ";
        token += GetToken.getToken(mcontext);
        RequestBody formBody = new FormBody.Builder()
                .add("title", tripname)
                .add("visit_date", tripdateStart)
                .add("visit_length", dateCount)
                .build();
        Request request = new Request.Builder()
                .header("Authorization", token)
                .url(url)
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return String.valueOf(response.code());
        }

    }
}