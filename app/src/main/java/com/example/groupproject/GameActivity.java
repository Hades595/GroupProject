package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;

public class GameActivity extends AppCompatActivity {

    //TODO
    //Implement score
    //Implement trajectory
    //Implement gestures and fling
    //Implement check collision
    //Implement animation

    private int ballSize = 50;
    private int targetSize = 60;
    private int obstacleSize = 65;

    public class GraphicsView extends View {

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
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //New graphics view for drawing ball
        GraphicsView graphicsView = new GraphicsView(this);

        //Constraint the graphic layout to the constraint layout
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.constraint_layout_graphics);
        constraintLayout.addView(graphicsView);


    }


}