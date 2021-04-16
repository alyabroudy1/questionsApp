package com.example.questionsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {



    TextView textViewWelcome;
    Button buttonConfirm;

    //String[] subjects = { SUBJECT_WUG, SUBJECT_ITK, SUBJECT_ANW, SUBJECT_DATABASE, SUBJECT_MIDDLE_EXAM, SUBJECT_FINAL_EXAM};

    Spinner spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewWelcome = (TextView) findViewById(R.id.main_textView_welcome_text);
        buttonConfirm = (Button) findViewById(R.id.main_button_confirm);

        spin = (Spinner) findViewById(R.id.main_spinner_subjects);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.subjects_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spin.setAdapter(adapter);

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get selected subject
                String selectedSubject= (String) spin.getSelectedItem();
                Log.d("TAG", "onClick: "+selectedSubject);

                //start a Quiz activity to start viewing questions
                Intent quizActivityIntent = new Intent(MainActivity.this, QuizActivity.class);

                quizActivityIntent.putExtra("SELECTED_SUBJECT", selectedSubject);
                startActivity(quizActivityIntent);
            }
        });
        

    }
}