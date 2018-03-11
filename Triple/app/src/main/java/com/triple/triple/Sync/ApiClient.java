package com.triple.triple.Sync;

import com.triple.triple.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kevin on 2018/3/12.
 */

public class ApiClient
{
    public static final String BASE_URL = "http://tripleapi-env.ap-southeast-1.elasticbeanstalk.com";

    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
