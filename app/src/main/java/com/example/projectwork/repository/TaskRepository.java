package com.example.projectwork.repository;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.projectwork.api.ApiService;
import com.example.projectwork.api.RetrofitClient;
import com.example.projectwork.model.Task;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskRepository {
    private static final String TAG = "TaskRepository";
    private static TaskRepository instance;
    private ApiService apiService;

    private TaskRepository() {
        apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    // Singleton Pattern
    public static synchronized TaskRepository getInstance() {
        if (instance == null) {
            instance = new TaskRepository();
        }
        return instance;
    }

    // ⭐ Fetch tasks from API
    public LiveData<List<Task>> getTasks() {
        MutableLiveData<List<Task>> data = new MutableLiveData<>();

        apiService.getTasks().enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body());
                    Log.d(TAG, "Tasks loaded: " + response.body().size());
                } else {
                    data.setValue(null);
                    Log.e(TAG, "Response not successful: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                data.setValue(null);
                Log.e(TAG, "Failed to load tasks", t);
            }
        });

        return data;
    }
}