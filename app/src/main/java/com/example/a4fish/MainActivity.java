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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements EventListAdapter.OnEventClickListener {

    private static final String TAG = "MainActivity";
    FloatingActionButton addNewEvent;
    private EventViewModel mEventViewModel;
    public static final int NEW_EVENT_ACTIVITY_REQUEST_CODE = 1;
    private static String CHANNEL_ID = "4Fish";

    DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());;
    String stringDate = dateFormat.format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();
        setContentView(R.layout.activity_main);

        HashSet<Date> events = new HashSet<>();
        events.add(new Date());
        for (Date temp : events) {
            Log.i(TAG, "temp: " + temp);
            Log.i(TAG, "Done with temp");
        }

        CalendarView customCalendarView = findViewById(R.id.calendar_view);
        customCalendarView.updateCalendar(events);
        for (Date temp2 : events) {
            Log.i(TAG, "temp2: " + temp2);
            Log.i(TAG, "Done with temp2");
        }

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

        addNewEvent = findViewById(R.id.addNewEvent);
        addNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewEventActivity.class);
                intent.putExtra("date", stringDate);
                startActivityForResult(intent, NEW_EVENT_ACTIVITY_REQUEST_CODE);
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_EVENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String dateFromIntent = data.getStringExtra("selected_date");
            String timeFromIntent = data.getStringExtra("selected_time");
            String formatPattern = dateFromIntent + timeFromIntent + "00";
            String tempTitle = data.getStringExtra(AddNewEventActivity.EXTRA_REPLY);
            Event event = new Event(tempTitle, dateFromIntent);
            mEventViewModel.insert(event);
            if (data.getBooleanExtra("notification_flag", false)) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyyHH:mmss");
                try {
                    Date date = format.parse(formatPattern);
                    calendar.setTime(date);
                    Log.v("onActivityResult", "calendar:   " + calendar.getTime().toString());

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getApplicationContext(), EventBroadcast.class);
                intent.putExtra("event_title", tempTitle);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

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
}
