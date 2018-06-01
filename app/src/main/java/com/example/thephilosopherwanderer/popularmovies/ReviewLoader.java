package com.example.thephilosopherwanderer.popularmovies;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by alex on 18.03.2018.
 */

public class ReviewLoader extends AsyncTaskLoader<List<ReviewObject>> {
    private String myRequestUrl;

    public ReviewLoader(Context context, String requestUrl) {
        super(context);
        myRequestUrl = requestUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<ReviewObject> loadInBackground() {
        return QueryForReview.returnReview(myRequestUrl);
    }
}