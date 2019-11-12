package com.menezesworks.snake001;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Grid2d{
    private int rows;
    private int cols;
    private  int screenWidth,screenHeight;
    private Point2D[] allTiles;
    Grid2d(int screenWidth,int screenHeight,int rows,int cols){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.rows = rows;
        this.cols = cols;
        allTiles = new Point2D[rows*cols];
        int tileWidht = screenWidth/cols;
        int tileHeight = screenHeight/rows;
        for (int i =0; i <rows; i++){
            for (int j =0; j <cols; j++){
                allTiles[i*rows+j] = new Point2D(j*tileWidht,i*tileHeight);
            }
        }
    }
    public int getRows(){
        return rows;
    }
    public int getCols() {
        return cols;
    }
    public int getScreenWidth() {
        return screenWidth;
    }
    public int getScreenHeight() {
        return screenHeight;
    }
    public Point2D getPositionOfTile(int row, int col){
        return allTiles[row*rows+col];
    }
    public Point2D[] getAllTiles() {
        return this.allTiles;
    }
}
