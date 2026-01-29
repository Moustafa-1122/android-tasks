package com.example.projectwork.api;

import com.example.projectwork.model.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("todos")
    Call<List<Task>> getTasks();
}
