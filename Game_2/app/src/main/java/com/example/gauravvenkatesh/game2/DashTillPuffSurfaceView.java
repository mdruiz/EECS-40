package com.example.gauravvenkatesh.game2;

/**
 * Created by Gaurav Venkatesh on 4/21/2015.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public  class  DashTillPuffSurfaceView  extends SurfaceView implements  SurfaceHolder.Callback , TimeConscious
{
    private CosmicFactory cosmic;
    private Lines line;
    private Trajectory trajectory;
    static int gamestate = 0 ;
    static int flag = 0;
    public  DashTillPuffSurfaceView(Context context)
    {
        super(context);
        getHolder().addCallback(this);
        cosmic = new CosmicFactory(this);
        trajectory = new Trajectory(this);
    }
    @Override
    public  void  surfaceCreated(SurfaceHolder  holder)
    {
        DashTillPuffRenderThread bsv = new  DashTillPuffRenderThread(this);
        bsv.start();

//  Create  the  sliding  background , cosmic  factory , trajectory
// and  the  space  ship
    }
    public void surfaceChanged(SurfaceHolder holder,
                               int format, int width, int height)
    {
// Respond to surface changes , e.g., aspect ratio changes.
    }


    public void surfaceDestroyed(SurfaceHolder holder)
    {

    }


    @Override
    public  void  onDraw(Canvas c)
    {
        super.onDraw(c);
// Draw  everything (restricted  to the  displayed  rectangle).
    }
    @Override
    public boolean onTouchEvent(MotionEvent e) {
// Update game state in response to events:
// touch -down , touch -up, and touch -move.
// Current finger position.
        int curX = (int) e.getX();
        int curY = (int) e.getY();

        switch (e.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            // Update Game State.
                gamestate = 1;
                break;
            case MotionEvent.ACTION_MOVE:
               // Update Game State.
                break;
            case MotionEvent.ACTION_UP:
            // Update Game State.
                gamestate = 0;
        }
        return true;
    }

    @Override
    public  void  tick(Canvas c) {
// Tick  background , space ship , cosmic  factory , and  trajectory.
// Draw  everything (restricted  to the  displayed  rectangle).
        cosmic.tick(c);
        trajectory.tick(c);
        cosmic.RocketMove(gamestate);
        flag++;

        if (flag > 1 )
        {
            int Y1 =trajectory.getPointY1();
            int Y2 = trajectory.getPointY2();
            int checkX = trajectory.getPointX();
            cosmic.SendValues(Y1,Y2,checkX);
            flag = 1;
        }

       /* Paint paint1 = new Paint();
        paint1.setStyle(Paint.Style.FILL);
        paint1.setColor(Color.WHITE);
        c.drawPaint(paint1);
        */

    }
}
