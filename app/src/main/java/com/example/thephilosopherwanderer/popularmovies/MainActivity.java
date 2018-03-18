package com.example.thephilosopherwanderer.popularmovies;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<MovieObject>>{

    // GridView object to be referenced throughout this class
    GridView gridView;
    // Constant value for the ID of the Loader
    private static final int MOVIE_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the Progress bar view to GONE
        View progressBar = findViewById(R.id.progress_view);
        progressBar.setVisibility(View.GONE);
        // Set Grid View to GONE
        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setVisibility(View.GONE);
        // Set the text of the empty view to display some instructions
        TextView emptyTextView = (TextView) findViewById(R.id.empty_view);
        emptyTextView.setText(R.string.nothing_to_show);
        // Set the empty view to VISIBLE
        emptyTextView.setVisibility(View.VISIBLE);

        tryToConnect();
    }

    private void tryToConnect() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        // If there is a network connection, initialize our loader
        if (networkInfo != null && networkInfo.isConnected())

        {
            // Get a reference to the LoaderManager
            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader
            loaderManager.initLoader(MOVIE_LOADER_ID, null, MainActivity.this);
            loaderManager.restartLoader(MOVIE_LOADER_ID, null, MainActivity.this);
        } else {
            // If there is no internet connection
            // Set the visibility of the progress bar to GONE and the empty text view to visible
            View progressBar = findViewById(R.id.progress_view);
            progressBar.setVisibility(View.GONE);
            // Set the visibility of the list view to GONE
            gridView.setVisibility(View.GONE);
            // Set the text of the empty text view so that it says there is no internet connection
            TextView emptyTextView = (TextView) findViewById(R.id.empty_view);
            emptyTextView.setText(R.string.no_internet);
            emptyTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Loader<List<MovieObject>> onCreateLoader(int i, Bundle bundle) {

        // Set the progress bar view to VISIBLE and the list view and the empty view to GONE
        // Make the progress bar visible
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_view);
        progressBar.setVisibility(View.VISIBLE);
        // Set the list view to GONE
        gridView.setVisibility(View.GONE);
        // Set the empty text view to GONE
        TextView emptyTextView = (TextView) findViewById(R.id.empty_view);
        emptyTextView.setVisibility(View.GONE);

        // Get preferences
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minDate = sharedPrefs.getString(getString(R.string.settings_min_date_key), "2017-07-01");
        minDate = minDate.replace(".", "-");
        String maxDate = sharedPrefs.getString(getString(R.string.settings_max_date_key), "2017-07-03");
        maxDate = maxDate.replace(".", "-");

        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        String urlString;
        urlString = null;

        // Create a new loader for the given URL
        return new MovieLoader(this, urlString);
    }

    @Override
    public void onLoadFinished(Loader<List<MovieObject>> loader, List<MovieObject> movieObjectGrid) {

    }

    @Override
    public void onLoaderReset(Loader<List<MovieObject>> loader) {

    }
}
