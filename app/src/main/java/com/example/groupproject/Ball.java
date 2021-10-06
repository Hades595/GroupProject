package com.example.groupproject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ball extends Object{

    //Player

    private final int x;
    private final int y;
    private final int radius;
    private final Paint color = new Paint(Color.BLACK);

    public Ball(int x, int y, int radius) {
        super(x, y, radius);
        this.x = x;
        this.y = y;
        this.radius = radius;

    }

    public void draw(Canvas canvas){
        canvas.drawCircle(x, y, radius, color);
    }
}
