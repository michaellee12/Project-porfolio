package com.example.android.newsapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Mike Lee on 10/07/2017.
 */

public class QueryUtils {
    public final static String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
        //An empty private constructor makes sure that the class is not going to be initialised.
    }

    public static ArrayList<NewsArticle> fetchData(String Url) {
        URL url = createURL(Url);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
            return new ArrayList<>();
        }
        ArrayList<NewsArticle> Array = extractData(jsonResponse);
        return Array;
    }

    private static URL createURL(String stringURL) {
        URL url = null;
        try {
            url = new URL(stringURL);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            MainActivity.httpResponse = urlConnection.getResponseCode();
            if (MainActivity.httpResponse == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
            MainActivity.httpResponse = urlConnection.getResponseCode();
            return new String();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            return jsonResponse;
        }
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static ArrayList<NewsArticle> extractData(String JsonResponse) {
        ArrayList<NewsArticle> List = new ArrayList<>();
        try {
            JSONObject Json = new JSONObject(JsonResponse);
            JSONObject Response = Json.getJSONObject("response");
            if (Response.has("results")) {
                JSONArray Array = Response.getJSONArray("results");
                for (int i = 0; i < Array.length(); i++) {
                    JSONObject article = Array.getJSONObject(i);
                    String Title = article.getString("webTitle");
                    String SectionName = null;
                    String URL = null;
                    String Type = null;
                    String Date = null;

                    if (article.has("sectionName")) {
                        SectionName = article.getString("sectionName");
                    }
                    if (article.has("webUrl")) {
                        URL = article.getString("webUrl");
                    }
                    if (article.has("type")) {
                        Type = article.getString("type");
                    }
                    if (article.has("webPublicationDate")) {
                        Date = article.getString("webPublicationDate");
                    }

                    List.add(new NewsArticle(Title, SectionName, Type, Date, URL));
                }
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the JSON results", e);
            return List;
        }
        return List;
    }

}
