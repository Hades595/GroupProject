package com.example.groupproject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Obstacles extends Object {

    //Interaction with the player
    //Can throw them off-course and power ups etc

    //the type of powerup
    private int type = 0;
    private final int x;
    private final int y;
    private final int radius;

    private final Paint color = new Paint();

    public Obstacles(int x, int y, int radius, int type){
        super(x,y, radius);
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.type = type;
        color.setColor(R.color.purple_500);
    }

    public void draw(Canvas canvas){
        canvas.drawCircle(x, y, radius, color);
    }

}
