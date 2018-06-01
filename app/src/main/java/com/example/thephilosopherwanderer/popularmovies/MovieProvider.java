package com.example.thephilosopherwanderer.popularmovies;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by alex on 01.06.2018.
 */

public class MovieProvider extends ContentProvider {

    public static final String LOG_TAG = MovieProvider.class.getSimpleName(); // Tag for error logs
    private static final int Favorites = 100; // Constant to know if we ar dealing with all the favorite movies
    private static final int Movie = 101; // Constant to know if we need only one favorite movie
    // Create URI matcher
    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    // Instantiate FavoriteDatabase
    static FavoritesDatabase mDbHelper;

    static {
        mUriMatcher.addURI(DbContract.CONTENT_AUTHORITY, DbContract.PATH_ITEM, Favorites); // URI for whole table
        mUriMatcher.addURI(DbContract.CONTENT_AUTHORITY, DbContract.PATH_ITEM + "/#", Movie); // URI for one movie
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new FavoritesDatabase(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sort) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;
        // Verify if we need the whole table or just one movie
        int match = mUriMatcher.match(uri);
        switch (match) {
            // Whole table
            case Favorites:
                cursor = database.query(DbContract.Movie.TABLE_NAME, projection, selection, selectionArgs, null, null, sort);
                break;
            // One single movie
            case Movie:
                selection = DbContract.Movie._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(DbContract.Movie.TABLE_NAME, projection, selection, selectionArgs, null, null, sort);
                break;
            default:
                throw new IllegalArgumentException("Cannot perform query of this URI: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }


    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = mUriMatcher.match(uri);
        switch (match) {
            case Favorites:
                return DbContract.Movie.CONTENT_LIST_TYPE;
            case Movie:
                return DbContract.Movie.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Problem with URI: " + uri + ". Match: " + match);
        }
    }


    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = mUriMatcher.match(uri);
        switch (match) {
            case Favorites:
                // Get writable database
                SQLiteDatabase database = mDbHelper.getWritableDatabase();
                // Insert new row with favorite movie
                long id = database.insert(DbContract.Movie.TABLE_NAME, null, contentValues);
                if (id == -1) {
                    Log.e(LOG_TAG, "Failed to insert row for " + uri);
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            case Movie:
                break;
            default:
                throw new IllegalArgumentException("Could not insert for the URI: " + uri);
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int deletedRows = 0;
        final int match = mUriMatcher.match(uri);
        switch (match) {
            case Favorites:
                // Delete rows with specified selection and selectionArgs
                deletedRows = database.delete(DbContract.Movie.TABLE_NAME, selection, selectionArgs);
                break;
            case Movie:
                // Delete specific movie
                selection = DbContract.Movie._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                deletedRows = database.delete(DbContract.Movie.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Could not delete for the URI: " + uri);
        }
        if (deletedRows != 0) getContext().getContentResolver().notifyChange(uri, null);
        return deletedRows;
    }

    // No need to update, we just add, query and delete
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
