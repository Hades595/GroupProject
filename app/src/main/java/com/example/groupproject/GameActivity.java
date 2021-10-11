package com.example.groupproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    //TODO
    //Implement check collision

    //For the scores
    public static int[] scores = new int[3];
    private int currentScore = 0;
    private Paint textColor = new Paint();

    //Constant sizes for the objects
        //I plan to move them to classes, as they make more sense to be there
    private int ballSize = 50;
    private int targetSize = 60;
    private int obstacleSize = 65;

    //Graphics view to draw upon
    public class GraphicsView extends View {

        //Create a gesture detector
        private final GestureDetector gestureDetector;

        String TAG = "TAG_GESTURE";

        //Width and Height of the screen
        private float width = 0;
        private float height = 0;

        //Declare the obstacles
            //Later define multiple obstacles for each power up and just call the draw method
        Ball player;
        Obstacles obstacle1;
        Obstacles obstacle2;
        Obstacles obstacle3;
        Target target;


        //Of the player
        private int x;
        private int y;
        //For the animation of the player
        private float increaseXby = 0;
        private float increaseYby = 0;
        //For checking if the ball is inside the walls
        private float tempx = 0;
        private float tempy = 0;
        //For the play area
        private int maxX = 0;
        private int maxY = 0;
        private int minX = 0;
        private  int minY = 0;


        public GraphicsView(Context context){
            super(context);
            //Gesture detector
            gestureDetector = new GestureDetector(context, new MyGestureListener());
            textColor.setColor(Color.WHITE);
            textColor.setTextSize(120);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            //Whenever user touches the screen
            if(gestureDetector.onTouchEvent(event)){
                return true;
            }
            return super.onTouchEvent(event);
        }

        class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            //Whenever the user pulls down
            @Override
            public boolean onDown(MotionEvent e) {
                Log.i("TAG", "onDOWN");
                return true;
            }

            //Whenever the user flings
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.i(TAG, "onFling");
                Log.i(TAG, "onFling velocity x: " + velocityX);
                Log.i(TAG, "onFling velocity y: " + velocityY);

                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            //Swipe right
                            increaseXby = 10;
                            increaseYby = 0;
                        } else {
                            //Swipe left
                            increaseXby = -10;
                            increaseYby = 0;
                        }
                    }
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        //Swipe bottom
                        increaseXby = 0;
                        increaseYby = 10;
                    } else {
                        //Swipe top
                        increaseXby = 0;
                        increaseYby = -10;

                    }
                }

                return true;
            }
        }


        //When the user lanuches the app
        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            //Get the width and height of the screen
            width = w;
            height = h;
            //Find the middle
            x = (int) (width/2);
            y = (int) (height/2);
            //Define the play area
            maxX = (int) (width - 65);
            maxY = (int) (height - 65);
            minX = 65;
            minY = 65;

            //Draw the objects
            player = new Ball(x,y+700,ballSize);
            obstacle1 = new Obstacles(x, y-200, obstacleSize, 1);
            obstacle2 = new Obstacles(x+300, y-200, obstacleSize, 2);
            obstacle3 = new Obstacles(x-300, y-200, obstacleSize, 3);
            target = new Target(x, y-600, targetSize);

            super.onSizeChanged(w, h, oldw, oldh);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            //Draw the score
            canvas.drawText(String.valueOf(currentScore), width/2, 200, textColor);
            //Draw the player
            player.draw(canvas);

            //Add the animation
            x += increaseXby;
            y += increaseYby;
            player.setX(x);
            player.setY(y);


            //If the point is between the screen
            if (!(x > 0 && x < width &&
                    y > 0 && y < height)){
                //Set the temp values
                tempx = increaseXby;
                tempy = increaseYby;
                //Stop the ball
                increaseXby = 0.0f;
                increaseYby = 0.0f;
                //Reverse the ball
                increaseXby -= tempx;
                increaseYby -= tempy;
            }

            //check if collision occured with target
            if(player.collisionDetection(player, target)){
                //Change the target's x and y
                target.setX(400);
                target.setY(500);
                //Increase the score
                currentScore++;
            }

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