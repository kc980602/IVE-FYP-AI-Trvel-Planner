package com.triple.triple.Interface;

import com.triple.triple.Model.Attraction;
import com.triple.triple.Model.AuthData;
import com.triple.triple.Model.ResponeMessage;
import com.triple.triple.Model.Trip;
import com.triple.triple.Model.TripDetail;
import com.triple.triple.Model.TripItinerary;
import com.triple.triple.Sync.GetAttractionDetail;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by HaYYY on 2018/3/9.
 */

public interface ApiInterface {

    @GET("system/property")
    Call<String> getProperty();

    @POST("member/register")
    Call<List<ResponeMessage>> register(
            @Field("username") String username,
            @Field("first_name") String fname,
            @Field("last_name") String lname,
            @Field("password") String password,
            @Field("password_confirmation") String cPassword,
            @Field("gender") String gender,
            @Field("age") String age,
            @Field("email") String email,
            @Field("income") String income
    );

    @POST("member/authentication")
    Call<List<AuthData>> authenticate(
            @Field("username") String username,
            @Field("password") String password
    );

    @POST("member/password/forget")
    Call<List<ResponeMessage>> forgetPassword(
            @Field("username") String username
    );

    @GET("attraction")
    Call<Attraction> getRows();

    @GET("attraction/{id}")
    Call<Attraction> getInfo(
            @Path("id") Integer id
    );

    @GET("trip/bookmarks")
    Call<List<Trip>> getBookmark();

    @GET("trip")
    Call<List<Trip>> listTrip(
            @Header("Authorization") String authHeader
    );

    @GET("trip/{id}")
    Call<TripDetail> listTripByUser(
            @Header("Authorization") String authHeader,
            @Path("id") Integer id
    );

    @POST("trip")
    Call<String> createTrip(
            @Header("Authorization") String authHeader,
            @Field("title") String tripname,
            @Field("visit_date") String tripdateStart,
            @Field("visit_length") String dateCount,
            @Field("city_id") String destination,
            @Field("auto_generate") String generate
    );
}
