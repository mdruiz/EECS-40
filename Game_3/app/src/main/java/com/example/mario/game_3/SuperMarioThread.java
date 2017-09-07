package com.example.mario.game_3;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Gaurav Venkatesh on 5/11/2015.
 */
public class SuperMarioThread extends Thread {

    private  final  SuperMarioSuperView  bsv;
    private  static  final  int  FRAME_PERIOD = 5;
    // In ms
    public  SuperMarioThread(SuperMarioSuperView  bsv) {
        this.bsv = bsv;
    }
    public  void  run() {
        SurfaceHolder sh = bsv.getHolder();
// Main  game  loop.
        while( !Thread.interrupted() ) {
            Canvas c = sh.lockCanvas(null);
            try {
                synchronized(sh) {
                    bsv.tick(c);
                }
            } catch (Exception e) {
            } finally {
                if ( c != null ) {
                    sh.unlockCanvasAndPost(c);
                }
            }
            try {
                Thread.sleep( FRAME_PERIOD );
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
