package com.example.quintype.data;

import android.app.Application;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.quintype.data.dao.CollectionDao;
import com.example.quintype.data.dao.ItemsDao;
import com.example.quintype.data.entity.Collection;
import com.example.quintype.data.entity.Item;


@Database(entities = {Item.class, Collection.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract ItemsDao itemsDao();

    public abstract CollectionDao collectionDao();

    public static AppDatabase getInstance(Application application){

        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(application,
                    AppDatabase.class, "quintype-data").build();
        }

        return INSTANCE;
    }
}
