package com.example.quintype.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.quintype.data.entity.Item;

import java.util.List;

@Dao
public interface ItemsDao {

    @Query("select * from item")
    LiveData<List<Item>> getItems();


    @Query("Delete from item")
    void deleteAll();

    @Insert
    void add(List<Item> items);
}
