package com.example.todolist.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todolist.model.TaskResponse;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM taskresponse")
    List<TaskResponse> getAllTask();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(TaskResponse taskResponses);

    @Delete
    void deleteTask(TaskResponse taskResponse);

    @Query("DELETE FROM taskresponse")
    void deleteAllTask();

    @   Update
    void updateTask(TaskResponse taskResponse);

}
