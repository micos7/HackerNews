package com.example.hackernews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThreadActivity extends AppCompatActivity {

    private RecyclerView tRecyclerView;
    private RecyclerView.Adapter tAdapter;
    private RecyclerView.LayoutManager tLayoutManager;
    private List<DataResponse> threads = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);


        tRecyclerView = findViewById(R.id.threadRecyclerView);
        tRecyclerView.setHasFixedSize(true);
        tLayoutManager = new LinearLayoutManager(getApplicationContext());
        tRecyclerView.setLayoutManager(tLayoutManager);
        tAdapter = new ThreadAdapter(threads);


        if (getIntent().getExtras() != null) {
            Intent intent = getIntent();
            DataResponse myStory = (DataResponse) intent.
                    getSerializableExtra("THREAD");


            List<Integer> commentsIds = myStory.getKids();


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
        }
    }

}
