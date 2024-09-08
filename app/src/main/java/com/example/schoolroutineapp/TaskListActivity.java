//// TaskListActivity.java
//package com.example.schoolroutineapp;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import java.util.ArrayList;
//import java.util.List;
//
//public class TaskListActivity extends AppCompatActivity {
//
//    private TaskAdapter taskAdapter;
//    private List<Task> taskList;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_task_list);
//
//        taskList = new ArrayList<>();
//        taskAdapter = new TaskAdapter(taskList);
//
//        RecyclerView recyclerView = findViewById(R.id.recyclerViewTasks);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(taskAdapter);
//
//        Button addTaskButton = findViewById(R.id.buttonAddTask);
//        addTaskButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addNewTask();
//            }
//        });
//    }
//
//    private void addNewTask() {
//        // Логика добавления нового задания
//        // Можем показать диалог или новое Activity для добавления задания
//        // Например, вызов метода для показа диалога:
//        AddTaskDialogFragment dialog = new AddTaskDialogFragment();
//        dialog.show(getSupportFragmentManager(), "AddTaskDialog");
//    }
//}
