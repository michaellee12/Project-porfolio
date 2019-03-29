package com.example.android.newsapp;

/**
 * Created by Mike Lee on 10/07/2017.
 */

public class NewsArticle {
    /*Required fields include the title of the article and the name of the section that it belongs to.
     *Optional fields (if available) : author name , date published
     */
    private String Title;
    private String SectionName;
    private String Type;
    private String Date;
    private String URL;

    public NewsArticle(String mTitle, String mSectionName, String mType, String mDate, String mURL) {
        Title = mTitle;
        SectionName = mSectionName;
        Type = mType;
        Date = mDate;
        URL = mURL;
    }

    public String getTitle() {
        return Title;
    }

    public String getSectionName() {
        return SectionName;
    }

    public String getType() {
        return Type;
    }

    public String getDate() {
        return Date;
    }

    public String getURL() {
        return URL;
    }


}
