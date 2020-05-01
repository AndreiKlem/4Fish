package com.example.a4fish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddNewEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    EditText titleEditText;
    Button createEventButton;
    String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_event);

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

        Intent intent = getIntent();
        selectedDate = intent.getStringExtra("date");
        showSelectedDate(selectedDate);

        LinearLayout datePickerLinearLayout = findViewById(R.id.date_picker_linear_layout);
        datePickerLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date_picker");
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, dayOfMonth);
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        Date date = c.getTime();
        selectedDate = dateFormat.format(date);
        showSelectedDate(selectedDate);
    }

    public void showSelectedDate(String dateString) {
        TextView selectedDateTextView = findViewById(R.id.current_date_text_view);
        selectedDateTextView.setText(dateString);
    }
}
