package com.example.a4fish;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

class EventRepository {
    private EventDao mEventDao;
    private LiveData<List<Event>> mAllEvents;

    EventRepository(Application application) {
        EventRoomDatabase db = EventRoomDatabase.getDatabase(application);
        mEventDao = db.eventDao();
        mAllEvents = mEventDao.getAllEvents();
    }

    LiveData<List<Event>> getAllEvents() {
        return mAllEvents;
    }

    LiveData<List<Integer>> getEvents(int year, int month) {
        return mEventDao.getEvents(year, month);
    }

    void insert(Event event) {
        EventRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mEventDao.insert(event);
            }
        });
    }
}