package com.example.codsoft;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {
    public TaskAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task task = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_cell, parent, false);
        }
        TextView tvTitle = convertView.findViewById(R.id.cellTitle);
        TextView tvDate = convertView.findViewById(R.id.cellDate);
        TextView tvTime = convertView.findViewById(R.id.cellTime);
        CheckBox checkBox = convertView.findViewById(R.id.checkBox);

        // Populate the data into the template view using the data object
        tvTitle.setText(task.getTitle());
        tvDate.setText(task.getDate());
        tvTime.setText(task.getTime());
        checkBox.setOnCheckedChangeListener(null);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                task.setCompletionStatus(isChecked);
            }
        });
        return convertView;
    }
}
