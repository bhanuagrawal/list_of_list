package com.example.quintype.data;

import androidx.room.TypeConverter;

import com.example.quintype.data.entity.Item;
import com.example.quintype.datamodel.Story;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {

    private static Gson gson = new Gson();

    private static Type type = new TypeToken<List<Item>>(){}.getType();
    @TypeConverter
    public static Story fromString(String value) {
        return gson.fromJson(value, Story.class);
    }
    @TypeConverter
    public static String fromStory(Story story) {
        return gson.toJson(story);
    }

    @TypeConverter
    public static List<Item> storiesFromString(String value){
        return gson.fromJson(value, type);
    }

    @TypeConverter
    public static String fromItemList(List<Item> items) {
        return gson.toJson(items);
    }
}
