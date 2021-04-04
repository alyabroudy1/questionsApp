package com.example.questionsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textViewWelcome;
    Button buttonConfirm;

    String[] subjects = { "WUG", "ITK", "ANW", "Database", "MiddleExam", "FinalExam"};

    Spinner spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewWelcome = (TextView) findViewById(R.id.main_textView_welcome_text);
        buttonConfirm = (Button) findViewById(R.id.main_button_confirm);

        spin = (Spinner) findViewById(R.id.main_spinner_subjects);

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quizActivityIntent = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(quizActivityIntent);
            }
        });
        

    }
}