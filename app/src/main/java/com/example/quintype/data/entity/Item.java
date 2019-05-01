
package com.example.quintype.data.entity;

import androidx.annotation.NonNull;
import androidx.annotation.PluralsRes;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.quintype.datamodel.Story;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Entity
public class Item {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    @NonNull
    private Integer itemId;


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("slug")
    @Expose
    private String slug;



    @SerializedName("story")
    @Expose
    private Story story;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    @NonNull
    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(@NonNull Integer itemId) {
        this.itemId = itemId;
    }
}
