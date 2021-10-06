package com.example.groupproject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Target extends Object {
    //What player is aiming to hit

    private final int x;
    private final int y;
    private final int radius;
    private final Paint color = new Paint();

    public Target(int x, int y, int radius) {
        super(x, y, radius);
        this.x = x;
        this.y = y;
        this.radius = radius;
        color.setColor(R.color.teal_200);
    }

    public void draw(Canvas canvas){
        canvas.drawCircle(x, y, radius, color);
    }


}
