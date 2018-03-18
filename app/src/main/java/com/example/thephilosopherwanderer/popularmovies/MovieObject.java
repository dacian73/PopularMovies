package com.example.thephilosopherwanderer.popularmovies;

/**
 * Created by alex on 17.03.2018.
 */


public class MovieObject {
    // Declaring variables
    String mTitle;
    String mOverview;
    String mReleaseDate;
    String mUrl;
    String mRating;

    // Constructor
    public MovieObject (String title, String publication, String date, String url, String section) {
        mTitle = title;
        mOverview = publication;
        mReleaseDate = date;
        mUrl = url;
        mRating = section;
    }

    // Getter methods
    public String getmTitle () {
        return mTitle;
    }
    public  String getmPublication () {
        return mOverview;
    }
    public String getmReleaseDate() {
        return mReleaseDate;
    }
    public String getmUrl () {
        return mUrl;
    }
    public String getmRating() { return mRating;}

    // Setter methods
    public void setmTitle (String title) {
        mTitle = title;
    }
    public void setmPublication (String publication) {
        mOverview = publication;
    }
    public void setmReleaseDate(String date) {
        mReleaseDate = date;
    }
    public void setmUrl (String url) {
        mUrl = url;
    }
    public void setmRating(String section) { mRating = section; }
}


