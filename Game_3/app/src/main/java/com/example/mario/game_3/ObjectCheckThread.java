package com.example.mario.game_3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by Mario on 5/17/2015.
 */
public class ObjectCheckThread extends Thread {
    private final SuperMarioSuperView smsv;
    private static boolean crash = false;
    private ArrayList<Obstacle> obj;
    private Rect marioRect;
    private MarioChar mario;
    Bitmap mushroom;

    public ObjectCheckThread(ArrayList<Obstacle> obj,Rect marioRect,SuperMarioSuperView smsv ){
        this.obj = obj;
        this.marioRect = marioRect;
        this.smsv = smsv;
        BitmapFactory.Options options = new BitmapFactory.Options();
        mushroom = BitmapFactory.decodeResource(smsv.getResources(), R.drawable.mushimushi, options);
    }

    @Override
    public void run(){
        checkBottom(obj,marioRect);
        //System.out.println(crash);
        //System.out.println("Size of array list is : "+obj.size());
        if (crash == false && obj.size() >0 && obj.get(0).getJumpFlag() !=1 ){
            obj.get(0).startFalling();
        }
        crash=false;
        checkhitblock(obj, marioRect);
        checkCrash(obj, marioRect);
        if (crash == true){
            //System.out.println("crashed into an object!!!!");
            crash = false;
            smsv.setMariostate(0);
        }
    }

    public void checkCrash(ArrayList<Obstacle> obstacle, Rect marioRect){
        boolean check;
        for (int i = 0; i < obstacle.size(); i++) {
            Obstacle currentObj = obstacle.get(i);
            check = intersectRect(marioRect, currentObj.getRect());
            if (check == true ){
                //System.out.println("Mario : " +marioRect);
                //System.out.println("Enemy : " +currentObj.getRect());

            }
            crash = (crash || check);
        }
    }

    public boolean intersectRect(Rect r1, Rect r2) {
        if (r2 != null) {
            return !(r2.left > r1.right ||
                    r2.right < r1.left ||
                    r2.top > r1.bottom-1 ||
                    r2.bottom  < r1.top);
        }
        else{
            return false;
        }
    }

    public void checkhitblock(ArrayList<Obstacle> obstacles, Rect marioRect) {
        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle currentObj = obstacles.get(i);
            if (currentObj.getRect() != null) {
                if (marioRect.top > currentObj.getRect().bottom - 10) {
                    boolean check1 = intersectRect(currentObj.getRect(), marioRect);
                    if (check1 == true) {
                        if (currentObj.getBit().sameAs(mushroom)) {
                            currentObj.mariostatechange();
                        } else currentObj.killblock(currentObj);

                    }
                }
            }
        }
    }

    public void checkBottom (ArrayList<Obstacle> obstacles, Rect marioRect){
        boolean check;
        crash = false;
        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle currentObj = obstacles.get(i);
            if (currentObj.getRect() != null) {
                if (marioRect.bottom <= currentObj.getRect().top) {
                    //System.out.println("Entered loop");
                    int left = currentObj.getRect().left;
                    int right = currentObj.getRect().right;
                    int bottom = currentObj.getRect().top;
                    int top = bottom - ((currentObj.getRect().bottom - bottom) / 4);
                    Rect tempRect = new Rect(left, top, right, bottom);
                    //System.out.println("temp : "+tempRect);
                    //System.out.println("enemy :" +currentObj.getRect());
                    check = intersectRect(marioRect, tempRect);
                    //System.out.println(currentObj.getOffset());

                    crash = (crash || check);//*
                    if (obj.get(0).getJumpFlag() == 1){
                        check = false;
                        //System.out.println("mario should jump!!");
                        //currentObj.stopFalling(currentObj);
                        //System.out.println(+marioRect.bottom+ " sdgf "+currentObj.getRect().top);

                    }//*/

                    if (check == true) {
                        //System.out.println("Mario should stop falling now!!!!");
                        currentObj.stopFalling(currentObj);
                        currentObj.setdY();
                    }
                }
            }
        }
    }
}
