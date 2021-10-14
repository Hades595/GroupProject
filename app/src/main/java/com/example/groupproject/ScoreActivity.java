package com.example.groupproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity {

    public static int[] scoreArrayDup = new int[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        //Remove the action bar
        ActionBar actionbar = getSupportActionBar();
        actionbar.hide();

        //Set sticky immersive mode, since user will be clicking a lot
        int uiOptions = View. SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View. SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);

        Intent intent = getIntent();
        int currentScore = intent.getIntExtra("currentScore", 0);

        for (int j = 0; j < scoreArrayDup.length; j++){
            if (currentScore > scoreArrayDup[j]){
                //If it is outside of the index delete
                if (j == 5) {
                    //change the score
                    scoreArrayDup[j] = currentScore;
                }
                //Move the previous score down one
                int temp = scoreArrayDup[j];
                scoreArrayDup[j+1] = temp;
                //change the score
                scoreArrayDup[j] = currentScore;
                break;
            }
        }

        TextView latestScore = (TextView) findViewById(R.id.scoreText);
        ImageView overBanner = (ImageView) findViewById(R.id.gameOverBanner);

        latestScore.setText(currentScore + "");

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
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, R.layout.row, arrayListScores);
        ListView listView = (ListView)findViewById(R.id.bestFiveScores);
        listView.setAdapter(arrayAdapter);

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