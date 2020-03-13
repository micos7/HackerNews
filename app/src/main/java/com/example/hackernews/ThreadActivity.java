package com.example.hackernews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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

public class ThreadActivity extends AppCompatActivity {

    private static final String TAG = "ThreadActivity";
    private RecyclerView tRecyclerView;
    private ThreadAdapter tAdapter;
    private RecyclerView.LayoutManager tLayoutManager;
    private List<DataResponse> thread = new ArrayList<>();
    TextView tTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);



        Log.d(TAG, "haida");

        getUpperStory();
    }

    private void getUpperStory() {

        tRecyclerView = findViewById(R.id.threadRecyclerView);
        tRecyclerView.setHasFixedSize(true);
        tLayoutManager = new LinearLayoutManager(getApplicationContext());
        tRecyclerView.setLayoutManager(tLayoutManager);

        tTextView = findViewById(R.id.threadTextView);

        if (getIntent().getExtras() != null) {
            Intent intent = getIntent();
            DataResponse myThread = (DataResponse) intent.
                    getSerializableExtra("THREAD");


            Integer commentId = myThread.getParent();


            tTextView.setText(myThread.getText());



            Log.d(TAG, "parinte "+commentId.toString());

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


            Call<DataResponse> storylvl1 = hackerNewsApi.getStory(commentId);

            storylvl1.enqueue(new Callback<DataResponse>() {
                @Override
                public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                    if(response.body().getParent() != null){
                        Call<DataResponse> storylvl2 = hackerNewsApi.getStory(response.body().getParent());
                        Log.d(TAG, "lvl2 "+response.body().getText());

                        storylvl2.enqueue(new Callback<DataResponse>() {
                            @Override
                            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                                if(response.body().getParent() != null){
                                    Call<DataResponse> storylvl3 = hackerNewsApi.getStory(response.body().getParent());
                                    Log.d(TAG, "lvl3 "+response.body().getText());

                                    storylvl3.enqueue(new Callback<DataResponse>() {
                                        @Override
                                        public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                                            if(response.body().getParent() != null){
                                                Call<DataResponse> storylvl4 = hackerNewsApi.getStory(response.body().getParent());
                                                Log.d(TAG, "lvl4 "+response.body().getText());

                                                storylvl4.enqueue(new Callback<DataResponse>() {
                                                    @Override
                                                    public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                                                        if(response.body().getParent() != null){
                                                            Call<DataResponse> storylvl5 = hackerNewsApi.getStory(response.body().getParent());
                                                            Log.d(TAG, "lvl5 "+response.body().getText());

                                                            storylvl5.enqueue(new Callback<DataResponse>() {
                                                                @Override
                                                                public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                                                                    if(response.body().getParent() != null){
                                                                        Call<DataResponse> storylvl6 = hackerNewsApi.getStory(response.body().getParent());
                                                                        Log.d(TAG, "lvl6 "+response.body().getText());
                                                                        storylvl6.enqueue(new Callback<DataResponse>() {
                                                                            @Override
                                                                            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                                                                                if(response.body().getParent() != null){
                                                                                    Call<DataResponse> storylvl7 = hackerNewsApi.getStory(response.body().getParent());
                                                                                    Log.d(TAG, "lvl7 "+response.body().getText());

                                                                                    storylvl7.enqueue(new Callback<DataResponse>() {
                                                                                        @Override
                                                                                        public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {

                                                                                        }

                                                                                        @Override
                                                                                        public void onFailure(Call<DataResponse> call, Throwable t) {

                                                                                        }
                                                                                    });
                                                                                }else {
                                                                                    thread.add(response.body());
                                                                                    tAdapter = new ThreadAdapter(thread);
                                                                                    tRecyclerView.setAdapter(tAdapter);
                                                                                    tAdapter.notifyDataSetChanged();
                                                                                    Log.d(TAG, "aaa6 "+thread.get(0).getTitle());
                                                                                }
                                                                            }

                                                                            @Override
                                                                            public void onFailure(Call<DataResponse> call, Throwable t) {

                                                                            }
                                                                        });
                                                                    }else {
                                                                        thread.add(response.body());
                                                                        tAdapter = new ThreadAdapter(thread);
                                                                        tRecyclerView.setAdapter(tAdapter);
                                                                        tAdapter.notifyDataSetChanged();
                                                                        Log.d(TAG, "aaa5 "+thread);
                                                                    }
                                                                }

                                                                @Override
                                                                public void onFailure(Call<DataResponse> call, Throwable t) {

                                                                }
                                                            });
                                                        }else {
                                                            thread.add(response.body());
                                                            tAdapter = new ThreadAdapter(thread);
                                                            tRecyclerView.setAdapter(tAdapter);
                                                            tAdapter.notifyDataSetChanged();
                                                            Log.d(TAG, "aaa4 "+thread);
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<DataResponse> call, Throwable t) {

                                                    }
                                                });
                                            }else {
                                                thread.add(response.body());
                                                tAdapter = new ThreadAdapter(thread);
                                                tRecyclerView.setAdapter(tAdapter);
                                                tAdapter.notifyDataSetChanged();
                                                Log.d(TAG, "aaa3 "+thread);
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<DataResponse> call, Throwable t) {

                                        }
                                    });
                                }else {
                                    thread.add(response.body());
                                    tAdapter = new ThreadAdapter(thread);
                                    tRecyclerView.setAdapter(tAdapter);
                                    tAdapter.notifyDataSetChanged();
                                    Log.d(TAG, "aaa2 "+thread);
                                }

                            }

                            @Override
                            public void onFailure(Call<DataResponse> call, Throwable t) {

                            }
                        });
                    }else {
                        thread.add(response.body());
                        tAdapter = new ThreadAdapter(thread);
                        tRecyclerView.setAdapter(tAdapter);
                        tAdapter.notifyDataSetChanged();
                        Log.d(TAG, "aaa1 "+thread);
                    }
                }

                @Override
                public void onFailure(Call<DataResponse> call, Throwable t) {

                }

            });

            Log.d(TAG, "verific "+thread);
        }
    }

}
