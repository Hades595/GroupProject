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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    //Graphics view to draw upon
    public class GraphicsView extends View {

        //For debugging
        String TAG = "TAG_GESTURE";

        //Constant sizes for the objects
        private final int BALL_SIZE = 50;
        private final int TARGET_SIZE = 60;
        private final int OBSTACLE_SIZE = 65;

        //For the scores
        public int[] scores = new int[5];
        private int currentScore = 0;
        private final Paint textColor = new Paint();
        //For 'level'
        private int currentLevel = 0;
        //For the Obstacles
        ArrayList<Obstacles> obstacles = new ArrayList<>();

        //To switch to score activity
        private Intent i;

        //Create a gesture detector
        private final GestureDetector gestureDetector;

        //Width and Height of the screen
        private float width = 0;
        private float height = 0;

        //Declare the obstacles for level 1
        Ball player;
        Obstacles obstacleBasic;
        Obstacles obstacleIncreaseTarget;
        Obstacles obstacleIncreaseSpeed;
        Obstacles obstacleIncreasePlaySize;
        Target target;

        //Of the player
        private int x;
        private int y;
        //For the animation of the player
        private float increaseXby = 0;
        private float increaseYby = 0;
        //For the play area
        private int maxX = 0;
        private int maxY = 0;

        //Boolean for only making it go once
        private boolean gameOver = false;
        private boolean gainPoint = false;
        private boolean gainTargetSize = false;
        private boolean gainSpeed = false;
        private boolean currSpeed = false;
        private boolean gainPlayerSize = false;

        //For random generation
        private final Random rand = new Random();

        public GraphicsView(Context context){
            super(context);
            //Gesture detector
            gestureDetector = new GestureDetector(context, new MyGestureListener());
            //For the score
            textColor.setColor(Color.WHITE);
            textColor.setTextSize(120);
            //For the high scores
            Arrays.fill(scores, 0);
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
                Log.i(TAG, "onDOWN");
                return true;
            }

            //Whenever the user flings
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                //To find out which direction was swiped
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            //Swipe right
                            Log.i(TAG, "Right");
                            increaseXby = 10;
                            increaseYby = 0;
                        } else {
                            //Swipe left
                            Log.i(TAG, "Left");
                            increaseXby = -10;
                            increaseYby = 0;
                        }
                    }
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        //Swipe bottom
                        Log.i(TAG, "Bottom");
                        increaseXby = 0;
                        increaseYby = 10;
                    } else {
                        //Swipe top
                        Log.i(TAG, "Top");
                        increaseXby = 0;
                        increaseYby = -10;

                    }
                }

                return true;
            }
        }


        //When the user launches the app
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

            //Draw the objects in initial positions
            //First level is always the same
            player = new Ball(x,y+500, BALL_SIZE);
            obstacleBasic = new Obstacles(x, y+100, OBSTACLE_SIZE, 0);
            obstacles.add(obstacleBasic); //Just need to add in to keep track of how many there are
            obstacleIncreaseTarget = new Obstacles(x, y-200, OBSTACLE_SIZE, 1);
            obstacleIncreaseSpeed = new Obstacles(x+300, y-200, OBSTACLE_SIZE, 2);
            obstacleIncreasePlaySize = new Obstacles(x-300, y-200, OBSTACLE_SIZE, 3);
            target = new Target(x, y-500, TARGET_SIZE);

            super.onSizeChanged(w, h, oldw, oldh);
        }

        int counter = 0;

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            //Move to score screen
            //Start the list activity
            if (gameOver){
                //check if the current score is higher than the previous scores
                for (int i = 0; i < scores.length; i++){
                    //If the current score is higher than the previous scores
                    if (currentScore > scores[i])
                        //change the score
                        scores[i] = currentScore;
                }
                //To move to the Score screen
                i = new Intent(getContext(), ScoreActivity.class);
                //Put the scores in
                i.putExtra("currentScore",currentScore);
                i.putExtra("scores", scores);
                //Start the score activity
                startActivity(i);
                gameOver = false;
                finish();
            }

            if (gainPoint){
                nextLevel();
                gainPoint = false;
            }

            if (gainTargetSize){
                //Increase the target size
                target.setRadius(target.getRadius()*2);
                gainTargetSize = false;
            }

            if (gainSpeed){
                gainSpeed = false;
                currSpeed = true;
            }

            if (currSpeed){
                //Increase the speed
                increaseXby = increaseXby*4;
                increaseYby = increaseYby*4;
            }

            //Roughly 6 seconds
            if (counter > 600){
                currSpeed = false;
                counter = 0;
            }


            if (gainPlayerSize){
                //Increase the player size
                player.setRadius(player.getRadius()*2);
                gainPlayerSize = false;
            }


            //If the point is between the screen
            if (!(x > 0 && x < width &&
                    y > 0 && y < height)){
                //Set the temp values
                //For checking if the ball is inside the walls
                float tempx = increaseXby;
                float tempy = increaseYby;
                //Stop the ball
                increaseXby = 0.0f;
                increaseYby = 0.0f;
                //Reverse the ball
                increaseXby -= tempx;
                increaseYby -= tempy;
            }

            //check if collision occurred with target
            if(player.collisionDetection(target)){
                //Remove the target so the player doesnt get any more points
                target.remove();
                gainPoint = true;
                //Increase the score
                currentScore++;
                currentLevel++;
            }

            for (Obstacles obstacle:obstacles) {
                //Check if collision occurred with obstacle
                if (player.collisionDetection(obstacle)){
                    obstacle.remove();
                    //Stops the player
                    increaseXby = 0;
                    increaseYby = 0;
                    x+=increaseXby;
                    y+=increaseYby;
                    player.setX(x);
                    player.setY(y);
                    gameOver = true;
                }
            }

            if (player.collisionDetection(obstacleIncreaseTarget)){
                //Remove the obstacle so the game doesnt keep hitting it
                obstacleIncreaseTarget.remove();
                gainTargetSize = true;
            }

            if (player.collisionDetection(obstacleIncreaseSpeed)){
                obstacleIncreaseSpeed.remove();
            }

            if (player.collisionDetection(obstacleIncreasePlaySize)){
                obstacleIncreasePlaySize.remove();
                gainPlayerSize = true;
            }

            //Add the animation for the player
            x = player.getX();
            y = player.getY();
            x += increaseXby;
            y += increaseYby;
            player.setX(x);
            player.setY(y);

            //Draw the score
            canvas.drawText(String.valueOf(currentScore), width/2, 200, textColor);
            //Draw the player
            player.draw(canvas);
            //Draw each obstacle
            for (Obstacles obstacle:obstacles) {
                obstacle.draw(canvas);
            }
            obstacleIncreaseTarget.draw(canvas);
            obstacleIncreaseSpeed.draw(canvas);
            obstacleIncreasePlaySize.draw(canvas);
            target.draw(canvas);
            counter++;
            //Create loop
            invalidate();
        }

        private void nextLevel(){
            //Every level more game over balls show up
            if (currentLevel > 0 && currentLevel < 25){
                target.setX(rand.nextInt(maxX));
                target.setY(rand.nextInt(maxY));
                //Increase the number of game over obstacles
                Obstacles obstacleBasic = new Obstacles(rand.nextInt(maxX),rand.nextInt(maxY), OBSTACLE_SIZE, 0);
                obstacles.add(obstacleBasic);
                //Change the power co-ords
                obstacleIncreaseTarget.setX(rand.nextInt(maxX));
                obstacleIncreaseTarget.setY(rand.nextInt(maxY));
                obstacleIncreaseSpeed.setX(rand.nextInt(maxX));
                obstacleIncreaseSpeed.setY(rand.nextInt(maxY));
                obstacleIncreasePlaySize.setX(rand.nextInt(maxX));
                obstacleIncreasePlaySize.setY(rand.nextInt(maxY));

            }
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

        //Constraint the graphic layout to the constraint layout
        ConstraintLayout constraintLayout = findViewById(R.id.constraint_layout_graphics);
        constraintLayout.addView(graphicsView);


    }



}