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

public class GameActivity extends AppCompatActivity {

    //Constant sizes for the objects
    private final int BALL_SIZE = 50;
    private final int TARGET_SIZE = 60;
    private final int OBSTACLE_SIZE = 65;

    //For the scores
    public static int[] scores = new int[3];
    private int currentScore = 0;
    private Paint textColor = new Paint();

    //For 'level'
    private int currentLevel = 0;

    private Intent i;

    //Graphics view to draw upon
    public class GraphicsView extends View {

        //For debugging
        //String TAG = "TAG_GESTURE";

        //Create a gesture detector
        private final GestureDetector gestureDetector;

        //Width and Height of the screen
        private float width = 0;
        private float height = 0;

        //Declare the obstacles
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
        private int minX = 0;
        private  int minY = 0;

        //Boolean for only making it go once
        private boolean gameOver = false;
        private boolean gainPoint = false;
        private boolean gainTargetSize = false;
        private boolean gainSpeed = false;
        private boolean gainPlayerSize = false;


        public GraphicsView(Context context){
            super(context);
            //Gesture detector
            gestureDetector = new GestureDetector(context, new MyGestureListener());
            //To move to the Score screen
            i = new Intent(getContext(), ScoreActivity.class);
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
                //Log.i(TAG, "onFling");
                //Log.i(TAG, "onFling velocity x: " + velocityX);
                //Log.i(TAG, "onFling velocity y: " + velocityY);

                //To find out which direction was swiped
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
            minX = 65;
            minY = 65;

            //Draw the objects in initial positions
            player = new Ball(x,y+500, BALL_SIZE);
            obstacleBasic = new Obstacles(x-90, y-500, OBSTACLE_SIZE, 0);
            obstacleIncreaseTarget = new Obstacles(x, y-200, OBSTACLE_SIZE, 1);
            obstacleIncreaseSpeed = new Obstacles(x+300, y-200, OBSTACLE_SIZE, 2);
            obstacleIncreasePlaySize = new Obstacles(x-300, y-200, OBSTACLE_SIZE, 3);
            target = new Target(x, y-600, TARGET_SIZE);



            super.onSizeChanged(w, h, oldw, oldh);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            //Move to score screen
            //Start the list activity
            if (gameOver){
                //Stops the player
                increaseXby = 0;
                increaseYby = 0;
                x+=increaseXby;
                y+=increaseYby;
                player.setX(x);
                player.setY(y);
                //this.getContext().startActivity(i);
                finish();
            }

            if (gainPoint){
                //Change the target's x and y
                target.setX(400);
                target.setY(500);
                //Increase the score
                currentScore++;
                gainPoint = false;
            }

            if (gainTargetSize){
                //Increase the target size
                target.setRadius(target.getRadius()*2);
                gainTargetSize = false;
            }

            if (gainSpeed){
                //Increase the speed
                increaseXby = increaseXby*2;
                increaseYby = increaseYby*2;
                gainSpeed = false;
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
                target.remove();
                gainPoint = true;
            }

            //Check if collision occurred with obstacle
            if (player.collisionDetection(obstacleBasic)){
                obstacleBasic.remove();
                gameOver = true;
            }

            if (player.collisionDetection(obstacleIncreaseTarget)){
                obstacleIncreaseTarget.remove();
                gainTargetSize = true;
            }

            if (player.collisionDetection(obstacleIncreaseSpeed)){
                obstacleIncreaseSpeed.remove();
                gainSpeed = true;
            }

            if (player.collisionDetection(obstacleIncreasePlaySize)){
                obstacleIncreasePlaySize.remove();
                gainPlayerSize = true;
            }

            //Add the animation
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
            obstacleBasic.draw(canvas);
            obstacleIncreaseTarget.draw(canvas);
            obstacleIncreaseSpeed.draw(canvas);
            obstacleIncreasePlaySize.draw(canvas);
            target.draw(canvas);
            //Create loop
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