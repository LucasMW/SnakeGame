package com.menezesworks.snake001;



import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {


    private GameThread gameThread;

    private Sprite gameOver;
    private SnakeSprite snakePlayer;
    public Grid2DSprite grid2DSprite;
    private MainActivity ref;

    public void setActivity(MainActivity ref) {
        this.ref = ref;
    }

    public int screenWidth,screenHeight;

    public GameSurface(Context context)  {
        super(context);

        // Make Game Surface focusable so it can handle events. .
        this.setFocusable(true);

        // Sét callback.
        this.getHolder().addCallback(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        System.out.println("width: "+ screenWidth+" height: "+screenHeight);
    }

    public void update()  {


        this.snakePlayer.update();
    }

    public void notifyStop() {
        this.gameThread.setRunning(false);
        //this.ref.finish();

    }
    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);

        if(snakePlayer.checkGameOver()){
            this.gameOver.draw(canvas);
        }
        this.grid2DSprite.draw(canvas);
        this.snakePlayer.draw(canvas);

    }
    float x1 = 0,y1 = 0;
    public boolean onTouchEvent(MotionEvent motionEvent) {
            float x2,y2;
            float MIN_DISTANCE = 150;
//            switch(motionEvent.getAction())
//            {
//                case MotionEvent.ACTION_DOWN:
//                    x1 = motionEvent.getX();
//                    y1 = motionEvent.getY();
//                    break;
//                case MotionEvent.ACTION_UP:
//                    x2 = motionEvent.getX();
//                    y2 = motionEvent.getY();
//                    float deltaX = x2 - x1;
//                    float deltaY = y2-y1;
//                    System.out.println("DeltaX "+ deltaX + " DeltaY "+deltaY);
//                    if(deltaX > MIN_DISTANCE || deltaY > MIN_DISTANCE) {
//                        if (deltaX > deltaY) {
//                            if(deltaX > 0){
//                                snakePlayer.sendCommand(SnakeSprite.CMDs.goRight);
//                            } else {
//                                snakePlayer.sendCommand(SnakeSprite.CMDs.goLeft);
//                            }
//                        } else {
//                            if(deltaY > 0){
//                                snakePlayer.sendCommand(SnakeSprite.CMDs.goUP);
//                            } else {
//                                snakePlayer.sendCommand(SnakeSprite.CMDs.goDown);
//                            }
//                        }
//                    }
//                    else
//                    {
                        System.out.println("Please turn");
                        snakePlayer.sendCommand(SnakeSprite.CMDs.turn);
//                    }
//                    break;
//            }
            return true;
    }


    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Bitmap chibiBitmap1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.chibi1);
        Bitmap squareBitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.rect);
        Bitmap gameOverBitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.gameover);
        Bitmap scaledImage = Bitmap.createScaledBitmap(squareBitmap,50,50,false);
//        gameOverBitmap = Bitmap.createScaledBitmap(gameOverBitmap,screenWidth,screenHeight,false);
        this.grid2DSprite = new Grid2DSprite(this,squareBitmap,this.screenWidth,screenHeight,30,30);
        this.gameOver = new Sprite(this,gameOverBitmap,0,0);
        this.snakePlayer = new SnakeSprite(this,scaledImage,screenWidth/2,screenHeight/2);
        this.gameThread = new GameThread(this,holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry= true;
        while(retry) {
            try {
                this.gameThread.setRunning(false);

                // Parent thread must wait until the end of GameThread.
                this.gameThread.join();
            }catch(InterruptedException e)  {
                e.printStackTrace();
            }
            retry= true;
        }
    }

}