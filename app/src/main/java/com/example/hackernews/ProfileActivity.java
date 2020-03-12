package com.example.hackernews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
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

public class ProfileActivity extends AppCompatActivity implements SubmissionsAdapter.OnStoryListener {

    private static final String TAG = "ProfileActivity";

    TextView pTextView;
    List<Integer> submissionsId;
    Call<DataResponse> story;
    List<DataResponse> stories = new ArrayList<>();
    RecyclerView mRecyclerView;
    SubmissionsAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        String userName = intent.getExtras().getString("username");

        pTextView = findViewById(R.id.profileTextView);

        pTextView.setText(userName);

        mRecyclerView = findViewById(R.id.profileRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new SubmissionsAdapter(stories,this);
        mRecyclerView.setAdapter(mAdapter);

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

        HackerNewsApi hackerNewsApi = retrofit.create(HackerNewsApi.class);

        Call<ProfileResponse> profileData = hackerNewsApi.getProfile(userName);

        profileData.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {

                submissionsId = response.body().getSubmitted();

                for (int i =0;i<submissionsId.size();i++){
                    story = hackerNewsApi.getStory(submissionsId.get(i));
                    story.enqueue(new Callback<DataResponse>() {
                        @Override
                        public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                            Log.d(TAG, "abc"+response.body().getText());
                            stories.add(response.body());
                            mAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<DataResponse> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void onStoryClick(int position) {
        Log.d(TAG, "TEST ");
        if(stories.get(position).getTitle() != null){
            Intent intent = new Intent(this, StoryActivity.class);
            intent.putExtra("STORY", stories.get(position));
            startActivity(intent);
        }else{
            if(stories.get(position).getText() != null){
                Intent intent = new Intent(this, ThreadActivity.class);
                intent.putExtra("THREAD", stories.get(position));
                startActivity(intent);
            }
        }

    }


    public void getAllSubmissions(List<DataResponse> stories){

    }
}
