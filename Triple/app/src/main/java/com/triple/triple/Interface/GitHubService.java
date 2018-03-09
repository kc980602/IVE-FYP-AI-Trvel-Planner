package com.triple.triple.Interface;

import com.triple.triple.Sync.GetAttractionDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by HaYYY on 2018/3/9.
 */

public interface GitHubService {
    @GET("/attraction/{id}")
    Call<String> getInfo(@Path("id") Integer id);
}
