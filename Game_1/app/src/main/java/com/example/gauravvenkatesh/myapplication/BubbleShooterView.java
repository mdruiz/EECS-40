package com.example.gauravvenkatesh.myapplication;

/**
 * Created by Ahmed on 3/24/2015.
 */

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.content.Context;

import java.util.Random;

import java.util.Random;


// This is the ``game engine ''.
public class BubbleShooterView extends SurfaceView
        implements SurfaceHolder.Callback {
    private RigidBall ball;  // The primordial object
    private StationBalls station;
    private BubbleShooterThread bst;
    Canvas d;

    int i;
    int j;
    public static int gamestate = 0;
    int x;
    int y;
    int LineState;
    int xpo,ypo;
    RigidBall[][] bubbles = new RigidBall[14][10];
  //  int rand = new Random().nextInt(colors.length);


    public BubbleShooterView(Context context) {
        super(context);
        Thread bst;
        //ball = new RigidBall(this);
// Notify the SurfaceHolder that you'd like to receive
// SurfaceHolder callbacks.
        getHolder().addCallback(this);
        setFocusable(true);
        ball = new RigidBall(this);
        station = new StationBalls(this);
// Initialize game state variables. DON'T RENDER THE GAME YET.


        for (i = 0; i < 7; i++) {
            for (j = 0; j < 10; j++) {
                bubbles[i][j] = new RigidBall(this);
                bubbles[i+7][j] = null;
            }
        }
        for (i = 1; i < 7; i+=2) {
                bubbles[i][9] = null;
            }


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Construct game initial state (bubbles , etc.)
        // Launch animator thread.
        BubbleShooterThread bst = new BubbleShooterThread(this);
        bst.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder,
                               int format, int width, int height) {
// Respond to surface changes , e.g., aspect ratio changes.
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // The cleanest way to stop a thread is by interrupting it.
        // BubbleShooterThread regularly checks its interrupt flag.
        bst.interrupt();
    }

    @Override
    public void onDraw(Canvas d) {
        super.onDraw(d);
        renderGame(d);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
// Update game state in response to events:
// touch -down , touch -up, and touch -move.
// Current finger position.
        int curX = (int) e.getX();
        int curY = (int) e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
// Update Game State.
                break;
            case MotionEvent.ACTION_MOVE:
                LineState =1;
                xpo = curX;
                ypo = curY;
// Update Game State.
                break;
            case MotionEvent.ACTION_UP:
// Update Game State.
                x = curX;
                y = curY;
                gamestate = 1;
                LineState=0;
                break;
        }
        return true;
    }

    public void advanceFrame(Canvas c) {
// Update game state to animate moving or exploding bubbles
// (e.g., advance location of moving bubble).
        renderGame(c);
    }
    protected void renderGame(Canvas d) {
// Render the game elements: bubbles (fixed , moving , exploding)
// and aiming arrow.
        Paint paint1;
        paint1 = new Paint();
        paint1.setStyle(Paint.Style.FILL);
        paint1.setColor(Color.WHITE);
        paint1.setAntiAlias(true);

        d.drawPaint(paint1);
        int Xco = 0, Yco = 0;
        for (i = 0; i < 14; i++) {
            for (j = 0; j < 10; j++) {
                if (bubbles[i][j] != null) {
                    Xco = (bubbles[i][j].getR()) * j * 2;
                    Yco = ((bubbles[i][j].getR() - 2) * i * 2);
                    if (i % 2 == 1) {
                        Xco += bubbles[i][j].getR();
                    }
                    bubbles[i][j].draw(d, Xco, Yco);
                }
            }
            Xco = 0;
        }

        if(LineState==1)
        {
            ball.Lines(d,xpo,ypo);
        }

        if (bubbles[13][4] != null){
            gamestate = -1;
        }
        int flag = 0;
        for (i = 0; i < 14; i++) { // checks if the player has won yet.
            for (j = 0; j < 10; j++) {
                if (bubbles[i][j] != null) {
                    flag = 1;
                    break;
                }
            }
        }
        if (flag == 0) { // if player won, reset game
            gamestate = -1;
        }

        if (gamestate == 0) { // draw new ball to shoot
            ball.drawshootball(d);
        }
        else if (gamestate == 1) { // shot ball hasn't crashed yet. keep moving it.
            gamestate = ball.shoot(d,x,y, bubbles);
        }
        else if (gamestate == 2){ // ball crashed, make new random ball to shoot
            ball = new RigidBall(this);
            gamestate = 0;
        }
        else if (gamestate == -1){ //resets game
            for (i = 0; i < 7; i++) {
                for (j = 0; j < 10; j++) {
                    bubbles[i][j] = new RigidBall(this);
                    bubbles[i+7][j] = null;
                }
            }
            for (i = 1; i < 7; i+=2) {
                bubbles[i][9] = null;
            }
            gamestate =0;
        }

    }
}


