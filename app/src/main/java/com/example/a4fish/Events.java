package com.example.a4fish;

public class Events {

    private int eventId;
    private String mTitle;
    private String mDescription;

    public Events(String title, String description) {
        mTitle = title;
        mDescription = description;
    }

    public void setEventId(int id) {
        eventId = id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getEventTitle() {
        return mTitle;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getEventDescription() {
        return mDescription;
    }

}
