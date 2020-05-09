package com.example.a4fish;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity(tableName = "event_table")
public class Event {

    @PrimaryKey(autoGenerate = true)
    int eventId;

    @NonNull
    @ColumnInfo(name = "event_title")
    String mTitle;

    @ColumnInfo(name = "event_date")
    long mEventDate;

    @ColumnInfo(name = "event_day")
    int mEventDay;

    @ColumnInfo(name = "event_month")
    int mEventMonth;

    @ColumnInfo(name = "event_year")
    int mEventYear;

    @ColumnInfo(name = "show_time")
    boolean mTimeSpecified;

    public Event(@NonNull String title, long eventDate, int eventDay, int eventMonth, int eventYear, boolean timeSpecified) {
        this.mTitle = title;
        this.mEventDate = eventDate;
        this.mEventDay = eventDay;
        this.mEventMonth = eventMonth;
        this.mEventYear = eventYear;
        this.mTimeSpecified = timeSpecified;
    }

    public String getEventTitle() {
        return this.mTitle;
    }

    public String getEventTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH : mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mEventDate);
        return dateFormat.format(calendar.getTime());
    }

    public boolean isTimeSpecified() {
        return mTimeSpecified;
    }
}
