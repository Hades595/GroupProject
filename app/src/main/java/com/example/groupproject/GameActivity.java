package com.example.groupproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class GameActivity extends AppCompatActivity {

    //TODO
    //Implement score
    //Implement trajectory
    //Implement gestures and fling
    //Implement check collision
    //Implement animation

    public static int[] scores = new int[3];

    private int ballSize = 50;
    private int targetSize = 60;
    private int obstacleSize = 65;

    public class GraphicsView extends View {

        private final GestureDetector gestureDetector;

        String TAG = "TAG_GESTURE";

        private float width = 0;
        private float height = 0;

        private int x;
        private int y;

        Ball player;
        Obstacles obstacle1;
        Obstacles obstacle2;
        Obstacles obstacle3;
        Target target;

        public GraphicsView(Context context){
            super(context);
            gestureDetector = new GestureDetector(context, new MyGestureListener());
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if(gestureDetector.onTouchEvent(event)){
                return true;
            }
            return super.onTouchEvent(event);
        }

        class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
            @Override
            public boolean onDown(MotionEvent e) {
                Log.i("TAG", "onDOWN");
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.i("TAG", "onFling");
                return true;
            }
        }


        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            //Get the width and height of the screen
            width = w;
            height = h;
            x = (int) (width/2);
            y = (int) (height/2);

            player = new Ball(x,y+700,ballSize);
            obstacle1 = new Obstacles(x, y-200, obstacleSize, 2);
            obstacle2 = new Obstacles(x+300, y-200, obstacleSize, 2);
            obstacle3 = new Obstacles(x-300, y-200, obstacleSize, 2);
            target = new Target(x, y-600, targetSize);


            super.onSizeChanged(w, h, oldw, oldh);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            player.draw(canvas);
            obstacle1.draw(canvas);
            obstacle2.draw(canvas);
            obstacle3.draw(canvas);
            target.draw(canvas);
            invalidate();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Remove the action bar
        ActionBar actionbar = getSupportActionBar();
        actionbar.hide();

        //Set immersive mode, since user will be clicking a lot
        int uiOptions = View. SYSTEM_UI_FLAG_IMMERSIVE
                | View. SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);


        //New graphics view for drawing ball
        GraphicsView graphicsView = new GraphicsView(this);

        scores[1] = 50;

        //Constraint the graphic layout to the constraint layout
        ConstraintLayout constraintLayout = findViewById(R.id.constraint_layout_graphics);
        constraintLayout.addView(graphicsView);


    }



}