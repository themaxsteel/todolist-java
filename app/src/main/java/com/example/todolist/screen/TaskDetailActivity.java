package com.example.todolist.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolist.ApiClient;
import com.example.todolist.R;
import com.example.todolist.db.TaskDatabase;
import com.example.todolist.model.TaskResponse;
import com.example.todolist.viewmodel.HomeViewModel;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class TaskDetailActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvName, tvCategory, tvLevel, tvDate;
    Button btnEdit, btnDelete;
    HomeViewModel viewModel;
    TaskResponse task;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail_screen);

        toolbar = findViewById(R.id.detail_toolbar);
        toolbar.setTitle("Task Detail");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white);

        tvName = findViewById(R.id.tv_task_name);
        tvCategory = findViewById(R.id.tv_task_category);
        tvLevel = findViewById(R.id.tv_task_level);
        tvDate = findViewById(R.id.tv_task_date);
        btnEdit = findViewById(R.id.btn_task_edit);
        btnDelete = findViewById(R.id.btn_task_delete);


        task = getIntent().getParcelableExtra("task");
        tvName.setText("Name : " + task.getName());
        tvCategory.setText("Category : " + task.getCategory());
        tvLevel.setText("Level : " + task.getLevel());
        tvDate.setText("Date : " + task.getDate());

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_task_edit){
            Intent i = new Intent(TaskDetailActivity.this, EditTaskActivity.class);
            i.putExtra("task", task);
            startActivity(i);
        }else if(v.getId() == R.id.btn_task_delete){
            TaskDatabase db = TaskDatabase.getInstance(this.getApplicationContext());
            btnDelete.setEnabled(false);
            viewModel.deleteTask(Integer.parseInt(task.getId()));
            viewModel.getOnSuccess().observe(this, taskResponse -> {
                db.taskDao().deleteTask(task);
                btnDelete.setEnabled(true);
                Intent i = new Intent(TaskDetailActivity.this, HomeActivity.class);
                i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            });
        }
    }
}