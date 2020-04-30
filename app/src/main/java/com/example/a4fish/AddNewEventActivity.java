package com.example.a4fish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Dao;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddNewEventActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    EditText titleEditText;
    Button createEventButton;
    String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_event);

        Intent intent = getIntent();
        selectedDate = intent.getStringExtra("date");

        titleEditText = findViewById(R.id.title_edit_text);
        createEventButton = findViewById(R.id.create_event_button);
        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(titleEditText.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String title = titleEditText.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, title);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

        TextView selectedDateTextView = findViewById(R.id.selected_date_text_view);
        selectedDateTextView.setText("Selected date: " + selectedDate);
    }
}
