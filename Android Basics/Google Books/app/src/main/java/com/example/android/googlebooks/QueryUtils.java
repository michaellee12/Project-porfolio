package com.example.android.googlebooks;

import java.io.*;

import android.os.AsyncTask;
import android.support.annotation.ArrayRes;

import java.lang.reflect.Array;
import java.util.Arrays;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.googlebooks.Books;

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
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static android.R.attr.x;
import static android.webkit.ConsoleMessage.MessageLevel.LOG;
import static java.lang.String.*;
import static java.lang.String.join;
import static java.util.Arrays.copyOfRange;

public final class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    public static ArrayList<Books> fetchBookData(String requestUrl) {
        URL url = createURL(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
            return new ArrayList<Books>();
        }
        ArrayList<Books> books = extractBooks(jsonResponse);
        return books;
    }

    private static URL createURL(String stringURl) {
        URL url = null;
        try {
            url = new URL(stringURl);
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
            if (urlConnection.getResponseCode() == 200) {
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
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static ArrayList<Books> extractBooks(String SAMPLE_JSON_RESPONSE) {

        ArrayList<Books> Books = new ArrayList<>();

        try {
            JSONObject Json = new JSONObject(SAMPLE_JSON_RESPONSE);
            if (Json.has("items")) {
                JSONArray JsonArray = Json.getJSONArray("items");
                for (int i = 0; i < JsonArray.length(); i++) {
                    JSONObject book = JsonArray.getJSONObject(i);
                    JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                    String Title = volumeInfo.getString("title");
                    String Author;
                    if (volumeInfo.has("authors")) {
                        JSONArray authorsList = volumeInfo.getJSONArray("authors");
                        Author = authorsList.join(", ");
                        Author = Author.replace("\"", "");
                    } else {
                        Author = "";
                    }
                    String Info = "";
                    if (volumeInfo.has("description")) {
                        String Info_long = volumeInfo.getString("description");
                        int len = 250;
                        if (Info_long.length() > len) {
                            Info_long = Info_long.substring(0, len);
                        }
                        List<Integer> list = new ArrayList<Integer>();
                        list.add(Info_long.lastIndexOf('.'));
                        list.add(Info_long.lastIndexOf('!'));
                        list.add(Info_long.lastIndexOf('?'));
                        int punc0 = Collections.max(list);
                        Info = Info_long.substring(0, punc0 + 1);

                    }
                    if (Info.replace(" ", "").equals("")) {
                        Info = "N.A.";
                    }
                    Books.add(new Books(Title, Author, Info));
                }
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the JSON results", e);
            return new ArrayList<Books>();
        }
        return Books;
    }

}