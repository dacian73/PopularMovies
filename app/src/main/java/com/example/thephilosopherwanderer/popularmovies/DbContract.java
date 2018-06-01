package com.example.thephilosopherwanderer.popularmovies;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by alex on 01.06.2018.
 */

public class DbContract {
    // Constants
    public static final String CONTENT_AUTHORITY = "com.example.thephilosopherwanderer.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_ITEM = "item";

    // Empty Constructor
    private DbContract() {
    }

    public static final class Movie implements BaseColumns {

        // Create URI
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ITEM);
        // MIME type for list
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEM;
        // MIME type for only one movie
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEM;

        // Table name
        public static final String TABLE_NAME = BaseColumns._ID;

        // Names for table columns
        public static final String COLUMN_TITLE = "mTitle";
        public static final String COLUMN_POSTER_PATH = "mPosterPath";
        public static final String COLUMN_OVERVIEW = "mOverview";
        public static final String COLUMN_RELEASE_DATE = "mReleaseDate";
        public static final String COLUMN_RATING = "mRating";
        public static final String COLUMN_MOVIE_ID = "mMovieID";
    }
}
