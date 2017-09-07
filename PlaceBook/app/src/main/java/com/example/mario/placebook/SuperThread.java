package com.example.mario.placebook;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Gaurav Venkatesh on 5/11/2015.
 */
public class SuperThread extends Thread {

    private  final  SuperView bsv;
    private  static  final  int  FRAME_PERIOD = 5;
    // In ms
    public  SuperThread(SuperView  bsv) {
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