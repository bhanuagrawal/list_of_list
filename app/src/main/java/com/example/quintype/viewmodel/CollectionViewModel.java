package com.example.quintype.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.quintype.data.ItemsRepo;
import com.example.quintype.data.entity.Item;
import com.example.quintype.data.entity.Collection;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionViewModel extends AndroidViewModel {


    ItemsRepo itemsRepo;

    public CollectionViewModel(@NonNull Application application) {
        super(application);
        itemsRepo = new ItemsRepo(application);

    }







    // TODO: Implement the ViewModel
}
