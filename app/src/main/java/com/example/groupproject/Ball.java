package com.example.groupproject;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ball extends Object{
    //Player

    //Set paint for ball
    private final Paint color = new Paint();

    public Ball(int x, int y, int radius) {
        super(x, y, radius);
        color.setColor(Color.WHITE);
    }

    public void draw(Canvas canvas){
        //Draw the ball
        canvas.drawCircle(getX(), getY(), getRadius(), color);
    }
}
