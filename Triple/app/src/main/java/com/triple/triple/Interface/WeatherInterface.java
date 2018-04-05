package com.triple.triple.Interface;

import com.triple.triple.Model.Article;
import com.triple.triple.Model.Attraction;
import com.triple.triple.Model.AuthData;
import com.triple.triple.Model.DataMeta;
import com.triple.triple.Model.ResponeMessage;
import com.triple.triple.Model.SystemProperty;
import com.triple.triple.Model.Trip;
import com.triple.triple.Model.TripDetail;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by HaYYY on 2018/3/9.
 */

public interface WeatherInterface {

    @GET("weather")
    Call<ResponseBody> getWeather(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("appid") String appid
            );


}
