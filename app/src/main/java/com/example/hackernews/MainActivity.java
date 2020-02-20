package com.example.hackernews;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements StoryAdapter.OnStoryListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<DataResponse> dataResponses = new ArrayList<>();
    boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    int totalList;
    Integer j;
    Call<List<Integer>> idlist;
    HackerNewsApi hackerNewsApi;
    Call<DataResponse> story;
    ProgressBar progressBar;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressBar = findViewById(R.id.progress);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hacker-news.firebaseio.com/v0/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        hackerNewsApi = retrofit.create(HackerNewsApi.class);

        idlist = hackerNewsApi.getPosts();

        idlist.enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
//                Log.e("onSubscribe", "YOUR DATA IS HERE: " + response.body());
                totalList = response.body().size();
                for (Integer i = 0; i < 14; i++) {
                    j = i;
                    story = hackerNewsApi.getStory(response.body().get(i));
                    story.enqueue(new Callback<DataResponse>() {
                        @Override
                        public void onResponse(Call<DataResponse> call, Response<DataResponse> st) {
                            if (st.isSuccessful()) {
                                dataResponses.add(st.body());
                                mAdapter.notifyDataSetChanged();
                            }

                        }

                        @Override
                        public void onFailure(Call<DataResponse> call, Throwable t) {
                            t.getMessage();
                        }
                    });

                }


                Log.e("onSubscribe", "YOUR DATA IS HERE: " + dataResponses);


            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {
                t.getMessage();
            }
        });

        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new StoryAdapter(dataResponses,this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = mLayoutManager.getChildCount();
                totalItems = mLayoutManager.getItemCount();
                Log.e("onSubscribe", "TOTAL " + totalItems);
                scrollOutItems = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    isScrolling = false;
                    getMoreStories();
                }
            }
        });



    }

    private void getMoreStories() {

        idlist.clone().enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                Log.e("onSubscribe", "TEST: " + totalList);
                if (totalList > j) {
                    for (Integer i = totalItems; i < 12+totalItems; i++) {
                        progressBar.setVisibility(View.VISIBLE);
                        Log.e("onSubscribe", "I ESTE" + j);
//                        j = i;
                        story = hackerNewsApi.getStory(response.body().get(i));
                        story.enqueue(new Callback<DataResponse>() {
                            @Override
                            public void onResponse(Call<DataResponse> call, Response<DataResponse> st) {
                                if (st.isSuccessful()) {
                                    dataResponses.add(st.body());
                                    mAdapter.notifyDataSetChanged();
                                    progressBar.setVisibility(View.GONE);
                                }

                            }

                            @Override
                            public void onFailure(Call<DataResponse> call, Throwable t) {
                                t.getMessage();
                            }
                        });

                    }
                }


                //Log.e("onSubscribe", "YOUR DATA IS HERE: " + dataResponses);


            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {
                t.getMessage();
            }
        });
    }


    @Override
    public void onStoryClick(int position) {
        Intent intent = new Intent(this,StoryActivity.class);
        intent.putExtra("STORY",dataResponses.get(position));
        startActivity(intent);
    }
}

