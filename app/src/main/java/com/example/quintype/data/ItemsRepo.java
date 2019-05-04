package com.example.quintype.data;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;

import com.example.quintype.data.entity.Collection;
import com.example.quintype.data.entity.Item;
import com.example.quintype.datamodel.Feed;
import com.example.quintype.network.Clients;
import com.example.quintype.network.FeedService;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Callback;

import static android.content.Context.MODE_PRIVATE;

public class ItemsRepo {

    private static final String MY_PREFS_NAME = "quintype";
    private static final String CACHE_EXPIRY_TINE = "cache_expiry_time";
    private final SharedPreferences sharedPreferences;
    private final Calendar calendar;
    private final SharedPreferences.Editor editor;
    Application application;
    AppDatabase appDatabase;
    FeedService feedService;

    public ItemsRepo(Application application) {
        this.application = application;
        appDatabase = AppDatabase.getInstance(application);
        feedService = Clients.getNewsClient().create(FeedService.class);
        sharedPreferences = application.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        calendar = Calendar.getInstance();


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

    public void setCacheExipyTime() {
        Date dt = new Date();
        calendar.setTime(dt);
        calendar.add(Calendar.DATE, 1);
        dt = calendar.getTime();
        String currentTimestamp = String.valueOf(dt.getTime());
        editor.putString(CACHE_EXPIRY_TINE, currentTimestamp);
        editor.apply();
    }


    public Long getCacheExipyTime() {
        String timestamp = sharedPreferences.getString(CACHE_EXPIRY_TINE, null);
        if (timestamp != null) {
            return Long.valueOf(timestamp);
        }
        return null;
    }


    public boolean isCacheExpired(){
        Long expiryTimestamp = getCacheExipyTime();
        Long currentTimestamp = new Date().getTime();
        if(expiryTimestamp == null ||
                currentTimestamp > expiryTimestamp){
            return true;
        }

        return false;
    }
}
