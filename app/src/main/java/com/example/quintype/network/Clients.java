package com.example.quintype.network;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Clients {


    private static final String BASE_URL = "https://demo9639618.mockable.io/" ;

    Application application;

    private static Retrofit retrofit = null;


    public static Retrofit getNewsClient() {
        if (retrofit==null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder defaultHttpClient= new OkHttpClient.Builder();
            defaultHttpClient.addInterceptor(logging);
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(defaultHttpClient.build())
                    .build();
        }
        return retrofit;
    }

    public Clients(Application application) {
        this.application = application;
    }

}
