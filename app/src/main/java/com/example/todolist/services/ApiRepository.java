package com.example.todolist.services;

import androidx.lifecycle.MutableLiveData;

import com.example.todolist.model.TaskResponse;
import com.example.todolist.utils.ResponseDataListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public interface ApiRepository {
    void getAllTask(ResponseDataListener<List<TaskResponse>> listener);

    void searchTask(String search, ResponseDataListener<List<TaskResponse>> listener);

    void postTask(TaskResponse taskResponse, ResponseDataListener<TaskResponse> listener);

    void updateTask(int id, TaskResponse taskResponse, ResponseDataListener<TaskResponse> listener);

    void deleteTask(int id,ResponseDataListener<TaskResponse> listener);

}
