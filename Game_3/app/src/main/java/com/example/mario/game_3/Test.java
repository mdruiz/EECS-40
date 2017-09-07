package com.example.mario.game_3;

import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by Mario on 5/16/2015.
 */
public class Test extends Thread {
    private final SuperMarioSuperView smsv;
    private static boolean crash = false;
    private ArrayList<Enemy> enemyarray;
    private Rect marioRect;
    private MarioChar mario;

    public Test(ArrayList<Enemy> enemyarray, Rect marioRect, SuperMarioSuperView smsv) {
        this.enemyarray = enemyarray;
        this.marioRect = marioRect;
        this.smsv = smsv;
    }

    @Override
    public void run() {
        checkKill(enemyarray, marioRect);
        checkCrash(enemyarray, marioRect);
        if (crash == true) {
            mario.life = mario.life - 1;
            if (mario.life == 0){
                smsv.setGameState(0);
            }
            crash = false;
            smsv.deleteList();
            smsv.flag = 0;
        }
    }

    public void checkCrash(ArrayList<Enemy> enemy, Rect marioRect) {
        boolean check;
        for (int i = 0; i < enemy.size(); i++) {
            Enemy currentObj = enemy.get(i);
            check = intersectRect(marioRect, currentObj.getRect());
            crash = (crash || check);
        }

    }

    public void checkKill2(ArrayList<Enemy> enemy, Rect marioRect) {
        int Height = smsv.getHeight();
        int Width = smsv.getWidth();
        for (int i = 0; i < enemy.size(); i++) {
            Enemy currentObj = enemy.get(i);
            if (currentObj.getRect() != null) {
                int difference = currentObj.getRect().bottom - currentObj.getRect().top;
                if (marioRect.left <= (currentObj.getRect().left - Width / 30) && marioRect.right <= (currentObj.getRect().right + Width / 30) &&
                        marioRect.bottom < currentObj.getRect().top + difference / 2) {
                    if (intersectRect(marioRect, currentObj.getRect()) == true) {
                        System.out.println(true);

                    }
                }
            }
        }

    }

    public void checkKill (ArrayList<Enemy> enemy, Rect marioRect){
        boolean check;
        for (int i = 0; i < enemy.size(); i++) {
            Enemy currentObj = enemy.get(i);
            if (currentObj.getRect() != null) {
                if (marioRect.bottom < currentObj.getRect().top) {
                    //System.out.println("Entered loop");
                    int left = currentObj.getRect().left;
                    int right = currentObj.getRect().right;
                    int bottom = currentObj.getRect().top;
                    int top = bottom - ((currentObj.getRect().bottom - bottom) / 4);
                    Rect tempRect = new Rect(left, top, right, bottom);
                    //System.out.println("temp : "+tempRect);
                    //System.out.println("enemy :" +currentObj.getRect());
                    check = intersectRect(marioRect, tempRect);
                    //System.out.println(tempRect);
                    if (check == true) {
                        //System.out.println("The enemy has been killed!!!!!");
                        currentObj.killEnemy(currentObj);
                        currentObj.setscore();
                    }
                }
            }

        }
    }

    public boolean intersectRect(Rect r1, Rect r2) {
        if (r2 != null) {
            return !(r2.left > r1.right ||
                    r2.right < r1.left ||
                    r2.top > r1.bottom ||
                    r2.bottom < r1.top);
        } else {
            return false;
        }
    }

}
