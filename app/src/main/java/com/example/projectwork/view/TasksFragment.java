package com.example.projectwork.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.projectwork.R;
import com.example.projectwork.adapter.TasksAdapter;
import com.example.projectwork.model.Task;
import com.example.projectwork.viewmodel.TaskViewModel;

import java.util.ArrayList;
import java.util.List;

public class TasksFragment extends Fragment {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private TasksAdapter adapter;
    private List<Task> taskList = new ArrayList<>();

    // ⭐ ViewModel
    private TaskViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        // Initialize Views
        recyclerView = view.findViewById(R.id.recyclerTasks);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TasksAdapter(getContext(), taskList);
        recyclerView.setAdapter(adapter);

        // ⭐ Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        // ⭐ Observe LiveData
        observeViewModel();

        // Load tasks
        viewModel.loadTasks();

        // Swipe to refresh
        swipeRefresh.setOnRefreshListener(() -> viewModel.loadTasks());

        return view;
    }

    // ⭐ Observe ViewModel data
    private void observeViewModel() {
        // Observe tasks
        viewModel.getTasksLiveData().observe(getViewLifecycleOwner(), tasks -> {
            if (tasks != null) {
                taskList.clear();
                taskList.addAll(tasks);
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(),
                        "Loaded " + tasks.size() + " tasks",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Observe loading state
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            swipeRefresh.setRefreshing(isLoading);
        });

        // Observe errors
        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}