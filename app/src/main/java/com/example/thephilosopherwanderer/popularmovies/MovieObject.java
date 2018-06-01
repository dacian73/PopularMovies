package com.example.thephilosopherwanderer.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alex on 17.03.2018.
 */


public class MovieObject implements Parcelable {
    public static final Creator<MovieObject> CREATOR = new Creator<MovieObject>() {
        @Override
        public MovieObject createFromParcel(Parcel in) {
            return new MovieObject(in);
        }

        @Override
        public MovieObject[] newArray(int size) {
            return new MovieObject[size];
        }
    };
    // Declaring variables
    private String mTitle;
    private String mPosterPath;
    private String mOverview;
    private String mReleaseDate;
    private String mRating;
    private String mMovieID;

    // Constructor
    MovieObject(String title, String posterPath, String overview, String releaseDate, String rating, String movieID) {
        mTitle = title;
        mOverview = overview;
        mReleaseDate = releaseDate;
        mRating = rating;
        mPosterPath = posterPath;
        mMovieID = movieID;
    }

    protected MovieObject(Parcel in) {
        mTitle = in.readString();
        mPosterPath = in.readString();
        mOverview = in.readString();
        mReleaseDate = in.readString();
        mRating = in.readString();
        mMovieID = in.readString();
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

    String getMovieID() {
        return mMovieID;
    }

    public void setmPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public void setmMovieID(String movieID) {
        mMovieID = movieID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mPosterPath);
        parcel.writeString(mOverview);
        parcel.writeString(mReleaseDate);
        parcel.writeString(mRating);
        parcel.writeString(mMovieID);
    }
}


