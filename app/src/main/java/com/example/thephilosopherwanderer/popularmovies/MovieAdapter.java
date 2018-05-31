package com.example.thephilosopherwanderer.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by alex on 04.03.2018.
 */

public class MovieAdapter extends ArrayAdapter<MovieObject> {
    // Constants
    private String BASE_POSTER_URL = "http://image.tmdb.org/t/p/";
    private String BASE_POSTER_IMAGE_SIZE = "w185/";
    private String BIG_POSTER_SIZE = "w342/";

    // Constructor
    MovieAdapter(Context context, List<MovieObject> movies) {
        super(context, 0, movies);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Check if an existing view is being reused and, if not, inflate the view
        View movieGridView = convertView;
        if (movieGridView == null) {
            movieGridView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_layout, parent, false);
        }
        // Get the movie at the current position
        final MovieObject currentMovie = getItem(position);

        // Find the TextView in the movie_layout.xml that will contain the title of the movie
        TextView title = movieGridView.findViewById(R.id.text_movie_layout);

        // Get the title from the current movie object and set it to the corresponding textView
        if (currentMovie != null) {
            title.setText(currentMovie.getmTitle());
        }

        // Get the poster path for the current movie object and set the poster to the corresponding ImageView

        if ((currentMovie != null) && (currentMovie.getPosterPath() != null) && (!currentMovie.getPosterPath().equals("null"))) {
            String posterURL = BASE_POSTER_URL + BASE_POSTER_IMAGE_SIZE + currentMovie.getPosterPath();
            Picasso.with(getContext()).load(posterURL).into((ImageView) movieGridView.findViewById(R.id.poster_movie_layout));
        } else movieGridView.findViewById(R.id.poster_movie_layout).setVisibility(View.GONE);

        // Find the Linear Layout that contains the movie
        LinearLayout movie = movieGridView.findViewById(R.id.movie_layout);
        movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToDetail = new Intent(getContext(), DetailActivity.class);
                goToDetail.putExtra("title", currentMovie.getmTitle());
                goToDetail.putExtra("overview", currentMovie.getmOverview());
                goToDetail.putExtra("poster", BASE_POSTER_URL + BIG_POSTER_SIZE + currentMovie.getPosterPath());
                goToDetail.putExtra("release", currentMovie.getmReleaseDate());
                goToDetail.putExtra("rating", currentMovie.getmRating());
                goToDetail.putExtra("id", currentMovie.getMovieID());
                getContext().startActivity(goToDetail);
            }
        });

        return movieGridView;
    }
}
