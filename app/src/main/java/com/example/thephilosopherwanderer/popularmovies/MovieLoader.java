package com.example.thephilosopherwanderer.popularmovies;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by alex on 18.03.2018.
 */

public class MovieLoader extends AsyncTaskLoader<List<MovieObject>> {
    private String myRequestUrl;

    public MovieLoader(Context context, String requestUrl) {
        super(context);
        myRequestUrl = requestUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<MovieObject> loadInBackground() {
        return QueryForMovies.returnMovie(myRequestUrl);
    }
}