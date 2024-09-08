// AddActivityActivity.java
package com.example.schoolroutineapp;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class AddActivityActivity extends AppCompatActivity {

    private EditText activityTitleEditText;
    private Button startTimeButton, endTimeButton, saveButton;
    private int startHour, startMinute, endHour, endMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        activityTitleEditText = findViewById(R.id.editTextActivityTitle);
        startTimeButton = findViewById(R.id.buttonStartTime);
        endTimeButton = findViewById(R.id.buttonEndTime);
        saveButton = findViewById(R.id.buttonSaveActivity);

        // Получаем дату из Intent
        long selectedDateMillis = getIntent().getLongExtra("selectedDate", -1);
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.setTimeInMillis(selectedDateMillis);

        startTimeButton.setOnClickListener(v -> showTimePicker(true));
        endTimeButton.setOnClickListener(v -> showTimePicker(false));

        saveButton.setOnClickListener(v -> saveActivity(selectedDate));
    }

    private void showTimePicker(final boolean isStartTime) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (isStartTime) {
                    startHour = hourOfDay;
                    startMinute = minute;
                    startTimeButton.setText(String.format("%02d:%02d", startHour, startMinute));
                } else {
                    endHour = hourOfDay;
                    endMinute = minute;
                    endTimeButton.setText(String.format("%02d:%02d", endHour, endMinute));
                }
            }
        }, hour, minute, true);

        timePickerDialog.show();
    }

    private void saveActivity(Calendar selectedDate) {
        String title = activityTitleEditText.getText().toString().trim();
        if (title.isEmpty()) {
            Toast.makeText(this, "Введите название занятия", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("title", title);
        resultIntent.putExtra("startTime", String.format("%02d:%02d", startHour, startMinute));
        resultIntent.putExtra("endTime", String.format("%02d:%02d", endHour, endMinute));
        resultIntent.putExtra("selectedDate", selectedDate.getTimeInMillis()); // Передаем актуальную дату обратно
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
