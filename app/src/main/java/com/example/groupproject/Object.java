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

    //Basic collision detection for circless
    public boolean collisionDetection(Object ball1, Object ball2){
        double xDif = ball1.x - ball2.x;
        double yDif = ball1.y - ball2.y;
        double distanceSquared = xDif * xDif + yDif * yDif;
        return distanceSquared < (ball1.radius + ball2.radius) * (ball1.radius + ball2.radius);
    }

}
