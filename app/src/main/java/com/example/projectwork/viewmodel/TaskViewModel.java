package com.example.projectwork.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projectwork.model.Task;
import com.example.projectwork.repository.TaskRepository;

import java.util.List;

public class TaskViewModel extends ViewModel {
    private TaskRepository repository;
    private MutableLiveData<List<Task>> tasksLiveData;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<String> errorMessage;

    public TaskViewModel() {
        repository = TaskRepository.getInstance();
        tasksLiveData = new MutableLiveData<>();
        isLoading = new MutableLiveData<>(false);
        errorMessage = new MutableLiveData<>();
    }

    // ⭐ Load tasks
    public void loadTasks() {
        isLoading.setValue(true);

        LiveData<List<Task>> data = repository.getTasks();
        data.observeForever(tasks -> {
            isLoading.setValue(false);
            if (tasks != null) {
                tasksLiveData.setValue(tasks);
            } else {
                errorMessage.setValue("Failed to load tasks");
            }
        });
    }

    // Getters for LiveData
    public LiveData<List<Task>> getTasksLiveData() {
        return tasksLiveData;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}