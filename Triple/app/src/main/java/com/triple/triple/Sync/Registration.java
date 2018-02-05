package com.triple.triple.Sync;

import android.content.Context;
import android.util.Log;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Kevin on 2018/1/28.
 */

public class Registration {
    private final OkHttpClient client = new OkHttpClient();

    public String run(String url, String username, String password, String cPassword, String email, String age, String gender, String country) throws Exception {
        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .add("password_confirmation", cPassword)
                .add("first_name", "N/A")
                .add("last_name", "N/A")
                .add("gender", gender)
                .add("age", age)
                .add("email", email)
                .add("income", "0")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            Log.d("abc", response.message() + "end" + response.isSuccessful());
            return response.body().string();
        }

    }
}
