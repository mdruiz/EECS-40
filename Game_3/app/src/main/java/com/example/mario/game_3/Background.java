package com.example.mario.game_3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by Gaurav Venkatesh on 5/11/2015.
 */
public class Background {

    private final SuperMarioSuperView smsv;
    Bitmap back, back1, back2,back3, Dpad, ground, ground1,ground2,ground3,
            butA, mushroom, brick, pipe, goombaL, castle, booL, turtle;
    int Xco, Xbco;
    private Obstacle obstacle;
    private Enemy enemyobject;
    private MarioChar mario;
    private ArrayList<Obstacle> obj ;

    private ArrayList<Enemy> enemyarray;
    public Background(SuperMarioSuperView smsv)
    {
        this.smsv=smsv;
        mario = new MarioChar(smsv);
        obstacle = new Obstacle(smsv);
        enemyobject = new Enemy(smsv);
        this.obj = new ArrayList<Obstacle>();
        this.enemyarray = new ArrayList<Enemy>();
        BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inSampleSize = 8;
        back1 = BitmapFactory.decodeResource(smsv.getResources(), R.drawable.background1, options);
        back2 = BitmapFactory.decodeResource(smsv.getResources(), R.drawable.background2, options);
        back3 = BitmapFactory.decodeResource(smsv.getResources(), R.drawable.background3, options);
        Dpad = BitmapFactory.decodeResource(smsv.getResources(), R.drawable.dpad1, options);
        ground1 = BitmapFactory.decodeResource(smsv.getResources(), R.drawable.woodsprite, options);
        ground2 = BitmapFactory.decodeResource(smsv.getResources(), R.drawable.grassk, options);
        ground3 = BitmapFactory.decodeResource(smsv.getResources(), R.drawable.watersprite, options);
        butA = BitmapFactory.decodeResource(smsv.getResources(), R.drawable.unnamed, options);
        brick = BitmapFactory.decodeResource(smsv.getResources(),R.drawable.brick, options);
        pipe = BitmapFactory.decodeResource(smsv.getResources(),R.drawable.pipe, options);
        goombaL = BitmapFactory.decodeResource(smsv.getResources(),R.drawable.goombaleft, options);
        castle = BitmapFactory.decodeResource(smsv.getResources(),R.drawable.castleone, options);
        booL = BitmapFactory.decodeResource(smsv.getResources(),R.drawable.booleft, options);
        turtle = BitmapFactory.decodeResource(smsv.getResources(),R.drawable.troopa, options);
        mushroom = BitmapFactory.decodeResource(smsv.getResources(), R.drawable.mushimushi, options);
    }

    public void Draw(Canvas c, int Mariostate, int level)
    {
        //Draw The Background
        Paint paint = new Paint();
        int Height = smsv.getHeight();
        int Width = smsv.getWidth();

        Rect dst = new Rect(Xbco, 0, Width + Xbco, Height);
        Rect dst2 = new Rect(Width + Xbco, 0, (2 * Width) + Xbco, Height);
        Rect dst0 = new Rect(-Width + Xbco, 0, Xbco, Height);
        if (level == 1){
            back = back1;
        }
        else if (level == 2){
            back = back2;
        }
        else if ( level == 3) {
            back = back3;
        }
        c.drawBitmap(back, null, dst, paint);
        c.drawBitmap(back, null, dst2, paint);
        c.drawBitmap(back, null, dst0, paint);

        if (Mariostate > 0) {
            MoveBagBack();
        } else if (Mariostate < 0) {
            MoveBagFront();
        }
    }

    public void MoveBack()
    {
        int Width = smsv.getWidth();
        int speedscreen = (smsv.getWidth()) / 150;
        Xco = Xco - speedscreen;

        if (Xco < (-Width)) {
            Xco = 0;
        }
        //iterate through the ArrayList and move it to the left
        obstacle.moveObjBack(obj,speedscreen);
        enemyobject.moveEnemyBack(enemyarray,speedscreen);
    }

    public void MoveFront() {
        int Width = smsv.getWidth();
        int speedscreen = (smsv.getWidth()) / 150;
        Xco = Xco + speedscreen;

        if (Xco > Width) {
            Xco = 0;
        }
        //iterate through the ArrayList and move it to the right
        obstacle.moveObjFront(obj,speedscreen);
        enemyobject.moveEnemyFront(enemyarray,speedscreen);
    }

