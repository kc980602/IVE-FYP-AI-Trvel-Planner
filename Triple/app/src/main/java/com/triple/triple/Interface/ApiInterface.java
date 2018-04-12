package com.triple.triple.Interface;

import com.triple.triple.Model.Article;
import com.triple.triple.Model.Attraction;
import com.triple.triple.Model.AuthData;
import com.triple.triple.Model.DataMeta;
import com.triple.triple.Model.KeyValue;
import com.triple.triple.Model.ResponeMessage;
import com.triple.triple.Model.SystemProperty;
import com.triple.triple.Model.Trip;
import com.triple.triple.Model.TripDetail;
import com.triple.triple.Model.User;

import org.w3c.dom.Attr;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    Call<AuthData> register(
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
    @PUT("member/info")
    Call<Void> editInfo(
            @Field("first_name") String fname,
            @Field("last_name") String lname,
            @Field("gender") String gender,
            @Field("age") String age
    );

    @GET("member/info")
    Call<User> getInfo(
            @Header("Authorization") String authHeader
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

    @GET("/city/{id}/attractions/preference")
    Call<DataMeta> getAttractionByPreference(
            @Header("Authorization") String authHeader,
            @Path("id") Integer id
    );

    @GET("/city/{id}/attractions/attractions")
    Call<DataMeta> getCityAttractions(
            @Path("id") Integer id,
            @Query("page") Integer page,
            @Query("limit") Integer limit
    );

    @GET("/city/{id}/attractions/hotels")
    Call<DataMeta> getCityHotels(
            @Path("id") Integer id,
            @Query("page") Integer page,
            @Query("limit") Integer limit
    );

    @GET("/city/{id}/attractions/restaurants")
    Call<DataMeta> getCityRestaurants(
            @Path("id") Integer id,
            @Query("page") Integer page,
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

    @FormUrlEncoded
    @POST("attraction/{id}/review")
    Call<Void> placeReview(
            @Header("Authorization") String authHeader,
            @Path("id") Integer id,
            @Field("title") String title,
            @Field("message") String message,
            @Field("rating") Integer rating
    );

    @GET("trip")
    Call<List<Trip>> listTrip(
            @Header("Authorization") String authHeader
    );

    @GET("trip/ended")
    Call<List<Trip>> listTripEnded(
            @Header("Authorization") String authHeader
    );

    @GET("trip/{id}")
    Call<TripDetail> listTripByUser(
            @Header("Authorization") String authHeader,
            @Path("id") Integer id
    );

    @DELETE("trip/{id}")
    Call<TripDetail> removeTrip(
            @Header("Authorization") String authHeader,
            @Path("id") Integer id
    );

    @GET("trip/{id}/article")
    Call<List<Article>> getTripArticle(
            @Header("Authorization") String authHeader,
            @Path("id") Integer id
    );

    @GET("member/preference")
    Call<List<KeyValue>> getPreferences(
            @Header("Authorization") String authHeader
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
