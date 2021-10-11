package com.example.groupproject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Obstacles extends Object {

    //Interaction with the player

    //The type of power
    private int type = 0;

    private final Paint color_default = new Paint();
    private final Paint color_increaseSizeTarget = new Paint();
    private final Paint color_speed = new Paint();
    private final Paint color_sizePlayer = new Paint();

    String text = "2x";


    public Obstacles(int x, int y, int radius, int type){
        super(x,y, radius);
        // validate that cents is 0 to 3
        if (type < 0 || type > 3) {
            throw new IllegalArgumentException("Invalid type value: " + type);
        }
        this.type = type;
    }

    public void draw(Canvas canvas){
        //Basic green
        color_default.setColor(Color.argb(255, 64, 212, 164));

        //Depending on the effect
        if (type == 1){
            //Increase Size of Target (circle inside with red)
            color_increaseSizeTarget.setColor(Color.argb(255, 255, 0, 0));
            canvas.drawCircle(getX(), getY(), getRadius()-15, color_increaseSizeTarget);
            canvas.drawCircle(getX(), getY(), getRadius(), color_default);

        }else if (type == 2){
            //Increase Speed (2x)
            color_speed.setColor(Color.WHITE);
            color_speed.setTextSize(18f);
            color_speed.setAntiAlias(true);
            color_speed.setTextAlign(Paint.Align.CENTER);
            Rect bounds = new Rect();
            color_speed.getTextBounds(text, 0, text.length(), bounds);
            canvas.drawText(text, -3, 15, color_speed);
            canvas.drawCircle(getX(), getY(), getRadius(), color_default);

        }else if (type == 3){
            //Increase Player Size (circle inside without filling)
            color_sizePlayer.setColor(Color.argb(255, 255, 0, 0));
            canvas.drawCircle(getX(), getY(), getRadius(), color_default);
            canvas.drawCircle(getX(), getY(), getRadius()-15, color_sizePlayer);

        }else{
            //Default
            canvas.drawCircle(getX(), getY(), getRadius(), color_default);
        }
    }

}
