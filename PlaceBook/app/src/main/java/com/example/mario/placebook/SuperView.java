package com.example.mario.placebook;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class SuperView extends SurfaceView implements SurfaceHolder.Callback {
    public SuperView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        SuperThread bsv = new SuperThread(this);
        bsv.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public  void  onDraw(Canvas c)
    {
        super.onDraw(c);

    }

    public boolean onTouchEvent(MotionEvent e) {


        switch (e.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                // Update Game State.

                break;
            case MotionEvent.ACTION_MOVE:
                // Update Game State.
                break;
            case MotionEvent.ACTION_UP:
                // Update Game State.

        }
        return true;
    }

    public void tick(Canvas c) {


    }
}
