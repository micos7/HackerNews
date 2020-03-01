package com.example.hackernews;

import org.xml.sax.helpers.DefaultHandler;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HackerNewsApi {


    @GET("topstories.json?print=pretty")
    Call<List<Integer>> getPosts();

    @GET("item/{id}.json?print=pretty")
    Call<DataResponse> getStory(@Path("id") Integer id);

    @POST("login?acct={user}&pw={password}")
    Call<DefaultHandler> login(@Query("user") String user, @Query("password") String password);

}
