package com.example.thephilosopherwanderer.popularmovies;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alex on 01.06.2018.
 */

public class FavoritesDatabase extends SQLiteOpenHelper {

    // Constants for the name and the version of the database
    private static final String DATABASE_NAME = "favorites.db";
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public FavoritesDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // SQLite command to create database
        final String SQL_CREATE_DATABASE = "CREATE TABLE " +
                DbContract.Movie.TABLE_NAME + " (" +
                DbContract.Movie._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DbContract.Movie.COLUMN_TITLE + " TEXT NOT NULL," +
                DbContract.Movie.COLUMN_POSTER_PATH + " TEXT," +
                DbContract.Movie.COLUMN_OVERVIEW + " TEXT," +
                DbContract.Movie.COLUMN_RELEASE_DATE + " TEXT," +
                DbContract.Movie.COLUMN_RATING + " TEXT," +
                DbContract.Movie.COLUMN_MOVIE_ID + " TEXT NOT NULL" +
                ");";

        // Execute the creation command
        sqLiteDatabase.execSQL(SQL_CREATE_DATABASE);

    }

    // For updating the database
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContract.Movie.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
