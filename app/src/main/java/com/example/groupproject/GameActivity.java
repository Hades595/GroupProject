package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;

public class GameActivity extends AppCompatActivity {

    public class GraphicsView extends View {

        private float width = 0;
        private float height = 0;

        private int x;
        private int y;

        Ball player;
        Obstacles obstacle1;
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

            player = new Ball(x,y,50);
            obstacle1 = new Obstacles(x, y+200, 50, 2);
            target = new Target(x, y+500, 50);


            super.onSizeChanged(w, h, oldw, oldh);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            player.draw(canvas);
            obstacle1.draw(canvas);
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