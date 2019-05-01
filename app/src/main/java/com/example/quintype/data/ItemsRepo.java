package com.example.quintype.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.quintype.data.entity.Collection;
import com.example.quintype.data.entity.Item;
import com.example.quintype.datamodel.Feed;
import com.example.quintype.network.Clients;
import com.example.quintype.network.FeedService;

import java.util.List;

import retrofit2.Callback;

public class ItemsRepo {

    Application application;
    AppDatabase appDatabase;
    FeedService feedService;

    public ItemsRepo(Application application) {
        this.application = application;
        appDatabase = AppDatabase.getInstance(application);
        feedService = Clients.getNewsClient().create(FeedService.class);

    }


    public void getFeedFromNetwork(Callback<Feed> callback) {
        feedService.getFeed().enqueue(callback);
    }

    public void addToCache(Feed feed) {
        appDatabase.itemsDao().add(feed.getItems());
    }

    public LiveData<List<Item>> getFeedFromCache() {
        return appDatabase.itemsDao().getItems();
    }

    public void clearCache() {
        appDatabase.itemsDao().deleteAll();
    }

    public LiveData<Collection> getCollectionFromCache(int id) {
        return appDatabase.collectionDao().getCollection(id);
    }

    public void getColletionFromNetwork(String url, Callback<Collection> callback) {
        feedService.getCollection(url).enqueue(callback);
    }

    public void clearCollectionCache(int id) {
        appDatabase.collectionDao().delete(id);
    }

    public void addCollectionToCache(Collection collection) {
        appDatabase.collectionDao().add(collection);
    }

}
