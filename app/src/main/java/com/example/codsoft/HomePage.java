package com.example.codsoft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
    ArrayList<Task> tasks = new ArrayList<>();
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

        if (jsonTasks != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Task>>(){}.getType();
            tasks = gson.fromJson(jsonTasks, type);
        }
        TaskAdapter adapter = new TaskAdapter(this, tasks);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Listview", "Error message");
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                bundle.putString("title", tasks.get(position).getTitle());
                bundle.putString("description", tasks.get(position).getDescription());
                Intent intent = new Intent(HomePage.this, TaskView.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}