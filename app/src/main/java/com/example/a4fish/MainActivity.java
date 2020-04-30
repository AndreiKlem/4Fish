package com.example.a4fish;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addNewEvent;
    private EventViewModel mEventViewModel;
    public static final int NEW_EVENT_ACTIVITY_REQUEST_CODE = 1;

    String stringDate = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarView calendarView = findViewById(R.id.calendar_view);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth);
                Date currentDate;
                currentDate = selectedDate.getTime();
                DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
                stringDate = dateFormat.format(currentDate);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.events_recycler_view);
        final EventListAdapter adapter = new EventListAdapter(this);
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
            Event event = new Event(data.getStringExtra(AddNewEventActivity.EXTRA_REPLY));
            mEventViewModel.insert(event);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

}
