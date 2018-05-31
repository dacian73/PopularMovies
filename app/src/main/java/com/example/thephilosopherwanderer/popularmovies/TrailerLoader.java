package com.example.thephilosopherwanderer.popularmovies;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by alex on 18.03.2018.
 */

public class TrailerLoader extends AsyncTaskLoader<List<TrailerObject>> {
    private String myRequestUrl;

    TrailerLoader(Context context, String requestUrl) {
        super(context);
        myRequestUrl = requestUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<TrailerObject> loadInBackground() {
        return QueryForTrailer.returnTrailer(myRequestUrl);
    }
}