package com.example.groupproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Remove the action bar
        ActionBar actionbar = getSupportActionBar();
        actionbar.hide();

        //Set immersive mode, since user will be clicking a lot
        int uiOptions = View. SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View. SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);

        MediaPlayer player = MediaPlayer.create(this, R.raw.music);
        player.setLooping(true);
        player.start();

    }


    //When the user clicks the start button
    public void onClickStart(View v){
        //Debugging
        //Toast.makeText(this, "Message", Toast.LENGTH_SHORT).show();
        MediaPlayer mp = MediaPlayer.create(this, R.raw.start);
        mp.start();
        //Start the list activity
        Intent i = new Intent(this, GameActivity.class);
        startActivity(i);
    }

}