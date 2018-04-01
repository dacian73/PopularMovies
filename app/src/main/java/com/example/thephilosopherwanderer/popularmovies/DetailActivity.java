package com.example.thephilosopherwanderer.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    String title;
    String overview;
    String release;
    String rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle data = getIntent().getExtras();
        if (data != null) {
            // Set the title of the movie
            title = data.getString("title");
            TextView titleTV = findViewById(R.id.title_detail);
            titleTV.setText(title);
            // Set the short description of the movie
            overview = data.getString("overview");
            TextView overviewTV = findViewById(R.id.overview_detail);
            overviewTV.setText(overview);
            // Set the poster of the movie
            Picasso.with(this).load(data.getString("poster")).into((ImageView) findViewById(R.id.poster_detail));
            // Set the release date of the movie
            release = data.getString("release");
            TextView releaseTV = findViewById(R.id.release_detail);
            releaseTV.setText(release);
            // Set the ratting of the movie
            rating = data.getString("rating");
            TextView ratingTV = findViewById(R.id.rating_detail);
            ratingTV.setText(rating);

        }
    }
}
