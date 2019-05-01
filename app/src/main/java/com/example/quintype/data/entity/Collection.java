
package com.example.quintype.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.List;

import com.example.quintype.data.entity.Item;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(onDelete = CASCADE, entity = Item.class,
        parentColumns = "_id",
        childColumns = "item_id"))
public class Collection {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "collection_id")
    @NonNull
    private Integer collectionId;


    @ColumnInfo(name = "item_id")
    private int itemId;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @NonNull
    public Integer getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(@NonNull Integer collectionId) {
        this.collectionId = collectionId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
