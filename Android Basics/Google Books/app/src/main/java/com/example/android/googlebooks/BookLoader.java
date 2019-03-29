package com.example.android.googlebooks;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Mike Lee on 9/07/2017.
 */

public class BookLoader extends AsyncTaskLoader<ArrayList<Books>> {
    private String mUrl;

    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    public ArrayList<Books> loadInBackground() {
        ArrayList<Books> list = QueryUtils.fetchBookData(mUrl);
        return list;
    }
}
