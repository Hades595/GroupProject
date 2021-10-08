package com.example.groupproject;

import static com.example.groupproject.GameActivity.scores;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Toast.makeText(this, scores[1], Toast.LENGTH_SHORT).show();
    }
}