package com.example.android.perthtouristapp;

import android.location.Location;
import android.media.Image;
import android.support.annotation.NonNull;

import static java.lang.String.valueOf;

/**
 * Created by Mike Lee on 30/06/2017.
 */

public class Item {
    private String Name;
    private String Location;
    private String Number;
    private int Image;
    private String Summary;

    public Item(String mName, String mLocation, int mImage, String mSummary) {
        Name = mName;
        Location = mLocation;
        Image = mImage;
        Summary = mSummary;
    }

    public Item(String mName, String mLocation, int mImage, String mSummary, String mNumber) {
        Name = mName;
        Location = mLocation;
        Image = mImage;
        Summary = mSummary;
        Number = mNumber;
    }

    public String getName() {
        return Name;
    }

    public String getLocation() {
        return Location;
    }

    public String getCoordinate() {
        return "google.navigation:q=" + Location;
    }

    public String getNumber() {
        return Number;
    }

    public boolean hasNumber() {
        return Number != null;
    }

    public String getSummary() {
        return Summary;
    }

    public int getImage() {
        return Image;
    }


}
