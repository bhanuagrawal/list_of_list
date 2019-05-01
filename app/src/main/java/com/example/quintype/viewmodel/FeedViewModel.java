package com.example.quintype.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.quintype.data.ItemsRepo;
import com.example.quintype.data.entity.Collection;
import com.example.quintype.data.entity.Item;
import com.example.quintype.datamodel.Feed;
import com.example.quintype.datamodel.Status;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedViewModel extends AndroidViewModel {


    MutableLiveData<Status> feedStatusLiveData;
    ItemsRepo itemsRepo;
    public FeedViewModel(@NonNull Application application) {
        super(application);
        itemsRepo = new ItemsRepo(application);
        getFeedFromNetwork();
    }

    public MutableLiveData<Status> getFeedStatusLiveData() {
        if(feedStatusLiveData == null){
            feedStatusLiveData = new MutableLiveData<>();
        }
        return feedStatusLiveData;
    }

    public LiveData<List<Item>> getFeedFromCache(){
        return itemsRepo.getFeedFromCache();
    }

    public void getFeedFromNetwork(){
        itemsRepo.getFeedFromNetwork(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, final Response<Feed> response) {
                if(response.isSuccessful()){
                    Completable.fromAction(new Action() {
                        @Override
                        public void run() throws Exception {
                            itemsRepo.clearCache();
                            itemsRepo.addToCache(response.body());
                        }
                    }).observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onComplete() {
                            getFeedStatusLiveData().postValue(Status.SUCCESS);

                        }

                        @Override
                        public void onError(Throwable e) {
                            getFeedStatusLiveData().postValue(Status.FAILURE);

                        }

                    });
                }
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                getFeedStatusLiveData().postValue(Status.FAILURE);

            }
        });

    }



    public LiveData<Collection> getCollectionLiveData(Item item) {
        return itemsRepo.getCollectionFromCache(item.getItemId());
    }

    public void getCollectionFromNetwork(Item item) {
        String url = item.getUrl();
        itemsRepo.getColletionFromNetwork(url, new Callback<Collection>() {
            @Override
            public void onResponse(Call<Collection> call, Response<Collection> response) {
                if(response.isSuccessful()){
                    Completable.fromAction(new Action() {
                        @Override
                        public void run() throws Exception {
                            itemsRepo.clearCollectionCache(item.getItemId());
                            Collection collection = response.body();
                            collection.setItemId(item.getItemId());
                            itemsRepo.addCollectionToCache(collection);
                        }
                    }).observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onComplete() {
                            Log.e("collection", " add success");

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("collection", " add error");

                        }

                    });
                }
            }

            @Override
            public void onFailure(Call<Collection> call, Throwable t) {
                Log.e("collection", "network failure");
            }
        });
    }
}
