package com.example.codsoft;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class TaskView extends AppCompatActivity {
    EditText editTitle, editDescription;
    Button done;
    ImageButton delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);
        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        done = findViewById(R.id.done);
        delete = findViewById(R.id.delete);
        String title = getIntent().getExtras().getString("title");
        String description = getIntent().getExtras().getString("description");
        final int position = getIntent().getExtras().getInt("position", -1);

        editTitle.setText(title);
        if(description.isEmpty()){
            editDescription.setHint("Description");
        }
        else{
            editDescription.setText(description);
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position != -1) {
                    deleteTask(position);
                }
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(ToDoListMainActivity.PREFS_NAME, MODE_PRIVATE);
                String jsonTasks = sharedPreferences.getString("tasks", null);
                Gson gson = new Gson();

                if (jsonTasks != null) {
                    Type type = new TypeToken<ArrayList<Task>>(){}.getType();
                    ArrayList<Task> tasks = gson.fromJson(jsonTasks, type);

                    Task task = tasks.get(position);
                    task.setTitle(editTitle.getText().toString());
                    task.setDescription(editDescription.getText().toString());

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String updatedJsonTasks = gson.toJson(tasks);
                    editor.putString("tasks", updatedJsonTasks);
                    editor.apply();

                    finish();
                }
            }
        });
    }
    private void deleteTask(int position) {
        SharedPreferences sharedPreferences = getSharedPreferences(ToDoListMainActivity.PREFS_NAME, MODE_PRIVATE);
        String jsonTasks = sharedPreferences.getString("tasks", null);
        Gson gson = new Gson();

        if (jsonTasks != null) {
            Type type = new TypeToken<ArrayList<Task>>(){}.getType();
            ArrayList<Task> tasks = gson.fromJson(jsonTasks, type);

            tasks.remove(position);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            String updatedJsonTasks = gson.toJson(tasks);
            editor.putString("tasks", updatedJsonTasks);
            editor.apply();

            finish();
        }
    }
}