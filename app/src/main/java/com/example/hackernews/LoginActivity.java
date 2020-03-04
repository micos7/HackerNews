package com.example.hackernews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.xml.sax.helpers.DefaultHandler;


import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.concurrent.TimeUnit;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText username = findViewById(R.id.editTextUsername);
        EditText password = findViewById(R.id.editTextPassword);
        Button loginBtn = findViewById(R.id.loginBtn);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        CookieHandler cookieHandler = new CookieManager();
        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(interceptor)
                .cookieJar(new JavaNetCookieJar(cookieHandler))
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        client.followRedirects();


        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);





        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://news.ycombinator.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        HackerNewsApi hackerNewsApi = retrofit.create(HackerNewsApi.class);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            private static final String TAG = "LoginActivity";

            @Override
            public void onClick(View v) {
                Call<DefaultHandler> response = hackerNewsApi.login(username.getText().toString(), password.getText().toString());
                response.enqueue(new Callback<DefaultHandler>() {
                    @Override
                    public void onResponse(Call<DefaultHandler> call, Response<DefaultHandler> response) {
                        Log.d(TAG, "LOGIN: "+response.body());
                    }

                    @Override
                    public void onFailure(Call<DefaultHandler> call, Throwable t) {

                    }
                });

            }
        });
    }
}
