package com.jenishbek.armatov.news;


import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jenishbek.armatov.news.dto.Article;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StorageUtil {

    private final String STORAGE = "com.jenishbek.armatov.news";
    private SharedPreferences preferences;
    private Context context;


    public StorageUtil(Context context) {
        this.context = context;
    }

    public void addArticle(List<Article> arrayList, String key) {
        preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        editor.putString(key, json);
        editor.apply();
    }

    public List<Article> getArticle(String key) {
        preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString(key, null);
        Type type = new TypeToken<ArrayList<Article>>() {
        }.getType();
        return gson.fromJson(json, type);
    }


}


