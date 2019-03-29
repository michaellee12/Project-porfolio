package com.example.android.googlebooks;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Books>> {

    public final String url = "https://www.googleapis.com/books/v1/volumes";
    ArrayList<Books> arrayList = new ArrayList<Books>();
    BookAdapter adapter;
    public static int httpResponse = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.list);
        adapter = new BookAdapter(this, arrayList);
        listView.setAdapter(adapter);

        TextView text = (TextView) findViewById(R.id.emptyText);
        findViewById(R.id.loading).setVisibility(View.GONE);
        text.setText("Enter keywords into the search bar. Then click the search button.");
        text.setVisibility(View.VISIBLE);
        listView.setEmptyView(text);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load();
            }
        });
    }

    public void load() {
        getSupportLoaderManager().restartLoader(1, null, MainActivity.this).forceLoad();
        TextView text = (TextView) findViewById(R.id.emptyText);
        text.setVisibility(View.GONE);
        ListView listView = (ListView) findViewById(R.id.list);
        findViewById(R.id.loading).setVisibility(View.VISIBLE);
        listView.setEmptyView(findViewById(R.id.loading));

        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conMgr.getActiveNetworkInfo();
        httpResponse = 0;
        if (networkInfo != null && networkInfo.isConnected()) {
            getSupportLoaderManager().initLoader(1, null, MainActivity.this).forceLoad();


        } else {
            findViewById(R.id.loading).setVisibility(View.GONE);
            text.setText("No Internet");
            text.setVisibility(View.VISIBLE);
            listView.setEmptyView(text);
        }
    }

    @Override
    public Loader<ArrayList<Books>> onCreateLoader(int id, Bundle args) {
        EditText editText = (EditText) findViewById(R.id.edit);
        String keywork = editText.getText().toString().trim();
        if (keywork.equals("") | keywork == null) {
            keywork = "android";
        }

        String buildUrl = url;
        buildUrl += "?q=" + keywork;

        return new BookLoader(MainActivity.this, buildUrl);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Books>> loader, ArrayList<Books> data) {
        adapter.setBooks(data);
        TextView text = (TextView) findViewById(R.id.emptyText);
        ListView listView = (ListView) findViewById(R.id.list);

        if (httpResponse == 200) {
            findViewById(R.id.loading).setVisibility(View.GONE);
            text.setText("No Match. Try different keywords.");
            text.setVisibility(View.VISIBLE);
            listView.setEmptyView(text);
        } else {
            findViewById(R.id.loading).setVisibility(View.GONE);
            text.setText("No Internet");
            text.setVisibility(View.VISIBLE);
            listView.setEmptyView(text);

        }

    }


    @Override
    public void onLoaderReset(Loader<ArrayList<Books>> loader) {
        adapter.setBooks(new ArrayList<Books>());
    }
}
