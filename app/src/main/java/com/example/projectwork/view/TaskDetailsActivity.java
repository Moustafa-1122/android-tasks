package com.example.projectwork.view;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;


import androidx.appcompat.app.AppCompatActivity;

import com.example.projectwork.R;
import com.example.projectwork.model.Task;
import com.google.gson.Gson;

public class TaskDetailsActivity extends AppCompatActivity {

    TextView tvTaskId, tvUserId, tvTitle, tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the back button in the toolbar ←
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Task Details");

        tvTaskId = findViewById(R.id.tvTaskId);
        tvUserId = findViewById(R.id.tvUserId);
        tvTitle = findViewById(R.id.tvTitle);
        tvStatus = findViewById(R.id.tvStatus);

        // Get the task from the intent
        String json = getIntent().getStringExtra("task");

        if (json != null) {
            Task task = new Gson().fromJson(json, Task.class);

            getSupportActionBar().setTitle("Task #" + task.id);//change the title of the activity to the task id

            tvTaskId.setText("Task ID: " + task.id);
            tvUserId.setText("User ID: " + task.userId);
            tvTitle.setText("Title: " + task.title);
            tvStatus.setText(
                    "Status: " + (task.completed ? "Completed ✅" : "Not Completed ❌")
            );
        }
    }
    public boolean onSupportNavigateUp() {
        finish(); //back to the previous activity
        return true;
    }
}
