package com.example.thephilosopherwanderer.popularmovies;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<MovieObject>>{
    private static final String API_KEY = BuildConfig.API_KEY;
    // Constant value for the ID of the Loader
    private static final int MOVIE_LOADER_ID = 1;
    private static final int TRAILER_LOADER_ID = 2;
    String BASE_URL = "https://api.themoviedb.org/3/movie/";
    String PRE_API_KEY_URL = "?api_key=";
    // GridView object to be referenced throughout this class
    GridView gridView;
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set the Progress bar view to GONE
        View progressBar = findViewById(R.id.progress_view);
        progressBar.setVisibility(View.GONE);
        // Set Grid View to GONE
        gridView = findViewById(R.id.grid_view);
        gridView.setVisibility(View.GONE);
        // Set the text of the empty view to display some instructions
        TextView emptyTextView = findViewById(R.id.empty_view);
        emptyTextView.setText(R.string.nothing_to_show);
        // Set the empty view to VISIBLE
        emptyTextView.setVisibility(View.VISIBLE);

        tryToConnect();
    }

    private void tryToConnect() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
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
            TextView emptyTextView = findViewById(R.id.empty_view);
            emptyTextView.setText(R.string.no_internet);
            emptyTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Loader<List<MovieObject>> onCreateLoader(int i, Bundle bundle) {

        // Set the progress bar view to VISIBLE and the list view and the empty view to GONE
        // Make the progress bar visible
        ProgressBar progressBar = findViewById(R.id.progress_view);
        progressBar.setVisibility(View.VISIBLE);
        // Set the list view to GONE
        gridView.setVisibility(View.GONE);
        // Set the empty text view to GONE
        TextView emptyTextView = findViewById(R.id.empty_view);
        emptyTextView.setVisibility(View.GONE);

        // Get an instance of the preferences
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        // Get the ordering preference
        String orderBy = sharedPrefs.getString(
                getString(R.string.order_preference_key),
                getString(R.string.settings_order_by_popularity)
        );

        String urlString;

        // Create the URL for the query
        urlString = BASE_URL + orderBy + PRE_API_KEY_URL + API_KEY;

        // Create a new loader for the given URL
        return new MovieLoader(this, urlString);
    }

    @Override
    public void onLoadFinished(Loader<List<MovieObject>> loader, List<MovieObject> movies) {

        // Find the Empty text view
        TextView emptyTextView = findViewById(R.id.empty_view);
        // Find the progress bar view
        ProgressBar progressBar = findViewById(R.id.progress_view);

        if (movies != null && !movies.isEmpty()) {
            // Update adapter
            adapter = new MovieAdapter(MainActivity.this, movies);
            // Set the adapter to that List View
            gridView.setAdapter(adapter);
            // Set the list view to visible.
            gridView.setVisibility(View.VISIBLE);
            // Set the progress bar and the empty text view to GONE
            progressBar.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.GONE);
        } else {
            // Set the empty text view to visible
            emptyTextView.setText(R.string.no_movies);
            emptyTextView.setVisibility(View.VISIBLE);
            // Set the progress bar and the list view to GONE
            gridView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<MovieObject>> loader) {
        adapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.filter_settings) {
            Intent goToFilter = new Intent(this, FilterActivity.class);
            startActivity(goToFilter);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        tryToConnect();
        super.onResume();
    }
}
