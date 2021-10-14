package com.example.groupproject;
import static com.example.groupproject.GameActivity.GraphicsView.MY_PREFS_NAME;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Bundle i = getIntent().getExtras();
        int currentScore = i.getInt("currentScore");
        //int[] scoreArrayDup = i.getIntArray("scores"); //this contains the last 5 scores. NOT CURRENT SCORE
        int[] scoreArrayDup = new int[5];

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        scoreArrayDup[0] = prefs.getInt("0", 0);
        scoreArrayDup[1] = prefs.getInt("1", 0);
        scoreArrayDup[2] = prefs.getInt("2", 0);
        scoreArrayDup[3] = prefs.getInt("3", 0);
        scoreArrayDup[4] = prefs.getInt("4", 0);


        TextView latestScore = (TextView) findViewById(R.id.scoreText);
        ImageView overBanner = (ImageView) findViewById(R.id.gameOverBanner);

        latestScore.setText(currentScore + "");

        //premade array for testing overBanner screens
        //int[] scoreArrayDup = {1,1,1,1,0};
        if (currentScore > scoreArrayDup[0]) {
            overBanner.setImageResource(R.drawable.highscorebanner);
        } else {
            overBanner.setImageResource(R.drawable.gameoverbannerred);
        }

        //Listview population to show best 5 scores
        //first make an array of strings using our integer array
        ArrayList<String> arrayListScores = new ArrayList<>();
        for(int s:scoreArrayDup) {
            arrayListScores.add(String.valueOf(s));
        }

        //create the adaptor
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayListScores);
        ListView listView = (ListView)findViewById(R.id.bestFiveScores);
        listView.setAdapter(arrayAdapter);



        //Remove the action bar
        ActionBar actionbar = getSupportActionBar();
        actionbar.hide();

        //Set immersive mode, since user will be clicking a lot
        int uiOptions = View. SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View. SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);
    }




    //When the user clicks the retry button //we want a fresh version of gameActivity.
    public void onClickRetry(View v){
        //Debugging
        //Toast.makeText(this, "Message", Toast.LENGTH_SHORT).show();

        //Start the list activity
        Intent i = new Intent(this, GameActivity.class);
        startActivity(i);
    }
}