    public void Dpad(Canvas c) {
        Paint paint1 = new Paint();
        int Dpad_H = (smsv.getHeight() - smsv.getHeight() / 3);
        int Dpad_W = smsv.getWidth() / 5;
        Rect Dpader = new Rect(0, Dpad_H, Dpad_W, smsv.getHeight());
        c.drawBitmap(Dpad, null, Dpader, paint1);
    }

    public void Abutton(Canvas c) {
        Paint paint1 = new Paint();
        int Height = smsv.getHeight();
        int Width = smsv.getWidth();
        int Dpad_H = (smsv.getHeight() - smsv.getHeight() / 6);
        int Dpad_W = Width - smsv.getWidth() / 10;
        Rect RecA = new Rect(Dpad_W, Dpad_H, Width, Height);
        c.drawBitmap(butA, null, RecA, paint1);

    }

    public void Grass(Canvas c, int Mariostate, int level) {
        Paint paint = new Paint();
        int Height = smsv.getHeight();
        int Width = smsv.getWidth();
        Rect sprite = new Rect(Xco, Height - Height / 5, Width + Xco, Height);
        Rect sprite2 = new Rect(Width + Xco, Height - Height / 5, (2 * Width) + Xco, Height);
        Rect sprite0 = new Rect(-Width + Xco, Height - Height / 5, Xco, Height);

        if (level == 1){
            ground = ground1;
        }
        else if (level == 2){
            ground = ground2;
        }
        else if ( level == 3) {
            ground = ground3;
        }
        c.drawBitmap(ground, null, sprite, paint);
        c.drawBitmap(ground, null, sprite2, paint);
        c.drawBitmap(ground, null, sprite0, paint);

        if (Mariostate > 0) {
            MoveBack();
        } else if (Mariostate < 0) {
            MoveFront();
        }
    }

    public void MoveBagBack() {
        int Width = smsv.getWidth();
        int speedscreen = (smsv.getWidth()) / 300;
        Xbco = Xbco - speedscreen;

        if (Xbco < (-Width)) {
            Xbco = 0;
        }
    }

    public void MoveBagFront() {
        int Width = smsv.getWidth();
        int speedscreen = (smsv.getWidth()) / 300;
        Xbco = Xbco + speedscreen;
        if (Xbco > Width) {
            Xbco = 0;
        }
    }

