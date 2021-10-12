package com.example.groupproject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Obstacles extends Object {

    //Interaction with the player

    //The type of power
    private int type = 0;

    //For each power
    private final Paint color_default = new Paint();
    private final Paint color_increaseSizeTarget = new Paint();
    private final Paint color_increaseSizeTargetStroke = new Paint();
    private final Paint color_speed = new Paint();
    private final Paint color_sizePlayer = new Paint();


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
            //Circle inside the obstacle
            color_increaseSizeTarget.setColor(Color.argb(255, 255, 0, 0));
            //Stroke of the circle
            color_increaseSizeTargetStroke.setStyle(Paint.Style.STROKE);
            color_increaseSizeTargetStroke.setColor(Color.WHITE);
            color_increaseSizeTargetStroke.setStrokeWidth(5);
            //Draw the circle, mini-target, and stroke
            canvas.drawCircle(getX(), getY(), getRadius(), color_default);
            canvas.drawCircle(getX(), getY(), getRadius()-20, color_increaseSizeTarget);
            canvas.drawCircle(getX(), getY(), getRadius()-20, color_increaseSizeTargetStroke);

        }else if (type == 2){
            //Increase Speed (2x)
            String text = "2x";
            //Text properties
            color_speed.setColor(Color.BLACK);
            color_speed.setTextSize(60f);
            color_speed.setTextAlign(Paint.Align.CENTER);
            //Draw circle and text
            canvas.drawCircle(getX(), getY(), getRadius(), color_default);
            canvas.drawText(text, getX(), getY()+15, color_speed);

        }else if (type == 3){
            //Increase Player Size (circle inside without filling)
            //Set the circle
            color_sizePlayer.setStyle(Paint.Style.STROKE);
            color_sizePlayer.setColor(Color.BLACK);
            color_sizePlayer.setStrokeWidth(10);
            //Draw the circle
            canvas.drawCircle(getX(), getY(), getRadius(), color_default);
            canvas.drawCircle(getX(), getY(), getRadius()-15, color_sizePlayer);

        }else{
            //Default
            canvas.drawCircle(getX(), getY(), getRadius(), color_default);
        }
    }

    public int getType() {
        return type;
    }
}
