package com.example.android.newsapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

/**
 * Created by Mike Lee on 10/07/2017.
 */

public class NewsLoader extends AsyncTaskLoader<ArrayList<NewsArticle>> {
    private String URL;

    public NewsLoader(Context context, String mURL) {
        super(context);
        URL = mURL;
    }

    @Override
    public ArrayList<NewsArticle> loadInBackground() {
        ArrayList<NewsArticle> list = QueryUtils.fetchData(URL);
        return list;
    }
}
