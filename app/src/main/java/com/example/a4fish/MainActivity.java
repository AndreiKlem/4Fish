package com.example.a4fish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView eventsRecycleView;
    RecyclerView.Adapter eventsAdapter;
    ArrayList<String> events;
    FloatingActionButton addNewEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventsRecycleView = findViewById(R.id.events_recycler_view);
        eventsRecycleView.setLayoutManager(new LinearLayoutManager(this));

        events = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            events.add("New event # " + i);
        }
        eventsAdapter = new EventsAdapter(events);
        eventsRecycleView.setAdapter(eventsAdapter);

        addNewEvent = findViewById(R.id.addNewEvent);
        addNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewEventActivity.class);
                startActivity(intent);
            }
        });

    }


}
