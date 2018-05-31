package com.example.thephilosopherwanderer.popularmovies;

/**
 * Created by alex on 17.03.2018.
 */


public class ReviewObject {
    // Declaring variables
    private String mAuthor;
    private String mContent;


    // Constructor
    ReviewObject(String author, String content) {
        mAuthor = author;
        mContent = content;
    }

    // Getter methods
    String getmAuthor() {
        return mAuthor;
    }

    // Setter methods
    public void setmAuthor(String author) {
        mAuthor = author;
    }

    String getmContent() {
        return mContent;
    }

    public void setmContent(String content) {
        mContent = content;
    }

}


