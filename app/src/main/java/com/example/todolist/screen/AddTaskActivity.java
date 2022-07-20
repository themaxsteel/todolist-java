package com.example.todolist.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
public class AddTaskActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtText;
    RadioButton rbRoutine, rbWork, rbEasy, rbMedium, rbHard;
    Spinner spinnerDate;
    Button btnSubmit;
    String category = "Routines";
    String difficulty = "Easy";
    HomeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_screen);

        Toolbar toolbar = findViewById(R.id.add_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Add Task");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white);

        spinnerDate = findViewById(R.id.spinner_date);
        String[] items = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(AddTaskActivity.this, android.R.layout.simple_spinner_dropdown_item, items);

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

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit_task) {
            postTask();
        } else if (v.getId() == R.id.rb_routine) {
            category = "Routines";
        } else if (v.getId() == R.id.rb_work) {
            category = "Works";
        } else if (v.getId() == R.id.rb_easy) {
            difficulty = "Easy";
        } else if (v.getId() == R.id.rb_medium) {
            difficulty = "Medium";
        } else if (v.getId() == R.id.rb_hard) {
            difficulty = "Hard";
        }

    }

    public void postTask() {
        btnSubmit.setEnabled(false);
        TaskResponse task = new TaskResponse(edtText.getText().toString(), difficulty, category, spinnerDate.getSelectedItem().toString());
        viewModel.postTask(task);

        viewModel.getOnSuccess().observe(this, new Observer<TaskResponse>() {
               @Override
            public void onChanged(TaskResponse taskResponse) {
                Intent i = new Intent(AddTaskActivity.this, HomeActivity.class);
                i.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                btnSubmit.setEnabled(true);
//                Intent i = new Intent(AddTaskActivity.this, HomeActivity.class);
//                i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(i);
//            }
//        }, 2000);
//        Call<TaskResponse> postTask = ApiClient.getTaskService().postTask(task);
//
//        postTask.enqueue(new Callback<TaskResponse>() {
//            @Override
//            public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
//                if(response.isSuccessful()){
//                    Toast.makeText(AddTaskActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(AddTaskActivity.this, HomeActivity.class);
//                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(i);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TaskResponse> call, Throwable t) {
//                Toast.makeText(AddTaskActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

}