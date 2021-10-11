package com.example.groupproject;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Object {
    //Co-ordinates for any object
    private int x;
    private int y;
    //Radius
    private int radius;

    public Object(int x, int y, int radius){
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    //Getters and setters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    //Basic collision detection for circles
    public boolean collisionDetection(Object ball2){
        double xDif = x - ball2.x;
        double yDif = y - ball2.y;
        double distanceSquared = xDif * xDif + yDif * yDif;
        return distanceSquared < (radius + ball2.radius) * (radius + ball2.radius);
    }

}
