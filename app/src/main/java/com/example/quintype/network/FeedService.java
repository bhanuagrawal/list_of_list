package com.example.quintype.network;

import com.example.quintype.data.entity.Collection;
import com.example.quintype.datamodel.Feed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

public interface FeedService {
    @Headers({"Accept: application/json"})
    @GET("/collection")
    Call<Feed> getFeed();

    @Headers({"Accept: application/json"})
    @GET
    Call<Collection> getCollection(@Url String url);
}