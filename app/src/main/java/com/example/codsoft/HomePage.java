package com.example.codsoft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
    Button addNewTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        addNewTask = findViewById(R.id.addNewTask);

        addNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, NewTask.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadTasks();
    }
    private void loadTasks() {
        SharedPreferences sharedPreferences = getSharedPreferences(ToDoListMainActivity.PREFS_NAME, MODE_PRIVATE);
        String jsonTasks = sharedPreferences.getString("tasks", null);

        ArrayList<Task> tasks;
        if (jsonTasks != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Task>>(){}.getType();
            tasks = gson.fromJson(jsonTasks, type);
        } else {
            tasks = new ArrayList<>();
        }
        TaskAdapter adapter = new TaskAdapter(this, tasks);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}