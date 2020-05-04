package com.example.a4fish;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EventViewModel extends AndroidViewModel {

    private EventRepository mRepository;
    private LiveData<List<Event>> mAllEvents;

    public EventViewModel(@NonNull Application application) {
        super(application);
        mRepository = new EventRepository(application);
        mAllEvents = mRepository.getAllEvents();
    }

    LiveData<List<Event>> getAllEvents() { return mAllEvents; }

    public void insert(Event event) {
        mRepository.insert(event);
    }
}
