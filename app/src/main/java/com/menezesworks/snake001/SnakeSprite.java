package com.menezesworks.snake001;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Collection;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class SnakeSprite extends GameObject {



    private int colUsing;
    private Grid2d gridRef;
    private Bitmap[] leftToRights;
    private Bitmap[] rightToLefts;
    private Bitmap[] topToBottoms;
    private Bitmap[] bottomToTops;

    private Point2D[] snakePositions = new Point2D[500];
    int count = 1;


    public enum CMDs {
        goUP,goDown,goLeft,goRight,turn,pause,restart;
    }

    void turn() {
        System.out.println("turned from" + direction.name());
        switch (direction){
            case UP:
                direction = Directions.RIGHT;
                break;
            case DOWN:
                direction = Directions.LEFT;
                break;
            case LEFT:
                direction = Directions.UP;
                break;
            case RIGHT:
                direction = Directions.DOWN;
                break;
        }
        System.out.println("turned to " + direction.name());
    }

    public void sendCommand(CMDs command) {
        System.out.println("Sent Command" + command.name());
        switch (command){
            case goUP:
                direction = Directions.UP;
            case goDown:
                direction = Directions.DOWN;
            case goLeft:
                direction = Directions.LEFT;
            case goRight:
                direction = Directions.RIGHT;
            case turn:
                turn();

        }

    }
    // Velocity of game character (1 tile/millisecond)
    public static final float tiles_per_frame = 0.1f;

    public enum  Directions{
        UP,DOWN,LEFT,RIGHT;
    }
    public Point2D[] movingVectors = {
            new Point2D(0,-1),
            new Point2D(0,1),
            new Point2D(-1,0),
            new Point2D(1,0)
    };
    public Point2D getVelocityVector() {
        return movingVectors[direction.ordinal()];
    }

    private int numberOfSquares = 1;

    private final int numberOfUpdatesToMove = 3;
    private  int currentNumberOfUpdates = 0;
    private long lastDrawNanoTime =-1;
    private long squarePlusInterval = 100;

    private GameSurface gameSurface;
    private Directions direction = Directions.RIGHT;


    public SnakeSprite(GameSurface gameSurface, Bitmap image, int x, int y) {

        super(image, 1, 1, x, y);

        this.gameSurface= gameSurface;
        this.direction = Directions.DOWN;
        for(int i =0; i<500;i++){
            this.snakePositions[i] = new Point2D(0,0);
        }
        this.gridRef = this.gameSurface.grid2DSprite.getGrid();
       int len = gridRef.getAllTiles().length;
       snakePositions[0]  = new Point2D(gridRef.getRows()/2,gridRef.getCols()/2);

    }

    public Bitmap[] getMoveBitmaps()  {
//        switch (rowUsing)  {
//            case ROW_BOTTOM_TO_TOP:
//                return  this.bottomToTops;
//            case ROW_LEFT_TO_RIGHT:
//                return this.leftToRights;
//            case ROW_RIGHT_TO_LEFT:
//                return this.rightToLefts;
//            case ROW_TOP_TO_BOTTOM:
//                return this.topToBottoms;
//            default:
//                return null;
//        }
        Bitmap[] arr = new Bitmap[1];
        arr[0] = image;
        return  arr;
    }


    public Bitmap getCurrentMoveBitmap()  {
        Bitmap[] bitmaps = this.getMoveBitmaps();
        return bitmaps[this.colUsing];
    }

    public boolean checkGameOver() {

        int row = snakePositions[0].x;
        int col = snakePositions[0].y;
        System.out.printf("row and col(%d,%d)\n",row,col);
        if(row < 0 ||
                row > this.gridRef.getRows()-1 ||
                col < 0 ||
        col > this.gridRef.getCols()-1){
            System.out.println("GAME OVER");
            return  true;

        }
        return  false;

    }

    public void moveBody(){
        Point2D v = getVelocityVector();
        int lastx,lasty;
        lastx = snakePositions[0].x;
        lasty = snakePositions[0].y;
        Point2D temp = snakePositions[count-1];

        for (int i = (count-1 - 1); i >= 0; i--) {
            snakePositions[i+1] = snakePositions[i];
        }

        snakePositions[0] = temp;

    }
    public void move() {
        Point2D v = getVelocityVector();
        int r = snakePositions[0].x + v.x;
        int c = snakePositions[0].y + v.y;
        snakePositions[0].x = r;
        snakePositions[0].y = c;
        System.out.printf("receiving (%d,%d)\n",v.x ,v.y);
        System.out.printf("moved to (%d,%d)",snakePositions[0].x ,snakePositions[0].y);
        moveBody();


    }
    public void increaseSnake(){
        this.snakePositions[count].x = this.snakePositions[count-1].x - getVelocityVector().x;
        this.snakePositions[count].y = this.snakePositions[count-1].y - getVelocityVector().y;
        count++;

    }

    public void update()  {

        // Current time in nanoseconds
        if(currentNumberOfUpdates % numberOfUpdatesToMove == 0){
            move();
        }
        if(currentNumberOfUpdates % 100 == 0){
            increaseSnake();
        }

        currentNumberOfUpdates++;

        if(checkGameOver()){
            this.gameSurface.notifyStop();
        }

    }

    public void draw(Canvas canvas)  {

        Bitmap bitmap = this.getCurrentMoveBitmap();
        for(int i=0;i<count;i++){
            Point2D p = gridRef.getPositionOfTile(snakePositions[i].x,snakePositions[i].y);
            canvas.drawBitmap(bitmap,p.x, p.y, null);
            System.out.println("drawing x " + snakePositions[i].x + " y: " + snakePositions[i].y);

        }

        // Last draw time.
        this.lastDrawNanoTime= System.nanoTime();
    }



}
