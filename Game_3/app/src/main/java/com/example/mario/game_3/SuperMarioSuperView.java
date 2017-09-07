package com.example.mario.game_3;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Gaurav Venkatesh on 5/11/2015.
 */
public class SuperMarioSuperView  extends SurfaceView implements SurfaceHolder.Callback{

    private Background background;
    private MarioChar mario;
    private Obstacle obstacle;
    private Enemy enemy;
    static int Mariostate, jumpState, gameState;
    private Menu menu;
    static int flag =0;
    //private Test test;
    public  SuperMarioSuperView(Context context)
    {
        super(context);
        getHolder().addCallback(this);
    }
    @Override
    public  void  surfaceCreated(SurfaceHolder holder)
    {
        SuperMarioThread bsv = new SuperMarioThread(this);
        background = new Background(this);
        mario = new MarioChar(this);
        menu = new Menu(this);
        //obstacle = new Obstacle(this);
        //enemy = new Enemy(this);
        bsv.start();
        //test = new Test();
    }
    public void surfaceChanged(SurfaceHolder holder,
                               int format, int width, int height)
    {

    }


    public void surfaceDestroyed(SurfaceHolder holder)
    {

    }


    @Override
    public  void  onDraw(Canvas c)
    {
        super.onDraw(c);

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        int GetX = (int) e.getX();
        int GetY = (int) e.getY();
        int pointerIndex = e.getActionIndex();
        int pointerId = e.getPointerId(pointerIndex);
        int maskedAction = e.getActionMasked();

        switch (maskedAction)
        {
            case MotionEvent.ACTION_MOVE:
                // Update Game State.
                break;
            case MotionEvent.ACTION_UP:
                if (gameState > 0) {
                    Mariostate = 0;
                }
                break;
            case MotionEvent.ACTION_DOWN:
                // Update Game State.
                GetX = (int) e.getX();
                GetY = (int) e.getY();
                if (gameState > 0){
                    Mariostate = mario.dpadCheck(GetX, GetY);
                }
                else if (gameState == 0){
                    if (menu.checkDifficulty(GetX,GetY) !=0){
                        gameState = 1;
                        mario.setDifficulty(menu.checkDifficulty(GetX,GetY));
                    }
                }
            case MotionEvent.ACTION_POINTER_DOWN:
                int GetX2 = (int) e.getX(pointerIndex);
                int GetY2 = (int) e.getY(pointerIndex);
                if (gameState > 0) {
                    jumpState = mario.jumpCheck(GetX2, GetY2);
                }
        }
        return true;
    }


    public  void  tick(Canvas c) {
        if (Mariostate >= 1 && Mariostate < 20) {
            Mariostate ++ ;
        }
        else if (Mariostate == 20) {
            Mariostate = 1;
        }
        else if (Mariostate <= -1 && Mariostate > -20){
            Mariostate -- ;
        }
        else if (Mariostate == -20) {
            Mariostate = -1;
        }

        // main menu
        if (gameState == 0){
            menu.DrawWall(c);
            menu.Diff(c);
            mario.score = 0;
        }//Draws the appropriate level
        else if (gameState >= 1) {
            background.Draw(c, Mariostate,gameState);
            mario.drawMario(c, Mariostate, jumpState);
            background.Grass(c, Mariostate, gameState);
            background.genObstacles(c,flag,gameState);
            if (flag <= 0) {
                flag++;
            }
            background.Dpad(c);
            background.Abutton(c);
            jumpState = 0;

        }

    }
    public void setGameState(int state){
        gameState = state;
    }
    public int getGameState(){
        return gameState;
    }
    public void setFlag(){
        flag = -1;
    }
    public void deleteList(){
        background.deleteArray();
    }
    public void setMariostate(int state){
        Mariostate = state;
    }
}
