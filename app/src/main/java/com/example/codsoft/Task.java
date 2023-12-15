package com.example.codsoft;

public class Task {
    public String title;
    public String description;
    public String date;
    public String time;
    public int radioButton;
    public Task(String title, String description, String date, String time, int radioButton) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.radioButton = radioButton;
    }

    // Constructor without description (making description optional)
    public Task(String title, String date, String time, int radioButton) {
        this(title, "", date, time, radioButton); // Assigns an empty string or null to description
    }
}
