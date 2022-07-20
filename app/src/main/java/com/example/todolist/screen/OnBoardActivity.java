package com.example.todolist.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.todolist.R;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class OnBoardActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board_screen);

        Button btnStart = findViewById(R.id.btn_start);

        btnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_start){
            startActivity(new Intent(OnBoardActivity.this, HomeActivity.class));
        }
    }
}