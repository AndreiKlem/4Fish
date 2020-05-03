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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements EventListAdapter.OnEventClickListener {

    private static final String TAG = "MainActivity";
    FloatingActionButton addNewEvent;
    private EventViewModel mEventViewModel;
    public static final int NEW_EVENT_ACTIVITY_REQUEST_CODE = 1;

    DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());;
    String stringDate = dateFormat.format(new Date());

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
                Date currentDate = selectedDate.getTime();
                stringDate = dateFormat.format(currentDate);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.events_recycler_view);
        final EventListAdapter adapter = new EventListAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //recyclerView.g

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
            Event event = new Event(data.getStringExtra(AddNewEventActivity.EXTRA_REPLY), data.getStringExtra("selected_date"));
            mEventViewModel.insert(event);
            Toast.makeText(getApplicationContext(), "Recieved date: " + data.getStringExtra("selected_date"), Toast.LENGTH_LONG).show();
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
}
