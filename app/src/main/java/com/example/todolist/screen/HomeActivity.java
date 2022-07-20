package com.example.todolist.screen;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolist.R;
import com.example.todolist.adapter.TaskAdapter;
import com.example.todolist.db.TaskDatabase;
import com.example.todolist.model.TaskResponse;
import com.example.todolist.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    TaskAdapter taskAdapter;
    TextView tvTaskWaiting, tvWorksSize, tvRoutinesSize;
    ImageView btnAdd;
    HomeViewModel viewModel;
    SwipeRefreshLayout refreshLayout;
    LinearLayout routineBox, workBox;
    List<TaskResponse> trWorks, trRoutines;
    LinearLayout searchBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        tvTaskWaiting = findViewById(R.id.tvTaskWaiting);
        tvWorksSize = findViewById(R.id.tvWorkSize);
        tvRoutinesSize = findViewById(R.id.tvRoutineSize);
        btnAdd = findViewById(R.id.btn_add);
        recyclerView = findViewById(R.id.rv_task);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapter(task -> taskDetail(task));
        btnAdd.setOnClickListener(this);
        refreshLayout = findViewById(R.id.refreshLayout);
        routineBox = findViewById(R.id.boxRoutines);
        workBox = findViewById(R.id.boxWorks);
        searchBox = findViewById(R.id.search_box);
        routineBox.setOnClickListener(this);
        workBox.setOnClickListener(this);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModelInit();

        refreshLayout.setOnRefreshListener(() -> {
            viewModel.getAllTask();
            refreshLayout.setRefreshing(false);
        });

        searchBox.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_add) {
            startActivity(new Intent(HomeActivity.this, AddTaskActivity.class));
        } else if (v.getId() == R.id.boxRoutines) {
            Intent i = new Intent(HomeActivity.this, WorkActivity.class);
            i.putExtra("title", "Routine");
            i.putParcelableArrayListExtra("category", (ArrayList<? extends Parcelable>) trRoutines);
            startActivity(i);
        } else if (v.getId() == R.id.boxWorks) {
            Intent i = new Intent(HomeActivity.this, WorkActivity.class);
            i.putExtra("title", "Work");
            i.putParcelableArrayListExtra("category", (ArrayList<? extends Parcelable>) trWorks);
            startActivity(i);
        } else if (v.getId() == R.id.search_box) {
            Intent i = new Intent(HomeActivity.this, SearchActivity.class);
            startActivity(i);
        }
    }

    private void taskDetail(TaskResponse task) {
        Intent i = new Intent(HomeActivity.this, TaskDetailActivity.class);
        i.putExtra("task", task);
        startActivity(i);
    }

    public void viewModelInit() {
        viewModel.getAllTask();
        viewModel.getLiveData().observeForever(taskResponses -> {
            taskAdapter.setData(taskResponses);
            recyclerView.setAdapter(taskAdapter);

            trWorks = new ArrayList<>();
            trRoutines = new ArrayList<>();
            for (TaskResponse tr : taskResponses) {
                if (tr.getCategory().equals("Works")) {
                    trWorks.add(tr);
                } else if (tr.getCategory().equals("Routines")) {
                    trRoutines.add(tr);
                }
            }

            tvWorksSize.setText(trWorks.size() + " task(s)");
            tvRoutinesSize.setText(trRoutines.size() + " task(s)");
            tvTaskWaiting.setText(taskResponses.size() + " task(s) waiting");
        });

    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 100) {
                        viewModelInit();
                        Toast.makeText(HomeActivity.this, "Hehei", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );
}