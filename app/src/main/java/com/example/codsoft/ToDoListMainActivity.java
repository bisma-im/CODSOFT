package com.example.codsoft;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ToDoListMainActivity extends AppCompatActivity {
    EditText name;
    Button register;
    public static String PREFS_NAME = "ToDoListSP";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list_main);

        name = findViewById(R.id.editTextText);
        register = findViewById(R.id.button);

        SharedPreferences sp = getSharedPreferences(PREFS_NAME, 0);
        boolean hasRegistered = sp.getBoolean("hasRegistered", false);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("hasRegistered", true);
                editor.commit();

                startActivity(new Intent(ToDoListMainActivity.this, HomePage.class));
            }
        });

        if(hasRegistered){
            Intent intent = new Intent(ToDoListMainActivity.this, HomePage.class);
            startActivity(intent);
            finish();
        }
    }
}