package com.example.thephilosopherwanderer.popularmovies;

/**
 * Created by alex on 17.03.2018.
 */


public class MovieObject {
    // Declaring variables
    private String mTitle;
    private String mPosterPath;
    private String mOverview;
    private String mReleaseDate;
    private String mRating;


    // Constructor
    MovieObject(String title, String posterPath, String overview, String releaseDate, String rating) {
        mTitle = title;
        mOverview = overview;
        mReleaseDate = releaseDate;
        mRating = rating;
        mPosterPath = posterPath;
    }

    // Getter methods
    String getmTitle() {
        return mTitle;
    }

    // Setter methods
    public void setmTitle (String title) {
        mTitle = title;
    }

    String getmOverview() {
        return mOverview;
    }

    public void setmOverview(String overview) {
        mOverview = overview;
    }

    String getmReleaseDate() {
        return mReleaseDate;
    }

    public void setmReleaseDate(String date) {
        mReleaseDate = date;
    }

    String getmRating() {
        return mRating;
    }

    public void setmRating(String section) { mRating = section; }

    String getPosterPath() {
        return mPosterPath;
    }

    public void setmPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }
}


