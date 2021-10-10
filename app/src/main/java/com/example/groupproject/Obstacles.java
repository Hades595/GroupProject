package com.example.groupproject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Obstacles extends Object {

    //Interaction with the player
    //Can throw them off-course and power ups etc

    //TODO
    //Implement 3 different types of obstacles
        //Travel 2x fast
        //Make the player bigger
        //Make the obstacles smaller, not the power up
    //Getter for type

    //the type of power
    private int type = 0;
    private final int x;
    private final int y;
    private final int radius;

    private Paint color_default = new Paint();

    public Obstacles(int x, int y, int radius, int type){
        super(x,y, radius);
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.type = type;
        //Setting the default_obstacles color from R.color
            //Will fix later
        color_default.setColor(Color.argb(255, 64, 212, 164));
    }

    public void draw(Canvas canvas){
        canvas.drawCircle(x, y, radius, color_default);
    }

}
