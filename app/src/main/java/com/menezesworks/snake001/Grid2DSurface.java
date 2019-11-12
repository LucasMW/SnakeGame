package com.menezesworks.snake001;

import android.graphics.Bitmap;
import android.graphics.Canvas;

class Grid2DSprite extends GameObject {
    private Grid2d aux;
    public boolean isGameOver;
    Grid2DSprite(GameSurface gameSurface, Bitmap image,int width,int height,int rows,int cols){
        super(image, 1, 1, 0, 0);
        this.image = Bitmap.createScaledBitmap(image,width/cols/10,height/rows/10,false);
        this.aux = new Grid2d(width,height,rows,cols);
        Point2D p = aux.getAllTiles()[aux.getAllTiles().length-1];
        System.out.printf("last tile(%d,%d)\n",p.x,p.y);




    }
    public void draw(Canvas canvas)  {
        for(Point2D tile : aux.getAllTiles() ){
            canvas.drawBitmap(this.image,tile.x, tile.y, null);
            //System.out.printf("tile(%d,%d)\n",tile.x,tile.y);
        }

    }
    public void stop(){

    }
    public Grid2d getGrid(){
        return aux;
    }
}
