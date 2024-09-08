package com.example.schoolroutineapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView selectedDayText;
    private Button selectDateButton;
    private Button addActivityButton;
    private Calendar selectedDate;
    private ActivityAdapter activityAdapter;

    // Хранение списка занятий для каждой даты
    private HashMap<String, List<ActivityItem>> activityMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectedDayText = findViewById(R.id.selectedDayText);
        selectDateButton = findViewById(R.id.selectDateButton);
        addActivityButton = findViewById(R.id.addActivityButton);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewActivities);
        activityAdapter = new ActivityAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(activityAdapter);

        selectDateButton.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                    (view, year1, month1, dayOfMonth) -> {
                        selectedDate = Calendar.getInstance();
                        selectedDate.set(year1, month1, dayOfMonth);
                        String selectedDateString = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                        selectedDayText.setText("Selected Date: " + selectedDateString);

                        // Обновляем список занятий для выбранной даты
                        updateActivityListForSelectedDate(selectedDateString);

                    }, year, month, day);
            datePickerDialog.show();
        });

        addActivityButton.setOnClickListener(v -> {
            if (selectedDate == null) {
                Toast.makeText(MainActivity.this, "Выберите дату", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(MainActivity.this, AddActivityActivity.class);
            intent.putExtra("selectedDate", selectedDate.getTimeInMillis()); // Передаем дату в миллисекундах
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String title = data.getStringExtra("title");
            String startTime = data.getStringExtra("startTime");
            String endTime = data.getStringExtra("endTime");

            long dateMillis = data.getLongExtra("selectedDate", -1);
            Calendar date = Calendar.getInstance();
            date.setTimeInMillis(dateMillis);
            String selectedDateString = date.get(Calendar.DAY_OF_MONTH) + "/" +
                    (date.get(Calendar.MONTH) + 1) + "/" +
                    date.get(Calendar.YEAR);

            // Добавляем новое занятие для конкретной даты
            ActivityItem activityItem = new ActivityItem(title, startTime, endTime, date);
            if (!activityMap.containsKey(selectedDateString)) {
                activityMap.put(selectedDateString, new ArrayList<>());
            }
            activityMap.get(selectedDateString).add(activityItem);

            // Обновляем отображаемый список
            updateActivityListForSelectedDate(selectedDateString);
        }
    }

    private void updateActivityListForSelectedDate(String selectedDateString) {
        // Получаем занятия для выбранной даты
        List<ActivityItem> activityItemsForDate = activityMap.getOrDefault(selectedDateString, new ArrayList<>());
        activityAdapter.updateActivityList(activityItemsForDate);
    }
}
