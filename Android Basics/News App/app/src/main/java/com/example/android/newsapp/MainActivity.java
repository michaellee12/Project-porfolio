package com.example.android.newsapp;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<NewsArticle>> {
    public final String url = "https://content.guardianapis.com/search?q=";
    public static int httpResponse = 0;
    public ArrayList<NewsArticle> Array = new ArrayList<NewsArticle>();
    public NewsAdapter adapter;
    public static String search;
    public SearchView menuSearchView;
    public static int ScreenWidth = 0;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenWidth = getWindowManager().getDefaultDisplay().getWidth();

        context = getApplicationContext();
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new NewsAdapter(this, Array);
        listView.setAdapter(adapter);
        TextView text = (TextView) findViewById(R.id.emptyText);
        text.setText("Enter keywords into the search bar. Then click the search button.");
        listView.setEmptyView(text);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                NewsArticle newsArticle = Array.get(position);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(newsArticle.getURL()));
                startActivity(i);

            }
        });
    }

    public void Load(String keyword) {
        TextView text = (TextView) findViewById(R.id.emptyText);
        View loading = findViewById(R.id.loading);
        ListView listView = (ListView) findViewById(R.id.listView);
        text.setVisibility(View.GONE);
        listView.setEmptyView(loading);
        loading.setVisibility(View.VISIBLE);

        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            httpResponse = 0;
            getSupportLoaderManager().destroyLoader(1);
            getSupportLoaderManager().initLoader(1, null, MainActivity.this).forceLoad();
        } else {
            adapter.setList(new ArrayList<NewsArticle>());
            loading.setVisibility(View.GONE);
            text.setText("No Internet");
            listView.setEmptyView(text);
            text.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            menuSearchView.setQuery(query, true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_action, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        menuSearchView = searchView;

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        ComponentName componentName = new ComponentName(MainActivity.this, MainActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));

        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        menuSearchView.clearFocus();
                        search = query;
                        Load(query);
                        return true;
                    }
                }
        );
        return true;
    }


    @Override
    public Loader<ArrayList<NewsArticle>> onCreateLoader(int id, Bundle args) {
        String inputURL = url + search + "&api-key=test";
        return new NewsLoader(MainActivity.this, inputURL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<NewsArticle>> loader, ArrayList<NewsArticle> data) {
        TextView text = (TextView) findViewById(R.id.emptyText);
        View loading = findViewById(R.id.loading);
        ListView listView = (ListView) findViewById(R.id.listView);
        loading.setVisibility(View.GONE);

        if (httpResponse == 200) {
            text.setText("No Match. Try different keywords.");
        } else {
            text.setText("No Internet");
        }
        listView.setEmptyView(text);
        text.setVisibility(View.VISIBLE);
        adapter.setList(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<NewsArticle>> loader) {
        adapter.setList(new ArrayList<NewsArticle>());
    }
}
