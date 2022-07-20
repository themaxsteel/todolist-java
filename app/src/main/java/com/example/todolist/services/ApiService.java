package com.example.todolist.services;

import com.example.todolist.model.TaskResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("task?sortBy=id&order=desc")
    Observable<List<TaskResponse>> getAllTask();

    @GET("task")
    Observable<List<TaskResponse>> searchTask(@Query("search") String search);

    @POST("task")
    Observable<TaskResponse> postTask(@Body TaskResponse task);

    @PUT("task/{id}")
    Observable<TaskResponse> updateTask(@Path("id") int id, @Body TaskResponse task);

    @PATCH("task/{id}")
    Observable<TaskResponse> replaceTask(@Path("id") int id, @Body TaskResponse task);

    @DELETE("task/{id}")
    Observable<TaskResponse> deleteTask(@Path("id") int id);
}
