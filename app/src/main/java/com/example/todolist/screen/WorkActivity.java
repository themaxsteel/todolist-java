package com.example.todolist.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.todolist.R;
import com.example.todolist.adapter.TaskAdapter;
import com.example.todolist.model.TaskResponse;
import com.example.todolist.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WorkActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TaskAdapter taskAdapter;
    HomeViewModel viewModel;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        Toolbar toolbar = findViewById(R.id.workToolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white);

        recyclerView = findViewById(R.id.rvWork);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        refreshLayout = findViewById(R.id.workRefreshLayout);
        taskAdapter = new TaskAdapter(task -> taskDetail(task));
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        refreshLayout.setOnRefreshListener(() -> {
            viewModel.getAllTask();
            refreshLayout.setRefreshing(false);
        });

        intentInit();
    }

    private void intentInit() {
        List<TaskResponse> category = getIntent().getParcelableArrayListExtra("category");
        if (category != null) {
            taskAdapter.setData(category);
            recyclerView.setAdapter(taskAdapter);
        }
    }

    private void taskDetail(TaskResponse task) {
        Intent i = new Intent(WorkActivity.this, TaskDetailActivity.class);
        i.putExtra("task", task);
        startActivity(i);
    }


}