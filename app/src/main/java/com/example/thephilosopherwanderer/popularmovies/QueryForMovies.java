package com.example.thephilosopherwanderer.popularmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by alex on 18.03.2018.
 */

class QueryForMovies {
    private static final int READ_TIMEOUT = 10000;
    private static final int CONNECT_TIMEOUT = 15000;

    // Empty private constructor
    private QueryForMovies() {
    }

    static ArrayList<MovieObject> returnMovie(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            // Perform HTTP request
            jsonResponse = httpRequest(url);
        } catch (IOException e) {
            // HTTP ERROR
        }
        return extractMovie(jsonResponse);
    }

    // Create URL from string
    private static URL createUrl(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            // Error creating the url
            Log.e(QueryForMovies.class.getSimpleName(), "An error creating the url.", e);
        }
        return url;
    }

    private static String httpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return empty String for JSONResponse
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(READ_TIMEOUT);
            urlConnection.setConnectTimeout(CONNECT_TIMEOUT);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read and parse the response if the response code was 200
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = jsonToStringFromStream(inputStream);
            } else {
                Log.e(QueryForMovies.class.getSimpleName(), "HTTP error code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(QueryForMovies.class.getSimpleName(), "An error occurred when trying to retrieve the json response.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    // Create a String with the whole JSON response
    private static String jsonToStringFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    // Parsing the JSON response
    private static ArrayList<MovieObject> extractMovie(String JSONResponse) {
        // Create an empty ArrayList
        ArrayList<MovieObject> movies = new ArrayList<>();

        // Try to parse the JSON response
        try {

            // Create JSONObject
            JSONObject baseJsonResponse = new JSONObject(JSONResponse);

            // Extract the JSONArray "results"
            JSONArray moviesArray = baseJsonResponse.getJSONArray("results");

            // For each item create a JSONObject
            for (int i = 0; i < moviesArray.length(); i++) {

                // Create a JSON Object for each movie
                JSONObject currentMovie = moviesArray.getJSONObject(i);
                // Extract the title
                String title = currentMovie.getString("original_title");
                // Extract poster path
                String posterPath = currentMovie.getString("poster_path");
                // Extract overview
                String overview = currentMovie.getString("overview");
                // Extract release date
                String releaseDate = currentMovie.getString("release_date");
                // Extract rating
                String rating = currentMovie.getString("vote_average");
                // Extract Id
                String id = currentMovie.getString("id");

                // Create a new movie object
                MovieObject movie = new MovieObject(title, posterPath, overview, releaseDate, rating, id);

                // Add the current movie to the movies array
                movies.add(movie);
            }

        } catch (JSONException e) {
            Log.e(QueryForMovies.class.getSimpleName(), "An error occured while parsing.", e);
        }
        return movies;
    }
}
