package com.example.groupproject;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ball extends Object{

    //Player
    private int x;
    private int y;
    private final int radius;
    private Paint color = new Paint();

    public Ball(int x, int y, int radius) {
        super(x, y, radius);
        this.x = x;
        this.y = y;
        this.radius = radius;
        color.setColor(Color.WHITE);
    }

    @Override
    public void setX(int x) {
        this.x = x;
        super.setX(x);
    }

    @Override
    public void setY(int y) {
        this.y = y;
        super.setY(y);
    }

    public void draw(Canvas canvas){
        canvas.drawCircle(x, y, radius, color);
    }
}