    public void genObstacles(Canvas c,int flag, int level){
        int Width = smsv.getWidth();
        int dX = smsv.getWidth()/15;
        if (flag == 0) {
            mario.mariochangesize();
        }
        if (flag == 0 && level == 1) {
            Obstacle temp;
            Enemy enemytemp;
            //brick
            temp = new Obstacle(smsv);
            temp.setInit(brick, Width + dX, smsv.getHeight() / 2);
            obj.add(temp);
            //brick
            temp = new Obstacle(smsv);
            temp.setInit(brick, Width + 2 * dX, smsv.getHeight() / 2);
            obj.add(temp);
            //brick
            temp = new Obstacle(smsv);
            temp.setInit(brick, Width + 3 * dX, smsv.getHeight() / 2);
            obj.add(temp);
            //question block
            temp = new Obstacle(smsv);
            temp.setInit(mushroom, Width + 2 * dX, smsv.getHeight() / 6);
            obj.add(temp);
            //pipe1
            temp = new Obstacle(smsv);
            temp.setInit(pipe, Width + 7 * dX, 3 * smsv.getHeight() / 5);
            obj.add(temp);
            //goomba1
            enemytemp = new Enemy(smsv);
            enemytemp.setInit(goombaL, Width + 11 * dX, 27 * smsv.getHeight() / 40);
            enemytemp.setRange(2);
            enemyarray.add(enemytemp);
            //pipe2
            temp = new Obstacle(smsv);
            temp.setInit(pipe,Width + 14*dX,3*smsv.getHeight()/5);
            obj.add(temp);
            //brick
            temp = new Obstacle(smsv);
            temp.setInit(brick,Width + 18*dX,smsv.getHeight()/2);
            obj.add(temp);
            //brick
            temp = new Obstacle(smsv);
            temp.setInit(brick,Width + 19*dX,smsv.getHeight()/2);
            obj.add(temp);
            //pipe3
            temp = new Obstacle(smsv);
            temp.setInit(pipe,Width + 22*dX,3*smsv.getHeight()/5);
            obj.add(temp);
            //goomba2
            enemytemp = new Enemy(smsv);
            enemytemp.setInit(goombaL,Width + 27*dX, 27*smsv.getHeight()/40);
            enemytemp.setRange(3);
            enemyarray.add(enemytemp);
            //goomba3
            enemytemp = new Enemy(smsv);
            enemytemp.setInit(goombaL,Width + 27*dX, 27*smsv.getHeight()/40);
            enemytemp.setRange(3);
            enemytemp.setDX(-enemytemp.getDX());
            enemyarray.add(enemytemp);
            //pipe4
            temp = new Obstacle(smsv);
            temp.setInit(pipe,Width + 31*dX,3*smsv.getHeight()/5);
            obj.add(temp);
            //CASTLE
            temp = new Obstacle(smsv);
            temp.setInit(castle,Width + 45*dX,smsv.getHeight()/4);
            obj.add(temp);
        }
        else if (flag == 0 && level == 2){
            Obstacle temp;
            Enemy enemytemp;
            //pipe
            temp = new Obstacle(smsv);
            temp.setInit(pipe, Width + dX, 3 * smsv.getHeight() / 5);
            obj.add(temp);
            //brick
            temp = new Obstacle(smsv);
            temp.setInit(brick,Width + 5*dX,smsv.getHeight()/2);
            obj.add(temp);
            //question block
            temp = new Obstacle(smsv);
            temp.setInit(mushroom, Width + 6 * dX, smsv.getHeight() / 2);
            obj.add(temp);
            //brick
            temp = new Obstacle(smsv);
            temp.setInit(brick,Width + 7*dX,smsv.getHeight()/2);
            obj.add(temp);
            //boo
            enemytemp = new Enemy(smsv);
            enemytemp.setInit(booL,Width + 6*dX, 27*smsv.getHeight()/40);
            enemytemp.setRange(3);
            enemyarray.add(enemytemp);
            //goomba
            enemytemp = new Enemy(smsv);
            enemytemp.setInit(goombaL,Width + 6*dX, 27*smsv.getHeight()/40);
            enemytemp.setRange(3);
            enemytemp.setDX(-enemytemp.getDX());
            enemyarray.add(enemytemp);
            //pipe
            temp = new Obstacle(smsv);
            temp.setInit(pipe, Width + 10* dX, 3 * smsv.getHeight() / 5);
            obj.add(temp);
            //brick
            temp = new Obstacle(smsv);
            temp.setInit(brick,Width + 16*dX,smsv.getHeight()/2);
            obj.add(temp);
            //brick
            temp = new Obstacle(smsv);
            temp.setInit(brick,Width + 17*dX,smsv.getHeight()/2);
            obj.add(temp);
            //tall pipe
            temp = new Obstacle(smsv);
            temp.setInit(pipe, Width + 19* dX, 2 * smsv.getHeight() / 5);
            obj.add(temp);
            //goomba
            enemytemp = new Enemy(smsv);
            enemytemp.setInit(goombaL,Width + 15*dX, 27*smsv.getHeight()/40);
            enemytemp.setRange(3);
            enemyarray.add(enemytemp);
            //boo
            enemytemp = new Enemy(smsv);
            enemytemp.setInit(booL,Width + 15*dX, 27*smsv.getHeight()/40);
            enemytemp.setRange(3);
            enemytemp.setDX(-enemytemp.getDX());
            enemyarray.add(enemytemp);
            //boo
            enemytemp = new Enemy(smsv);
            enemytemp.setInit(booL,Width + 24*dX, 27*smsv.getHeight()/40);
            enemytemp.setRange(3);
            enemytemp.setDX(-enemytemp.getDX());
            enemyarray.add(enemytemp);
            //CASTLE
            temp = new Obstacle(smsv);
            temp.setInit(castle,Width + 30*dX,smsv.getHeight()/4);
            obj.add(temp);
        }
        else if (flag == 0 && level == 3){
            Obstacle temp;
            Enemy enemytemp;
            //pipe
            temp = new Obstacle(smsv);
            temp.setInit(pipe, Width + 3* dX, 3 * smsv.getHeight() / 5);
            obj.add(temp);
            //troopa
            enemytemp = new Enemy(smsv);
            enemytemp.setInit(turtle,Width + 1*dX, 7* smsv.getHeight()/16);
            enemytemp.setRange(3);
            enemytemp.setDX(-enemytemp.getDX());
            enemyarray.add(enemytemp);
            //brick
            temp = new Obstacle(smsv);
            temp.setInit(brick,Width + 7*dX,smsv.getHeight()/2);
            obj.add(temp);
            //brick
            temp = new Obstacle(smsv);
            temp.setInit(brick,Width + 8*dX,smsv.getHeight()/2);
            obj.add(temp);
            //pipe
            temp = new Obstacle(smsv);
            temp.setInit(pipe, Width + 10* dX, 3 * smsv.getHeight() / 5);
            obj.add(temp);
            //goomba
            enemytemp = new Enemy(smsv);
            enemytemp.setInit(goombaL,Width + 7*dX, 27*smsv.getHeight()/40);
            enemytemp.setRange(2);
            enemyarray.add(enemytemp);
            //brick
            temp = new Obstacle(smsv);
            temp.setInit(brick, Width + 14*dX, smsv.getHeight() / 2);
            obj.add(temp);
            //brick
            temp = new Obstacle(smsv);
            temp.setInit(mushroom, Width + 15 * dX, smsv.getHeight() / 2);
            obj.add(temp);
            //brick
            temp = new Obstacle(smsv);
            temp.setInit(brick, Width + 16 * dX, smsv.getHeight() / 2);
            obj.add(temp);
            //question block
            temp = new Obstacle(smsv);
            temp.setInit(brick, Width + 15 * dX, smsv.getHeight() / 6);
            obj.add(temp);
            //troopa
            enemytemp = new Enemy(smsv);
            enemytemp.setInit(turtle,Width + 15*dX, 3* smsv.getHeight()/8);
            enemytemp.setRange(1);
            enemyarray.add(enemytemp);
            //tall pipe
            temp = new Obstacle(smsv);
            temp.setInit(pipe, Width + 19* dX, 2 * smsv.getHeight() / 5);
            obj.add(temp);
            //troopa
            enemytemp = new Enemy(smsv);
            enemytemp.setInit(turtle,Width + 24*dX, 7* smsv.getHeight()/16);
            enemytemp.setRange(3);
            enemytemp.setDX(-enemytemp.getDX());
            enemyarray.add(enemytemp);
            //troopa
            enemytemp = new Enemy(smsv);
            enemytemp.setInit(turtle, Width + 24 * dX, 2 * smsv.getHeight() / 16);
            enemytemp.setRange(3);
            enemyarray.add(enemytemp);
            //CASTLE
            temp = new Obstacle(smsv);
            temp.setInit(castle,Width + 32*dX,smsv.getHeight()/4);
            obj.add(temp);
        }//drawing obstacles
        for (int i = 0; i < obj.size(); i++) {
            Rect image;
            Obstacle currentObj = obj.get(i);
            if (currentObj.getX() < Width + dX && currentObj.getX() > -2 * dX) {
                image = obstacle.draw(currentObj.getBit(), currentObj.getX(), currentObj.getY(), c);
                currentObj.setRect(image);
            }
        }//drawing enemies
        for (int i = 0; i < enemyarray.size(); i++) {
            Rect image;
            Enemy currentObj = enemyarray.get(i);
            if (currentObj.getX() < Width + dX && currentObj.getX() > -2 * dX) {
                int movement = enemyobject.moveEnemyAI(currentObj);
                image = enemyobject.draw(currentObj.getBit(), currentObj.getX() + movement, currentObj.getY(), c);
                currentObj.setRect(image);
            }
        }
        //making and starting a new thread to check for crash with enemies
        Test testThread = new Test(enemyarray,mario.getMarioRect(),smsv);
        testThread.start();
        // making and starting a new thread to check crash with obstacles
        ObjectCheckThread ObjectThread = new ObjectCheckThread(obj,mario.getMarioRect(),smsv);
        ObjectThread.start();
        // checking if we passed the level
        if (obstacle.checkWin(obj.get(obj.size()-1))){
            int newState;
            newState = smsv.getGameState();
            if (newState == 3){
                newState = 0;
            }
            else{
                newState++;
            }
            smsv.setGameState(newState);
            deleteArray();
            smsv.setFlag();
        }
    }
    public void deleteArray(){
        int size = obj.size();
        for (int i = 0; i < size; i++){
            obj.remove(0);
        }
        size = enemyarray.size();
        for (int i = 0; i < size; i++) {
            enemyarray.remove(0);
        }

    }
}