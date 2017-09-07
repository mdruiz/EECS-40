package com.example.mario.game_3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Gaurav Venkatesh on 5/11/2015.
 */
public class MarioChar {
    private final SuperMarioSuperView smsv;
    Bitmap Mario,Mariomove1R,Mariomove2R,Mariomove1L,Mariomove2L;
    // What we know about the Mario Object
    int stage;
    static int life = 0;
    int x;
    int y;
    static int dY = 0;
    static int flag = 0;
    static int offset = 0;
    Paint paint = new Paint();
    static Rect MarioRect;
    public static int mariostate = 0;
    public int score = 0;
    public static int scoreflag;

    public MarioChar(SuperMarioSuperView smsv)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        Mario = BitmapFactory.decodeResource(smsv.getResources(), R.drawable.mariostand, options);
        Mariomove1R = BitmapFactory.decodeResource(smsv.getResources(), R.drawable.mario_mve1, options);
        Mariomove2R = BitmapFactory.decodeResource(smsv.getResources(), R.drawable.mariomvetwo, options);
        Mariomove1L = BitmapFactory.decodeResource(smsv.getResources(), R.drawable.mariomveonel, options);
        Mariomove2L = BitmapFactory.decodeResource(smsv.getResources(), R.drawable.mariomvetwol, options);
        this.smsv = smsv;
    }

    public void mariochangesize() {
        mariostate = 0;
    }


    public Rect getMarioRect() {
        return MarioRect;
    }
    public void drawMario(Canvas c, int MarioState, int jumpState)
    {
        // conditions for jumping
        if (jumpState == 1 && flag == 0){
            dY = smsv.getHeight()/60;
            //System.out.println("OFFSET = "+offset);
            flag =1;
        }
        int Height = smsv.getHeight();
        int Width = smsv.getWidth();
        int MHeight = Height - (Height / 3) + Height / 50;
        int MWidth = Width / 3;
        if (mariostate == 1) {
            MHeight = Height - (Height / 3) - Height / 30;
        }
        MarioRect = new Rect(MWidth, MHeight - dY - offset, MWidth + MWidth / 4, Height - dY - offset - Height / 5);
        //System.out.println("Test printing a value" +MarioRect.right);
        //checking if mario should still be moving upwards
        int limit = smsv.getHeight() / 3 + smsv.getHeight() / 100;
        if (mariostate == 1) {
            limit = smsv.getHeight() / 2;
        }
        // flag = 1 meaning mario is moving upwards
        if (flag == 1 && dY != 0 && dY < limit) {
            dY = dY + smsv.getHeight()/60;
        }
        // if not moving upwards fet flag to 2 meaning he should be falling
        else //if (dY > smsv.getHeight()/3)
        {
            flag = 2;
        }// moving mario downward
        if (flag == 2){
            dY = dY - smsv.getHeight()/60;
        }// mario has reached the floor
        if ( dY <= 0){
            flag = 0;
            dY = 0;
        }
        //drawing image of mario according to his state
        if (MarioState == 0) {
            c.drawBitmap(Mario, null, MarioRect, paint);
        }
        else if (MarioState >= 1 && MarioState <= 10) {
            c.drawBitmap(Mariomove1R, null, MarioRect, paint);
        }
        else if (MarioState >= 11 && MarioState <= 20) {
            c.drawBitmap(Mariomove2R, null, MarioRect, paint);
        } else if (MarioState == -1) {
            c.drawBitmap(Mariomove1L, null, MarioRect, paint);
        } else if (MarioState == -2) {
            c.drawBitmap(Mariomove2L, null, MarioRect, paint);
        }
        else if (MarioState <= -1 && MarioState >= -10) {
            c.drawBitmap(Mariomove1L, null, MarioRect, paint);
        }
        else if (MarioState <= -11 && MarioState >= -20) {
            c.drawBitmap(Mariomove2L, null, MarioRect, paint);
        }
        //System.out.println(life);
        MakeLife(c);

    }



    public int dpadCheck(int X, int Y){
        int H = smsv.getHeight();
        int W = smsv.getWidth();
        if (X > W/10 && X < W/5 && Y > (H - H/3) && Y < H){
            return 1;
        }
        if (X > 0 && X < W/10 && Y > (H - H/3) && Y < H) {
            return -1;
        }
        else {
            return 0;
        }
    }
    public int jumpCheck(int X, int Y){
        int Height = smsv.getHeight();
        int Width = smsv.getWidth();
        int Dpad_H = (smsv.getHeight() - smsv.getHeight() / 6);
        int Dpad_W = Width - smsv.getWidth() / 10;
        if (X>Dpad_W && X< Width && Y>Dpad_H && Y<Height){
            return 1;
        }
        else {
            return 0;
        }
    }
    public void setDifficulty(int lifes){
        life = lifes;
    }
    public int getDifficulty(){
        return life;
    }

    private void MakeLife(Canvas c) {
        int Height = smsv.getHeight();
        int Width = smsv.getWidth();
        int x = Height / 10;
        int y = Width / 20;
        int x1 = Height / 10 + Height + 7;
        int y1 = Width / 20;
        int k = life;
        if (scoreflag == 1) {
            score = score + 10;
        }
        int k2 = score;
        Paint paint = new Paint();
        paint.setTextSize(Width / 18);
        Paint paint2 = new Paint();
        paint2.setTextSize(Width / 15);
        c.drawText("Lives : " + k, x, y, paint);
        c.drawText("Score : " + k2, x1, y1, paint2);
        scoreflag = 0;
    }

    public void stopFalling(Obstacle obj, int type){
        flag = 0;
        if(type==1) {
            offset = obj.getRect().bottom - obj.getRect().top +1;
            //System.out.println(offset );
        }
        else if (type == 2){
            offset = (4* smsv.getHeight()/5) - obj.getRect().top +1;
        }
        //dY =0;
    }

    public void mariostatechange() {
        mariostate = 1;
    }

    public void call() {
        scoreflag = 1;
    }


    public void startFalling(){
        offset=0;
    }
    public int getOffset(){
        return offset;
    }
    public void setdY(){
        dY =0;
    }
    public int getJumpFlag(){
        return flag;
    }
}