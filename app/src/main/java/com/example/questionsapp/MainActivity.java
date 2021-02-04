package com.example.questionsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent quizActivityIntent = new Intent(MainActivity.this, QuizActivity.class);
        startActivity(quizActivityIntent);

    }
}