package com.example.a4fish;

public class Events {

    private String mTitle, mDescription;

    public Events(String title, String description) {
        mTitle = title;
        mDescription = description;
    }

    public String getEventTitle() {
        return mTitle;
    }

    public String getEventDescription() {
        return mDescription;
    }

}
