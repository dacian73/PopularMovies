package com.example.thephilosopherwanderer.popularmovies;

/**
 * Created by alex on 17.03.2018.
 */


public class TrailerObject {
    // Declaring variables
    private String mName;
    private String mKey;


    // Constructor
    TrailerObject(String name, String key) {
        mName = name;
        mKey = key;
    }

    // Getter methods
    String getmName() {
        return mName;
    }

    // Setter methods
    public void setmName(String title) {
        mName = title;
    }

    String getmKey() {
        return mKey;
    }

    public void setmKey(String key) {
        mKey = key;
    }

}


