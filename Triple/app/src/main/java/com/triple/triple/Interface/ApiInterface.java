package com.triple.triple.Interface;

import com.triple.triple.Model.Attraction;
import com.triple.triple.Sync.GetAttractionDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by HaYYY on 2018/3/9.
 */

public interface ApiInterface {

    @GET("attraction")
    Call<Attraction> getRows();

    @GET("attraction/{id}")
    Call<Attraction> getInfo(@Path("id") Integer id);

}
