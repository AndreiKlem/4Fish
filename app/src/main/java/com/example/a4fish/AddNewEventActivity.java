package com.example.a4fish;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class AddNewEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    EditText titleEditText;
    Button createEventButton;
    Calendar selectedDate = Calendar.getInstance();
    boolean notificationFlag = false;
    boolean showTimeFlag = false;

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
                    long passDate = selectedDate.getTimeInMillis();
                    replyIntent.putExtra("selected_date", passDate);
                    replyIntent.putExtra("notification_flag", notificationFlag);
                    replyIntent.putExtra("time_flag", showTimeFlag);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

        Intent intent = getIntent();
        long dateTransfer = intent.getLongExtra("selected_date_calendar_view", -1);
        selectedDate.setTimeInMillis(dateTransfer);
        showSelectedDate();

        LinearLayout datePickerLinearLayout = findViewById(R.id.date_picker_linear_layout);
        datePickerLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date_picker");
            }
        });

        LinearLayout timeLinearLayout = findViewById(R.id.time_picker_linear_layout);
        timeLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(getSupportFragmentManager(), "time picker");
            }
        });

        CheckBox notificationCheckBox = findViewById(R.id.notification_check_box);
        notificationCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notificationCheckBox.isChecked()) {
                    notificationCheckBox.setButtonDrawable(R.drawable.baseline_notifications_active_black_24);
                    notificationFlag = true;
                }
                else {
                    notificationCheckBox.setButtonDrawable(R.drawable.baseline_notifications_off_black_24);
                    notificationFlag = false;
                }
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        selectedDate.set(Calendar.YEAR, year);
        selectedDate.set(Calendar.MONTH, month);
        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        showSelectedDate();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        selectedDate.set(Calendar.HOUR, hourOfDay);
        selectedDate.set(Calendar.MINUTE, minute);
        showSelectedTime();
        showTimeFlag = true;
    }

    public void showSelectedDate() {
        TextView selectedDateTextView = findViewById(R.id.current_date_text_view);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        selectedDateTextView.setText(dateFormat.format(selectedDate.getTime()));
    }

    public void showSelectedTime() {
        TextView timeTextView = findViewById(R.id.time_text_view);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH : mm");
        timeTextView.setText(timeFormat.format(selectedDate.getTime()));
    }

}
