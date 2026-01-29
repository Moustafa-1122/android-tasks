package com.example.projectwork.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectwork.R;
import com.example.projectwork.model.Task;
import com.example.projectwork.view.TaskDetailsActivity;
import com.google.gson.Gson;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskVH> {

    List<Task> list;
    Context context;

    public TasksAdapter(Context context, List<Task> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TaskVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_task, parent, false);
        return new TaskVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskVH holder, int position) {
        Task task = list.get(position);

        holder.title.setText(task.title);
        holder.status.setText(task.completed ? "Completed" : "Not completed");

        // لون حسب الحالة
        holder.itemView.setBackgroundColor(
                task.completed ? Color.parseColor("#C8E6C9")
                        : Color.parseColor("#FFCDD2")
        );

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, TaskDetailsActivity.class);
            i.putExtra("task", new Gson().toJson(task));
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class TaskVH extends RecyclerView.ViewHolder {
        TextView title, status;

        TaskVH(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            status = itemView.findViewById(R.id.tvStatus);
        }
    }
}
