package com.example.codsoft;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class NewTask extends AppCompatActivity {

    EditText title,description;
    Button date,time,addTask;
    String timeText,dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        addTask =findViewById(R.id.addTask);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDateDialog();
            }
        });
        
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimeDialog();
            }
        });

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String taskTitle = title.getText().toString();
                String taskDescription = description.getText().toString();

                if(taskTitle.isEmpty() || dateText == null || timeText == null) {
                    Toast.makeText(NewTask.this, "Please enter title, date, and time.", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    Task newTask = new Task(taskTitle, taskDescription, dateText, timeText);
                    Gson gson = new Gson();
                    SharedPreferences sharedPreferences = getSharedPreferences(ToDoListMainActivity.PREFS_NAME, MODE_PRIVATE);
                    String jsonTasks = sharedPreferences.getString("tasks", null);

                    ArrayList<Task> tasks;
                    if (jsonTasks != null) {
                        Type type = new TypeToken<ArrayList<Task>>(){}.getType();
                        tasks = gson.fromJson(jsonTasks, type);
                    } else {
                        tasks = new ArrayList<>();
                    }

                    tasks.add(newTask);
                    String json = gson.toJson(tasks);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("tasks", json);
                    editor.apply();
                }
                finish();
            }
        });
    }

    private void openTimeDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String newTime = hourOfDay+":" + minute;
                time.setText(newTime);
                timeText = newTime;
            }
        },6, 37, false);
        timePickerDialog.show();
    }

    private void openDateDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

//                String newDate = dayOfMonth +"/" +(month+1) + "/" + year;
//                date.setText(newDate);
//                dateText = newDate;

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                String newDate = new SimpleDateFormat("dd MMM yy").format(calendar.getTime());
                date.setText(newDate);
                dateText = newDate;
            }
        }, 2023, 12, 16);
        datePickerDialog.show();
    }
}