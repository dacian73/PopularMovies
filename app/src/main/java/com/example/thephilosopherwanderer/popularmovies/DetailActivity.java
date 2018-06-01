package com.example.thephilosopherwanderer.popularmovies;

import android.app.LoaderManager;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks, TrailerAdapter.MyOnItemClickListener {
    private static final String API_KEY = BuildConfig.API_KEY;
    // Constant value for the ID of the Loader
    private static final int TRAILER_LOADER_ID = 1;
    private static final int REVIEW_LOADER_ID = 2;
    String title;
    String overview;
    String release;
    String rating;
    ProgressBar trailerProgressBar;
    RecyclerView trailerRecycler;
    TextView emptyTextView;
    ProgressBar reviewProgressBar;
    RecyclerView reviewRecycler;
    List<TrailerObject> trailers;
    String BASE_URL = "https://api.themoviedb.org/3/movie/";
    String PRE_API_KEY_URL = "?api_key=";
    String VIDEOS_URL = "/videos";
    String REVIEWS_URL = "/reviews";
    String movieID;
    ImageView favoriteImageView;
    // Constants
    private String BASE_POSTER_URL = "http://image.tmdb.org/t/p/";
    private String BASE_POSTER_IMAGE_SIZE = "w185/";
    private String BIG_POSTER_SIZE = "w342/";
    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle data = getIntent().getExtras();
        MovieObject movie = null;
        if (data != null) {
            movie = data.getParcelable("movie");
        }
        if (movie != null) {
            // Set the title of the movie
            title = movie.getmTitle();
            TextView titleTV = findViewById(R.id.title_detail);
            titleTV.setText(title);
            // Set the short description of the movie
            overview = movie.getmOverview();
            TextView overviewTV = findViewById(R.id.overview_detail);
            overviewTV.setText(overview);
            // Get the poster path for the current movie object and set the poster to the corresponding ImageView
            if ((movie.getPosterPath() != null) && (!movie.getPosterPath().equals("null"))) {
                String posterURL = BASE_POSTER_URL + BIG_POSTER_SIZE + movie.getPosterPath();
                Picasso.with(this).load(posterURL).into((ImageView) findViewById(R.id.poster_detail));
            }
            // Set the release date of the movie
            release = movie.getmReleaseDate();
            TextView releaseTV = findViewById(R.id.release_detail);
            releaseTV.setText(release);
            // Set the ratting of the movie
            rating = movie.getmRating();
            TextView ratingTV = findViewById(R.id.rating_detail);
            ratingTV.setText(rating);
            // Get movie ID
            movieID = movie.getMovieID();
        }

        trailerRecycler = findViewById(R.id.trailer_recyler_view);
        emptyTextView = findViewById(R.id.empty_trailer_view);
        trailerProgressBar = findViewById(R.id.trailer_progress_bar);
        reviewRecycler = findViewById(R.id.reviews_recyler_view);
        reviewProgressBar = findViewById(R.id.reviews_progress_bar);

        tryToConnect();

        // Favorite Button
        favoriteImageView = findViewById(R.id.favorite_image_view);

        if (isFavorite(movieID) != -1) {
            favoriteImageView.setImageResource(R.drawable.ic_star_black_48dp);
        }

        final MovieObject finalMovie = movie;
        favoriteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFavoriteButtonPress(finalMovie);
            }
        });

    }

    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        String urlString;
        if (i == 1) {
            // Create the URL for the query
            urlString = BASE_URL + movieID + VIDEOS_URL + PRE_API_KEY_URL + API_KEY;
            // Set Visibility to relevant views
            trailerProgressBar.setVisibility(View.VISIBLE);
            // Create a new loader for the given URL
            return new TrailerLoader(this, urlString);
        } else if (i == 2) {
            // Create the URL for the query
            urlString = BASE_URL + movieID + REVIEWS_URL + PRE_API_KEY_URL + API_KEY;
            // Set Visibility to relevant views
            reviewProgressBar.setVisibility(View.VISIBLE);
            // Create a new loader for the given URL
            return new ReviewLoader(this, urlString);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        if (loader.getId() == 1) {
            List trailerObjects = (List) data;
            if (trailerObjects != null && !trailerObjects.isEmpty()) {
                // Set Visibility to relevant views
                trailerProgressBar.setVisibility(View.GONE);
                emptyTextView.setVisibility(View.GONE);
                trailerRecycler.setVisibility(View.VISIBLE);

                trailers = trailerObjects;

                // Update adapter
                trailerAdapter = new TrailerAdapter(trailerObjects, this);
                // Set the adapter to that List View
                LinearLayoutManager myLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                trailerRecycler.setLayoutManager(myLayoutManager);
                trailerRecycler.setHasFixedSize(true);
                trailerRecycler.setAdapter(trailerAdapter);


            } else {
                // Set Empty View
                emptyTextView.setText(R.string.no_trailers);
                emptyTextView.setVisibility(View.VISIBLE);
            }
        } else if (loader.getId() == 2) {
            List reviewObjects = (List) data;
            if (reviewObjects != null && !reviewObjects.isEmpty()) {
                // Set Visibility to relevant views
                reviewProgressBar.setVisibility(View.GONE);
                reviewRecycler.setVisibility(View.VISIBLE);
                // Update adapter
                reviewAdapter = new ReviewAdapter(reviewObjects);
                // Set the adapter to that List View
                LinearLayoutManager myLayoutManager = new LinearLayoutManager(this);
                reviewRecycler.setLayoutManager(myLayoutManager);
                reviewRecycler.setHasFixedSize(true);
                reviewRecycler.setAdapter(reviewAdapter);
            } else {
                // Set Empty View
                emptyTextView.setText(R.string.no_trailers);
                emptyTextView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
        // clear adapter
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
            loaderManager.initLoader(TRAILER_LOADER_ID, null, DetailActivity.this);
            loaderManager.restartLoader(TRAILER_LOADER_ID, null, DetailActivity.this);
            loaderManager.initLoader(REVIEW_LOADER_ID, null, DetailActivity.this);
            loaderManager.restartLoader(REVIEW_LOADER_ID, null, DetailActivity.this);

        } else {
            // If there is no internet connection
            // Set the visibility of the recycler view to GONE
            trailerRecycler.setVisibility(View.GONE);
            // Set the text of the empty text view so that it says there is no internet connection
            emptyTextView.setText(R.string.no_internet);
            emptyTextView.setVisibility(View.VISIBLE);
            trailerProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        watchTrailer(trailers.get(clickedItemIndex).getmKey());
    }

    public void watchTrailer(String id) {
        // Create intent to watch video on youtube app and intent to watch in the web browser
        Intent youtubeApp = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent youtubeWebBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + id));
        // Try to launch the youtube app, if it fails, use the browser
        try {
            this.startActivity(youtubeApp);
        } catch (ActivityNotFoundException ex) {
            this.startActivity(youtubeWebBrowser);
        }
    }

    private void onFavoriteButtonPress(MovieObject movie) {
        // Values to be saved in Favorites Database
        ContentValues values = new ContentValues();
        values.put(DbContract.Movie.COLUMN_TITLE, movie.getmTitle());
        values.put(DbContract.Movie.COLUMN_POSTER_PATH, movie.getPosterPath());
        values.put(DbContract.Movie.COLUMN_OVERVIEW, movie.getmOverview());
        values.put(DbContract.Movie.COLUMN_RELEASE_DATE, movie.getmReleaseDate());
        values.put(DbContract.Movie.COLUMN_RATING, movie.getmRating());
        values.put(DbContract.Movie.COLUMN_MOVIE_ID, movie.getMovieID());

        int id_as_favorite = isFavorite(movie.getMovieID());
        if (id_as_favorite != -1) {
            // Delete from favorites
            Uri currentUri = ContentUris.withAppendedId(DbContract.Movie.CONTENT_URI, id_as_favorite);
            int rowsDeleted = getContentResolver().delete(currentUri, null, null);
            if (rowsDeleted == 0) {
                Toast.makeText(this, getString(R.string.failed_to_delete), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.successfully_deleted), Toast.LENGTH_SHORT).show();
                favoriteImageView.setImageResource(R.drawable.ic_star_outline_black_48dp);
            }
        } else {
            // Add to favorites
            Uri newUri = getContentResolver().insert(DbContract.Movie.CONTENT_URI, values);
            if (newUri == null) {
                Toast.makeText(this, getString(R.string.insertion_failed), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.insertion_succeded), Toast.LENGTH_SHORT).show();
                favoriteImageView.setImageResource(R.drawable.ic_star_black_48dp);
            }
        }

    }

    private int isFavorite(String id) {
        String[] projection = {
                DbContract.Movie._ID,
                DbContract.Movie.COLUMN_MOVIE_ID
        };
        Cursor queryResult = getContentResolver().query(DbContract.Movie.CONTENT_URI, projection, "mMovieID = " + id, null, null);
        if (queryResult.getCount() == 0) {
            return -1;
        } else {
            int IdColumnIndex = queryResult.getColumnIndex(DbContract.Movie._ID);
            queryResult.moveToFirst();
            return queryResult.getInt(IdColumnIndex);
        }
    }

}
