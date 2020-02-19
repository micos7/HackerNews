package com.example.hackernews;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HackerNewsApi {


    @GET("topstories.json?print=pretty")
    Call<List<Integer>> getPosts();

    @GET("item/{id}.json?print=pretty")
    Call<DataResponse> getStory(@Path("id") Integer id);
}
