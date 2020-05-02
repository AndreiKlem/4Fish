package com.example.a4fish;

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

}
