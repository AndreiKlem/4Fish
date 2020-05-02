package com.example.a4fish;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "event_table")
public class Event {

    @PrimaryKey(autoGenerate = true)
    public int eventId;

    @NonNull
    @ColumnInfo(name = "event_title")
    public String mTitle;

    @NonNull
    @ColumnInfo(name = "event_date")
    public String mEventDate;


    public Event(@NonNull String title, @NonNull String eventDate) {
        this.mTitle = title;
        this.mEventDate = eventDate;
    }

    public int getEventId() {
        return this.eventId;
    }

    public void setEventTitle(String title) {
        this.mTitle = title;
    }

    public String getEventTitle() {
        return this.mTitle;
    }

    public String getEventDate() { return this.mEventDate;}

}
