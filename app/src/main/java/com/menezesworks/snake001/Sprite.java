package com.menezesworks.snake001;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Sprite extends GameObject {

    private static final int ROW_TOP_TO_BOTTOM = 0;
    private static final int ROW_RIGHT_TO_LEFT = 1;
    private static final int ROW_LEFT_TO_RIGHT = 2;
    private static final int ROW_BOTTOM_TO_TOP = 3;

    // Row index of Image are being used.
    private int rowUsing = ROW_LEFT_TO_RIGHT;

    private int colUsing;

    private Bitmap bitmap;

    // Velocity of game character (pixel/millisecond)


    private long lastDrawNanoTime =-1;

    private GameSurface gameSurface;

    public Sprite(GameSurface gameSurface, Bitmap image, int x, int y) {
        super(image, 1, 1, x, y);
        this.bitmap = image;
        this.gameSurface= gameSurface;
}



    public Bitmap getCurrentMoveBitmap()  {
        return this.bitmap;
    }


    public void update()  {

    }

    public void draw(Canvas canvas)  {
        Bitmap bitmap = this.getCurrentMoveBitmap();
        canvas.drawBitmap(bitmap,x, y, null);
        // Last draw time.
        this.lastDrawNanoTime= System.nanoTime();
    }

}
