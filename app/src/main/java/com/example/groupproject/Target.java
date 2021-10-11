package com.example.groupproject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Target extends Object {
    //What player is aiming to hit

    //Default color of the target
    private final Paint color_default = new Paint();
    //Outline of the target
    private final Paint outline_default = new Paint();

    public Target(int x, int y, int radius) {
        super(x, y, radius);
        //Set the colors
        color_default.setColor(Color.argb(255, 255, 000, 000));
        outline_default.setStyle(Paint.Style.STROKE);
        outline_default.setColor(Color.WHITE);
        outline_default.setStrokeWidth(10);
    }

    public void draw(Canvas canvas){
        //Draw the ball
        canvas.drawCircle(getX(), getY(), getRadius(), color_default);
        canvas.drawCircle(getX(), getY(), getRadius(), outline_default);
    }


}
