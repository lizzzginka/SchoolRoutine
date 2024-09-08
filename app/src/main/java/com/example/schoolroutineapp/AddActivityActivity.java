// AddActivityActivity.java
package com.example.schoolroutineapp;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class AddActivityActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private Button buttonSelectStartTime;
    private Button buttonSelectEndTime;
    private Button buttonSaveActivity;
    private String startTime = "";
    private String endTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editTextTitle = findViewById(R.id.editTextTitle);
        buttonSelectStartTime = findViewById(R.id.buttonSelectStartTime);
        buttonSelectEndTime = findViewById(R.id.buttonSelectEndTime);
        buttonSaveActivity = findViewById(R.id.buttonSaveActivity);

        buttonSelectStartTime.setOnClickListener(v -> showTimePickerDialog(true));
        buttonSelectEndTime.setOnClickListener(v -> showTimePickerDialog(false));

        buttonSaveActivity.setOnClickListener(v -> {
            String title = editTextTitle.getText().toString().trim();

            if (title.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
                Toast.makeText(AddActivityActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            long selectedDateMillis = getIntent().getLongExtra("selectedDate", -1);
            if (selectedDateMillis == -1) {
                Toast.makeText(AddActivityActivity.this, "Дата не выбрана", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent resultIntent = new Intent();
            resultIntent.putExtra("title", title);
            resultIntent.putExtra("startTime", startTime);
            resultIntent.putExtra("endTime", endTime);
            resultIntent.putExtra("selectedDate", selectedDateMillis);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    private void showTimePickerDialog(boolean isStartTime) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddActivityActivity.this,
                (TimePicker view, int hourOfDay, int minuteOfHour) -> {
                    String time = String.format("%02d:%02d", hourOfDay, minuteOfHour);
                    if (isStartTime) {
                        startTime = time;
                        buttonSelectStartTime.setText("Начало: " + time);
                    } else {
                        endTime = time;
                        buttonSelectEndTime.setText("Конец: " + time);
                    }
                }, hour, minute, true);
        timePickerDialog.show();
    }
}
