package com.example.hackernews;

import org.xml.sax.helpers.DefaultHandler;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HackerNewsApi {


    @GET("topstories.json?print=pretty")
    Call<List<Integer>> getPosts();

    @GET("item/{id}.json?print=pretty")
    Call<DataResponse> getStory(@Path("id") Integer id);

    @FormUrlEncoded
    @Headers({"Content-Type: application/x-www-form-urlencoded", "Access-Control-Allow-Origin: *"})
    @POST("login?goto=news?p=building_android_client_dont_ban_me")
    Call<DefaultHandler> login(@Field("acct") String user, @Field("pw") String password);

    @GET("user/{id}.json?print=pretty")
    Call<ProfileResponse> getProfile(@Path("id") String id);

}
