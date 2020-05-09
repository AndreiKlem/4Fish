package com.example.a4fish;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity implements EventListAdapter.OnEventClickListener {

    private static final String TAG = "MainActivity";
    FloatingActionButton addNewEvent;
    private EventViewModel mEventViewModel;
    public static final int NEW_EVENT_ACTIVITY_REQUEST_CODE = 1;
    private static String CHANNEL_ID = "4Fish";

    public long eventId;
    Calendar selectedDate = Calendar.getInstance();
    Calendar currentMonth = Calendar.getInstance();
    CalendarView customCalendarView;
    boolean showTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.events_recycler_view);
        final EventListAdapter adapter = new EventListAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mEventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        mEventViewModel.getAllEvents().observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(@Nullable final List<Event> events) {
                // Update the cached copy of the events in the adapter.
                adapter.setEvents(events);
            }
        });

        customCalendarView = findViewById(R.id.calendar_view);
        updateEvents();

        ImageView nextButton = findViewById(R.id.calendar_next_button);
        ImageView prevButton = findViewById(R.id.calendar_prev_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMonth.add(Calendar.MONTH, 1);
                updateEvents();
            }
        });
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMonth.add(Calendar.MONTH, -1);
                updateEvents();
            }
        });

        addNewEvent = findViewById(R.id.addNewEvent);
        addNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewEventActivity.class);
                long dateTransfer = selectedDate.getTimeInMillis();
                intent.putExtra("selected_date_calendar_view", dateTransfer);
                startActivityForResult(intent, NEW_EVENT_ACTIVITY_REQUEST_CODE);
            }
        });

    }

    private void updateEvents() {
        mEventViewModel.getEvents(currentMonth.get(Calendar.YEAR), currentMonth.get(Calendar.MONTH)).observe(this, new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> events) {
                HashSet<Integer> mEvents = new HashSet<>();
                mEvents.addAll(events);
                Date date = currentMonth.getTime();
                customCalendarView.updateCalendar(mEvents, date);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_EVENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            long dateFromIntent = data.getLongExtra("selected_date", -1);
            showTime = data.getBooleanExtra("time_flag", false);
            String tempTitle = data.getStringExtra(AddNewEventActivity.EXTRA_REPLY);
            selectedDate.setTimeInMillis(dateFromIntent);
            int eventDay = selectedDate.get(Calendar.DAY_OF_MONTH);
            int eventMonth = selectedDate.get(Calendar.MONTH);
            int eventYear = selectedDate.get(Calendar.YEAR);

            // Insert new event in database
            Event event = new Event(tempTitle, dateFromIntent, eventDay, eventMonth, eventYear, showTime);
            mEventViewModel.insert(event);
            updateEvents();

            // set alarm message
            if (data.getBooleanExtra("notification_flag", false)) {

                // create intent with actual parameters
                Intent intent = new Intent(getApplicationContext(), EventBroadcast.class);
                intent.putExtra("event_title", tempTitle);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                // create and set message
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, eventId, pendingIntent);

            }
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onEventClick(int position) {
        Log.d(TAG, "onEventClick: " + position);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.calendar_prev_button:
//                currentMonth.add(Calendar.MONTH, -1);
//                updateEvents();
//                break;
//            case  R.id.calendar_next_button:
//                currentMonth.add(Calendar.MONTH, 1);
//                updateEvents();
//                break;
//        }
//    }
}
