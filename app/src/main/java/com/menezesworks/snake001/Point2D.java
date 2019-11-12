package com.menezesworks.snake001;

public class Point2D {
    int x,y;
    Point2D(int x,int y){
        this.x = x;
        this.y = y;
    }
    Point2D Zero() {
        return new Point2D(0,0);
    }
}
