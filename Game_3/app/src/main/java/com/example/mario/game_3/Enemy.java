package com.example.mario.game_3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by Gaurav Venkatesh on 5/14/2015.
 */
public class Enemy {
    private final SuperMarioSuperView smsv;
    private int locx;
    private int locy;
    int flagg = 0;
    private int dX;
    private int range;
    private int displacement;
    private Bitmap goombaR, goombaL, booR, booL, bit,turtle;
    private int destroyed;
    public int Xbco;
    private Rect OBSRect;
    public static int score;
    private MarioChar mario;
    public Enemy(SuperMarioSuperView smsv) {
        this.smsv = smsv;
        mario = new MarioChar(smsv);
        this.locx = locx;
        this.locy = locy;
        this.destroyed = 0;
        this.bit = bit;
        this.dX = smsv.getWidth()/210;
        BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inSampleSize = 8;
        turtle = BitmapFactory.decodeResource(smsv.getResources(),R.drawable.troopa, options);
    }

    public Rect draw(Bitmap bitmap, int x, int y, Canvas c) {
        Paint paint = new Paint();
        int Height = smsv.getHeight();
        int Width = smsv.getWidth();
        int y1;
        int x1;
        if (bitmap.sameAs(turtle)) {
            y1 = y + Height / 8;
            x1 = x + Width / 15;
        } else {
            y1 = 4 * smsv.getHeight() / 5;
            x1 = x + Width / 15;
        }
        OBSRect = new Rect(x, y, x1, y1);
        c.drawBitmap(bitmap, null, OBSRect, paint);


        return OBSRect;

    }

    public Rect getRect()
    {
        return OBSRect;
    }

    public void setRect(Rect OBSRect) {
        this.OBSRect = OBSRect;
    }


    public int getX() {
        return locx;
    }

    public int getY() {
        return locy;
    }

    public Bitmap getBit() {
        return bit;
    }

    public  int getDX(){
        return dX;
    }

    public  int getRange(){
        return range;
    }

    public int getDisplacement(){
        return displacement;
    }

    public void setInit(Bitmap bit, int X, int Y) {
        this.bit = bit;
        this.locx = X;
        this.locy = Y;
    }

    public void setX(int X) {
        this.locx = X;
    }

    public void setDX(int X) {
        this.dX = X;
    }

    public void setRange(int range){
        this.range = range * smsv.getWidth()/15;
    }

    public void setDisplacement(int d){
        this.displacement = d;
    }

    public void moveEnemyBack(ArrayList<Enemy> obj, int speed){
        for (int i = 0; i<obj.size();i++){
            Enemy currentObj = obj.get(i);
            int newX = currentObj.getX() - speed;
            //int newX = currentObj.getStartX() - speed;
            currentObj.setX(newX);
        }
    }

    public void moveEnemyFront(ArrayList<Enemy> obj, int speed){
        for (int i = 0; i<obj.size();i++){
            Enemy currentObj = obj.get(i);
            int newX = currentObj.getX() + speed;
            //int newX = currentObj.getStartX() + speed;
            currentObj.setX(newX);
        }
    }

    public int moveEnemyAI(Enemy enemy){
        if (enemy.displacement >= enemy.range || enemy.displacement <= -enemy.range) {
            enemy.dX = -enemy.dX;
        }
        enemy.displacement = enemy.displacement + enemy.dX;
        return enemy.displacement;
    }
    public void killEnemy(Enemy enemy){

        enemy.locy = smsv.getHeight();//enemy.locy + smsv.getHeight()/12;
        flagg = 1;

    }


    public void setscore() {
        mario.call();
    }

}
