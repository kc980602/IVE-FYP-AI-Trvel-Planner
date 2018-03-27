package com.triple.triple.Interface;

import com.triple.triple.Model.Article;
import com.triple.triple.Model.Attraction;
import com.triple.triple.Model.AuthData;
import com.triple.triple.Model.DataMeta;
import com.triple.triple.Model.ResponeMessage;
import com.triple.triple.Model.SystemProperty;
import com.triple.triple.Model.Trip;
import com.triple.triple.Model.TripDetail;

import org.w3c.dom.Attr;

import java.util.List;

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

public interface ApiInterface {

    @GET("system/property")
    Call<SystemProperty> getProperty();

    @FormUrlEncoded
    @POST("member/register")
    Call<Void> register(
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

    @FormUrlEncoded
    @POST("member/authentication")
    Call<AuthData> authenticate(
            @Field("username") String username,
            @Field("password") String password
    );

    @POST("member/password/forget")
    Call<ResponeMessage> forgetPassword(
            @Field("username") String username
    );

    @GET("/city/{id}/attractions")
    Call<DataMeta> getAttractions(
            @Path("id") Integer id,
            @Query("limit") Integer limit
    );

    @GET("attraction/{id}")
    Call<Attraction> getInfo(
            @Path("id") Integer id
    );

    @POST("attraction/{id}/bookmark")
    Call<Void> setBookmark(
            @Header("Authorization") String authHeader,
            @Path("id") Integer id
    );

    @GET("city/{id}/attraction/bookmarks")
    Call<List<Attraction>> getBookmark(
            @Header("Authorization") String authHeader,
            @Path("id") Integer id
    );

    @GET("trip")
    Call<List<Trip>> listTrip(
            @Header("Authorization") String authHeader
    );

    @GET("trip/{id}")
    Call<TripDetail> listTripByUser(
            @Header("Authorization") String authHeader,
            @Path("id") Integer id
    );

    @GET("trip/{id}/article")
    Call<List<Article>> getTripArticle(
            @Header("Authorization") String authHeader,
            @Path("id") Integer id
    );

    @FormUrlEncoded
    @POST("trip")
    Call<TripDetail> createTrip(
            @Header("Authorization") String authHeader,
            @Field("title") String tripname,
            @Field("visit_date") String tripdateStart,
            @Field("visit_length") String dateCount,
            @Field("city_id") String destination,
            @Field("auto_generate") String generate
    );
}
