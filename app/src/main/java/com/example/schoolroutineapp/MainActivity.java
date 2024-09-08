package com.example.schoolroutineapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_ACTIVITY_REQUEST_CODE = 1;
    private TextView selectedDayText;
    private Button selectDateButton, addActivityButton;
    private RecyclerView recyclerViewActivities;
    private ActivityAdapter activityAdapter;
    private List<ActivityItem> activityList;
    private SharedPreferences sharedPreferences;
    private Calendar selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectedDayText = findViewById(R.id.selectedDayText);
        selectDateButton = findViewById(R.id.selectDateButton);
        addActivityButton = findViewById(R.id.addActivityButton);
        recyclerViewActivities = findViewById(R.id.recyclerViewActivities);

        sharedPreferences = getSharedPreferences("activities", Context.MODE_PRIVATE);
        activityList = new ArrayList<>();

        recyclerViewActivities.setLayoutManager(new LinearLayoutManager(this));
        activityAdapter = new ActivityAdapter(activityList, this::saveActivities);
        recyclerViewActivities.setAdapter(activityAdapter);

        selectedDate = Calendar.getInstance(); // Выбирается текущая дата по умолчанию
        updateSelectedDateText();

        loadActivities(); // Загружаем занятия из SharedPreferences

        selectDateButton.setOnClickListener(v -> showDatePickerDialog());

        addActivityButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddActivityActivity.class);
            intent.putExtra("selectedDate", selectedDate.getTimeInMillis());
            startActivityForResult(intent, ADD_ACTIVITY_REQUEST_CODE);
        });
    }

    private void showDatePickerDialog() {
        DatePickerFragment datePickerFragment = new DatePickerFragment((view, year, month, dayOfMonth) -> {
            // Обновляем дату
            selectedDate.set(year, month, dayOfMonth);

            // Обновляем текст выбранной даты на экране
            updateSelectedDateText();

            // Логируем новую выбранную дату
            System.out.println("Выбранная дата: " + selectedDate.getTime().toString());

            // Очищаем старый список и загружаем активности для новой даты
            loadActivities();
        });
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }



    private void updateSelectedDateText() {
        selectedDayText.setText("Выбранная дата: " + android.text.format.DateFormat.format("dd.MM.yyyy", selectedDate));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String title = data.getStringExtra("title");
            String startTime = data.getStringExtra("startTime");
            String endTime = data.getStringExtra("endTime");
            long dateMillis = data.getLongExtra("selectedDate", -1);

            if (dateMillis == selectedDate.getTimeInMillis()) {
                activityList.add(new ActivityItem(title, startTime, endTime, selectedDate));
                activityAdapter.notifyDataSetChanged();
                saveActivities();
            }
        }
    }

    private void saveActivities() {
        Gson gson = new Gson();
        String json = gson.toJson(activityList);

        // Сохраняем данные по ключу, уникальному для выбранной даты
        sharedPreferences.edit().putString(getDateKey(), json).apply();

        // Логируем процесс сохранения
        System.out.println("Сохраняю данные по ключу: " + getDateKey());
    }



    private void loadActivities() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(getDateKey(), null);

        // Логируем ключ, чтобы убедиться, что загружаются данные для правильной даты
        System.out.println("Загружаю данные по ключу: " + getDateKey());

        Type type = new TypeToken<List<ActivityItem>>() {}.getType();
        List<ActivityItem> savedActivities = gson.fromJson(json, type);

        // Очищаем список занятий перед загрузкой новых
        activityList.clear();

        if (savedActivities != null) {
            activityList.addAll(savedActivities);
        }
        // Сообщаем адаптеру об изменениях
        activityAdapter.notifyDataSetChanged();
    }

    private String getDateKey() {
        // Форматируем дату в строку формата ddMMyyyy (день, месяц, год)
        String key = android.text.format.DateFormat.format("ddMMyyyy", selectedDate).toString();
        // Логируем ключ, чтобы убедиться, что он меняется для каждой даты
        System.out.println("Текущий ключ для даты: " + key);
        return key;
    }

}
