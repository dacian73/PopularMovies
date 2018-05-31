package com.example.thephilosopherwanderer.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by alex on 04.03.2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.myHolder> {

    private List<ReviewObject> reviewObjects;

    // Constructor
    ReviewAdapter(List<ReviewObject> reviewObjects) {
        this.reviewObjects = reviewObjects;
    }

    @Override
    public ReviewAdapter.myHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Override the onCreateViewHolder to inflate the view and return a viewHolder with that inflated view
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_layout, parent, false);
        return new ReviewAdapter.myHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.myHolder holder, int position) {
        // Override onBindViewHolder to call the bind method
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        // Return the number of reviews
        return reviewObjects.size();
    }

    // View holder class
    class myHolder extends RecyclerView.ViewHolder {
        // Make variables for views that will contain data
        TextView authorTextView;
        TextView contentTextView;

        // Constructor
        myHolder(View itemView) {
            super(itemView);
            // Initialize the variables for views with findViewById
            authorTextView = itemView.findViewById(R.id.review_author);
            contentTextView = itemView.findViewById(R.id.review_content);
        }

        // Set data to views
        void bind(int listIndex) {
            authorTextView.setText(reviewObjects.get(listIndex).getmAuthor());
            contentTextView.setText(reviewObjects.get(listIndex).getmContent());
        }
    }
}
