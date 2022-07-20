package com.example.todolist.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.todolist.R;
import com.example.todolist.adapter.TaskAdapter;
import com.example.todolist.model.TaskResponse;
import com.example.todolist.viewmodel.HomeViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SearchActivity extends AppCompatActivity {
    TextView tvSearchEmpty;
    SearchView searchView;
    HomeViewModel viewModel;
    RecyclerView recyclerView;
    TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        tvSearchEmpty = findViewById(R.id.tv_search_empty);
        searchView = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.rv_search);

        searchView.setFocusable(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(task -> taskDetail(task));
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchTask(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void searchTask(String text){
        viewModel.searchTask(text);
        viewModel.getLiveData().observe(this, new Observer<List<TaskResponse>>() {
            @Override
            public void onChanged(List<TaskResponse> taskResponses) {
                if(taskResponses.isEmpty()){
                    tvSearchEmpty.setVisibility(View.VISIBLE);
                }else{
                    tvSearchEmpty.setVisibility(View.GONE);
                    adapter.setData(taskResponses);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }

    private  void taskDetail(TaskResponse task){
        Intent i = new Intent(SearchActivity.this, TaskDetailActivity.class);
        i.putExtra("task", task);
        startActivity(i);
    }
}