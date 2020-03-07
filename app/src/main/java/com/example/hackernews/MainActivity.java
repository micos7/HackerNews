package com.example.hackernews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.AbsListView;
import android.widget.ProgressBar;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import java.net.CookieHandler;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements StoryAdapter.OnStoryListener {

    private static final String TAG = "MainActivity";

    private RecyclerView mRecyclerView;
    private StoryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<DataResponse> dataResponses = new ArrayList<>();
    static List<Integer> ids;
    boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    int totalList;
    int noComments;
    String cookieStr;
    Integer j;
    Call<List<Integer>> idlist;
    HackerNewsApi hackerNewsApi;
    Call<DataResponse> story;
    ProgressBar progressBar;
    Toolbar toolbar;
    private SwipeRefreshLayout swipeContainer;

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public static void clearCookies(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (cookieStr != null) {
            menu.removeItem(R.id.login);
        }
        if (cookieStr == null) {
            menu.removeItem(R.id.logout);
            menu.removeItem(R.id.profile);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.login:
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
                return true;
            case R.id.profile:
                Intent profileIntent = new Intent(this, ProfileActivity.class);
                startActivity(profileIntent);
                return true;
            case R.id.logout:
                cookieStr = null;
                clearCookies(this);
                invalidateOptionsMenu();
                return true;

            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }

    }

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressBar = findViewById(R.id.progress);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        swipeContainer = findViewById(R.id.swipeRefresh);

        CookieManager mCookieManager = CookieManager.getInstance();
        if (mCookieManager.hasCookies()) {
            cookieStr = CookieManager.getInstance().getCookie("https://news.ycombinator.com/login");
            Log.d(TAG, "cookies: " + cookieStr);
        }


        getFirstStories();


        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new StoryAdapter(dataResponses, this, this);
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
                Log.e("onSubscribe", "VERTICAL " + dy);
                scrollOutItems = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    isScrolling = false;
                    getMoreStories();
                }
            }
        });


        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.clear();
                getFirstStories();
                mAdapter.addAll(dataResponses);
                swipeContainer.setRefreshing(false);
            }
        });


    }

    private void getFirstStories() {
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
    }

    private void getMoreStories() {

        idlist.clone().enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                Log.e("onSubscribe", "TEST: " + totalList);
                if (totalList > j) {
                    for (Integer i = totalItems; i < 14 + totalItems; i++) {
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
                                    progressBar.setVisibility(GONE);
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
        Intent intent = new Intent(this, StoryActivity.class);
        intent.putExtra("STORY", dataResponses.get(position));
        startActivity(intent);
    }
}

