package com.example.quintype.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.quintype.data.entity.Collection;

import java.util.List;

@Dao
public interface CollectionDao {

    @Query("select * from collection where item_id= :itemId")
    LiveData<Collection> getCollection(int itemId);


    @Query("Delete from item")
    void deleteAll();

    @Insert
    void add(List<Collection> collections);

    @Insert
    void add(Collection collection);


    @Query("delete from collection where item_id = :id")
    void delete(int id);
}
