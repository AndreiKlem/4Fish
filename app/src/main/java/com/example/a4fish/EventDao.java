package com.example.a4fish;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EventDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Event event);

    @Query("SELECT * FROM event_table")
    LiveData<List<Event>> getAllEvents();

    @Query("SELECT event_day FROM event_table WHERE event_year = :year AND event_month = :month")
    LiveData<List<Integer>> getEvents(int year, int month);
}
