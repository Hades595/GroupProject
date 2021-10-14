package com.example.groupproject;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Intent i = getIntent();
        ArrayList scores =  i.getParcelableExtra("scores"); //the scores from the game


        //Remove the action bar
        ActionBar actionbar = getSupportActionBar();
        actionbar.hide();

        //Set immersive mode, since user will be clicking a lot
        int uiOptions = View. SYSTEM_UI_FLAG_IMMERSIVE
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