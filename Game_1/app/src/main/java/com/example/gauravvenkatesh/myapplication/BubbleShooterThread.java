package com.example.gauravvenkatesh.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.content.Context;
/**
 * Created by Gaurav Venkatesh on 4/8/2015.
 */

public class BubbleShooterThread extends Thread {
    BubbleShooterView bsv;

    public BubbleShooterThread(BubbleShooterView bsv)
    {
        this.bsv= bsv;
    }

    @Override
    public void run()  {
        SurfaceHolder sh = bsv.getHolder();
// Main game loop.
        while( !Thread.interrupted() ) {
            Canvas c = sh.lockCanvas(null);
            try {
                synchronized(sh) {
                    bsv.advanceFrame(c);
                }
            } catch (Exception e) {
                System.err.print( e.getStackTrace() );
            } finally {
                if ( c != null ) {
                    sh.unlockCanvasAndPost(c);
                }
            }
// Set the frame rate by setting this delay
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
// Thread was interrupted while sleeping.
                return;
            }
        }
    }
}