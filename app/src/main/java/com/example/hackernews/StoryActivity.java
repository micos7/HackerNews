package com.example.hackernews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StoryActivity extends AppCompatActivity {
    private static final String TAG = "StoryActivity";

    private RecyclerView cRecyclerView;
    private RecyclerView.Adapter cAdapter;
    private RecyclerView.LayoutManager cLayoutManager;
    private List<DataResponse> comments = new ArrayList<>();
    private List<DataResponse> commentsLvl1 = new ArrayList<>();
    private List<DataResponse> commentsLvl2 = new ArrayList<>();
    private List<DataResponse> commentsLvl3 = new ArrayList<>();
    private List<DataResponse> commentsLvl4 = new ArrayList<>();
    private List<DataResponse> commentsLvl5 = new ArrayList<>();
    private List<DataResponse> commentsLvl6 = new ArrayList<>();
    private List<DataResponse> commentsLvl7 = new ArrayList<>();
    Call<DataResponse> commentLvl1;
    Call<DataResponse> commentLvl2;
    Call<DataResponse> commentLvl3;
    Call<DataResponse> commentLvl4;
    Call<DataResponse> commentLvl5;
    Call<DataResponse> commentLvl6;
    Call<DataResponse> commentLvl7;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        cRecyclerView = findViewById(R.id.commentRecyclerView);
        cRecyclerView.setHasFixedSize(true);
        cLayoutManager = new LinearLayoutManager(getApplicationContext());
        cRecyclerView.setLayoutManager(cLayoutManager);
        cAdapter = new CommentsAdapter(comments, commentsLvl1, commentsLvl2, commentsLvl3, commentsLvl4, commentsLvl5, commentsLvl6, commentsLvl7);


        if (getIntent().getExtras() != null) {
            Intent intent = getIntent();
            final DataResponse myStory = (DataResponse) intent.
                    getSerializableExtra("STORY");

            List<Integer> commentsIds = myStory.getKids();

            //Log.d(TAG, "IDS: " + commentsIds);

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
            //Log.d(TAG, "COMMENTLST: "+commentsIds);
            HackerNewsApi hackerNewsApi = retrofit.create(HackerNewsApi.class);

            if (commentsIds != null) {
                for (Integer i = 0; i < commentsIds.size(); i++) {
                    Call<DataResponse> comment = hackerNewsApi.getStory(commentsIds.get(i));
                    comment.enqueue(new Callback<DataResponse>() {
                        @Override
                        public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                            //Log.d(TAG, "COMMENT: "+response.body());
                            if (response.isSuccessful()) {
                                comments.add(response.body());
                                cAdapter.notifyDataSetChanged();

                                List<Integer> commentsLvl1ids = response.body().getKids();

                                if (commentsLvl1ids != null) {
                                    for (int j = 0; j < commentsLvl1ids.size(); j++) {

                                        commentLvl1 = hackerNewsApi.getStory(commentsLvl1ids.get(j));
                                        commentLvl1.enqueue(new Callback<DataResponse>() {
                                            @Override
                                            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {

                                                commentsLvl1.add(response.body());
                                                cAdapter.notifyDataSetChanged();
                                                Log.d(TAG, "COMMENTSLEVEL " + response.body().getText());

                                                List<Integer> commentsLvl2ids = response.body().getKids();

                                                if (commentsLvl2ids != null) {
                                                    for (int z = 0; z < commentsLvl2ids.size(); z++) {

                                                        commentLvl2 = hackerNewsApi.getStory(commentsLvl2ids.get(z));

                                                        commentLvl2.enqueue(new Callback<DataResponse>() {
                                                            @Override
                                                            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {

                                                                commentsLvl2.add(response.body());
                                                                cAdapter.notifyDataSetChanged();

                                                                List<Integer> commentsLvl3ids = response.body().getKids();

                                                                if (commentsLvl3ids != null) {

                                                                    for (int x = 0; x < commentsLvl3ids.size(); x++) {

                                                                        commentLvl3 = hackerNewsApi.getStory(commentsLvl3ids.get(x));

                                                                        commentLvl3.enqueue(new Callback<DataResponse>() {
                                                                            @Override
                                                                            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                                                                                commentsLvl3.add(response.body());
                                                                                cAdapter.notifyDataSetChanged();

                                                                                List<Integer> commentsLvl4ids = response.body().getKids();

                                                                                if (commentsLvl4ids != null) {

                                                                                    for (int k = 0; k < commentsLvl4ids.size(); k++) {

                                                                                        commentLvl4 = hackerNewsApi.getStory(commentsLvl4ids.get(k));

                                                                                        commentLvl4.enqueue(new Callback<DataResponse>() {
                                                                                            @Override
                                                                                            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                                                                                                commentsLvl4.add(response.body());
                                                                                                cAdapter.notifyDataSetChanged();

                                                                                                List<Integer> commentsLvl5ids = response.body().getKids();

                                                                                                if (commentsLvl5ids != null) {
                                                                                                    for (int l = 0; l < commentsLvl5ids.size(); l++) {
                                                                                                        commentLvl5 = hackerNewsApi.getStory(commentsLvl5ids.get(l));

                                                                                                        commentLvl5.enqueue(new Callback<DataResponse>() {
                                                                                                            @Override
                                                                                                            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                                                                                                                commentsLvl5.add(response.body());
                                                                                                                cAdapter.notifyDataSetChanged();

                                                                                                                List<Integer> commentsLvl6ids = response.body().getKids();

                                                                                                                if (commentsLvl6ids != null) {

                                                                                                                    for (int p = 0; p < commentsLvl6ids.size(); p++) {
                                                                                                                        commentLvl6 = hackerNewsApi.getStory(commentsLvl6ids.get(p));

                                                                                                                        commentLvl6.enqueue(new Callback<DataResponse>() {
                                                                                                                            @Override
                                                                                                                            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                                                                                                                                commentsLvl6.add(response.body());
                                                                                                                                cAdapter.notifyDataSetChanged();

                                                                                                                                List<Integer> commentsLvl7ids = response.body().getKids();
                                                                                                                                if (commentsLvl7ids != null) {

                                                                                                                                    for (int o = 0; o < commentsLvl7ids.size(); o++) {
                                                                                                                                        commentLvl7 = hackerNewsApi.getStory(commentsLvl7ids.get(o));

                                                                                                                                        commentLvl7.enqueue(new Callback<DataResponse>() {
                                                                                                                                            @Override
                                                                                                                                            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                                                                                                                                                commentsLvl7.add(response.body());
                                                                                                                                                cAdapter.notifyDataSetChanged();
                                                                                                                                            }

                                                                                                                                            @Override
                                                                                                                                            public void onFailure(Call<DataResponse> call, Throwable t) {

                                                                                                                                            }
                                                                                                                                        });
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }

                                                                                                                            @Override
                                                                                                                            public void onFailure(Call<DataResponse> call, Throwable t) {

                                                                                                                            }
                                                                                                                        });

                                                                                                                    }
                                                                                                                }
                                                                                                            }

                                                                                                            @Override
                                                                                                            public void onFailure(Call<DataResponse> call, Throwable t) {

                                                                                                            }
                                                                                                        });
                                                                                                    }
                                                                                                }


                                                                                            }

                                                                                            @Override
                                                                                            public void onFailure(Call<DataResponse> call, Throwable t) {

                                                                                            }
                                                                                        });
                                                                                    }
                                                                                }
                                                                            }

                                                                            @Override
                                                                            public void onFailure(Call<DataResponse> call, Throwable t) {

                                                                            }
                                                                        });
                                                                    }
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<DataResponse> call, Throwable t) {

                                                            }
                                                        });
                                                    }
                                                }


                                            }

                                            @Override
                                            public void onFailure(Call<DataResponse> call, Throwable t) {

                                            }
                                        });

                                    }
                                }


                            }

                        }

                        @Override
                        public void onFailure(Call<DataResponse> call, Throwable t) {

                        }
                    });
                }
            }


            cRecyclerView.setAdapter(cAdapter);

            //Log.d(TAG, "onCreate: " + myStory.getTitle());
            //storyTextView.setText(myStory.getTitle());
        }
    }
}
