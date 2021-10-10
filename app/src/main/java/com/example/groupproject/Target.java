package com.example.groupproject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Target extends Object {
    //What player is aiming to hit

    //TODO
    //Need to add outline

    private final int x;
    private final int y;
    private final int radius;
    private final Paint color_default = new Paint();

    public Target(int x, int y, int radius) {
        super(x, y, radius);
        this.x = x;
        this.y = y;
        this.radius = radius;

        color_default.setColor(Color.argb(255, 255, 000, 000));
    }

    public void draw(Canvas canvas){
        canvas.drawCircle(x, y, radius, color_default);
    }


}
