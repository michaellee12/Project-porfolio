package com.example.android.googlebooks;

/**
 * Created by Mike Lee on 9/07/2017.
 */

public class Books {
    private String Title;
    private String Info;
    private String Author;

    public Books(String mTitle, String mAuthor, String mInfo) {
        Title = mTitle;
        Info = mInfo;
        Author = mAuthor;
    }

    public String getTitle() {
        return Title;
    }

    public String getInfo() {
        return Info;
    }

    public String getAuthor() {
        return Author;
    }

}
