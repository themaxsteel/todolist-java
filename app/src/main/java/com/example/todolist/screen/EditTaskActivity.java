package com.example.todolist.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.todolist.ApiClient;
import com.example.todolist.R;
import com.example.todolist.model.TaskResponse;
import com.example.todolist.viewmodel.HomeViewModel;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class EditTaskActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtText;
    RadioButton rbRoutine, rbWork, rbEasy, rbMedium, rbHard;
    Spinner spinnerDate;
    Button btnSubmit;
    String category = "Routines";
    String difficulty = "Easy";
    HomeViewModel viewModel;
    Toolbar toolbar;

    TaskResponse task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task_screen);

        toolbar = findViewById(R.id.edit_toolbar);
        toolbar.setTitle("Edit Task");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white);

        task = getIntent().getParcelableExtra("task");

        spinnerDate = findViewById(R.id.spinner_date);
        String[] items = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(EditTaskActivity.this, android.R.layout.simple_spinner_dropdown_item, items);

        spinnerDate.setAdapter(spinnerAdapter);

        edtText = findViewById(R.id.edt_name);
        rbRoutine = findViewById(R.id.rb_routine);
        rbWork = findViewById(R.id.rb_work);
        rbEasy = findViewById(R.id.rb_easy);
        rbMedium = findViewById(R.id.rb_medium);
        rbHard = findViewById(R.id.rb_hard);
        btnSubmit = findViewById(R.id.btn_submit_task);

        btnSubmit.setOnClickListener(this);
        rbRoutine.setOnClickListener(this);
        rbWork.setOnClickListener(this);
        rbEasy.setOnClickListener(this);
        rbMedium.setOnClickListener(this);
        rbHard.setOnClickListener(this);

        edtText.setText(task.getName());

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_submit_task){
            updateTask();
        }else if(v.getId() == R.id.rb_routine){
            category = "Routines";
        }else if (v.getId() == R.id.rb_work){
            category = "Works";
        }else if(v.getId() == R.id.rb_easy){
            difficulty = "Easy";
        }else if (v.getId() == R.id.rb_medium){
            difficulty = "Medium";
        }else if(v.getId() == R.id.rb_hard){
            difficulty = "Hard";
        }
    }

    public void updateTask(){
        btnSubmit.setEnabled(false);
        TaskResponse taskItem = new TaskResponse(edtText.getText().toString(), difficulty, category, spinnerDate.getSelectedItem().toString());
        viewModel.updateTask(Integer.parseInt(task.getId()), taskItem);
        viewModel.getOnSuccess().observe(this, taskResponse -> {
            Toast.makeText(this, "Updated: " + taskResponse.getName(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(EditTaskActivity.this, TaskDetailActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("task", taskItem);
            startActivity(i);
        });
//        Call<TaskResponse> updateTask = ApiClient.getTaskService().updateTask(Integer.parseInt(task.getId()), taskItem);
//        updateTask.enqueue(new Callback<TaskResponse>() {
//            @Override
//            public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
//                Toast.makeText(EditTaskActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(EditTaskActivity.this, HomeActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(i);
//            }
//
//            @Override 
//            public void onFailure(Call<TaskResponse> call, Throwable t) {
//                Toast.makeText(EditTaskActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}