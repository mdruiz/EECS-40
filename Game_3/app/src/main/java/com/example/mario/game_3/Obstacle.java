package com.example.mario.game_3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by Gaurav Venkatesh on 5/13/2015.
 */
public class Obstacle {
    private final SuperMarioSuperView smsv;
    private int locx;
    private int locy;
    private Bitmap mushroom, pipe, blank, brick, bit, castle;
    private int destroyed;
    private Rect OBSRect;
    private MarioChar mario;

    public Obstacle(SuperMarioSuperView smsv) {
        this.smsv = smsv;
        this.locx = locx;
        this.locy = locy;
        this.destroyed = 0;
        mario = new MarioChar(smsv);
        this.bit = bit;
        BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inSampleSize = 8;
        brick = BitmapFactory.decodeResource(smsv.getResources(),R.drawable.brick, options);
        pipe = BitmapFactory.decodeResource(smsv.getResources(),R.drawable.pipe, options);
        castle = BitmapFactory.decodeResource(smsv.getResources(),R.drawable.castleone, options);
        mushroom = BitmapFactory.decodeResource(smsv.getResources(), R.drawable.mushimushi, options);
    }

    public Rect draw(Bitmap bitmap, int x, int y, Canvas c) {
        //System.out.println(x);
        //Simply makes the object in the right location.
        Paint paint = new Paint();
        int Height = smsv.getHeight();
        int Width = smsv.getWidth();
        int y1,x1;
        if (bitmap.sameAs(brick) || bitmap.sameAs(mushroom)) {
            y1 = y + Height / 8;
            x1 = x + Width / 15;
        }
        else if (bitmap.sameAs(pipe)){
            y1 = 4*smsv.getHeight()/5;
            x1 = x + Width/10;
        }
        else if (bitmap.sameAs(castle)){
            y1 = 4*smsv.getHeight()/5;
            x1 = x + Width/3;
        }
        else{
            y1= y;
            x1=x;
        }
        OBSRect = new Rect(x, y, x1, y1);
        c.drawBitmap(bitmap, null, OBSRect, paint);
        return  OBSRect;
    }
    public Rect getRect()
    {
        return OBSRect;
    }

    public void setRect(Rect OBSRect) {
        this.OBSRect = OBSRect;
    }

    public int getX(){
        return locx;
    }

    public int getY(){
        return locy;
    }

    public Bitmap getBit(){
        return bit;
    }

    public void setInit(Bitmap bit , int X , int Y){
        this.bit = bit;
        this.locx = X;
        this.locy = Y;
    }

    public void setX(int X){
        this.locx = X;
    }

    public void moveObjBack(ArrayList<Obstacle> obj, int speed){
        for (int i = 0; i<obj.size();i++){
            Obstacle currentObj = obj.get(i);
            int newX = currentObj.getX() - speed;
            currentObj.setX(newX);
        }
    }

    public void moveObjFront(ArrayList<Obstacle> obj, int speed){
        for (int i = 0; i<obj.size();i++){
            Obstacle currentObj = obj.get(i);
            int newX = currentObj.getX() + speed;
            currentObj.setX(newX);
        }
    }
    public boolean checkWin(Obstacle obj){
        if (obj.getX() <= smsv.getWidth()/2){
            return true;
        }
        else{
            return false;
        }
    }
    public void stopFalling(Obstacle object){
        int type;
        Bitmap temp = object.bit;
        if (temp.sameAs(pipe)){
            type = 1;
        }
        else if (temp.sameAs((brick))){
            type = 2;
        }
        else {
            type = 0;
        }
        mario.stopFalling(object, type);
    }
    public void startFalling(){
        mario.startFalling();
    }

    public int getOffset(){
        return mario.getOffset();
    }
    public void setdY(){
        mario.setdY();
    }
    public int getJumpFlag(){
        return mario.getJumpFlag();
    }

    public void killblock(Obstacle obstacle) {

        obstacle.locy = smsv.getHeight();
    }

    public void mariostatechange() {
        mario.mariostatechange();
    }

    ;
}
