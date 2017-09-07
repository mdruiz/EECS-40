package com.example.gauravvenkatesh.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;
/**
 * Created by Gaurav Venkatesh on 4/8/2015.
 */

/*
* Atempting to make my own stationary balls and display on screen.
*
* */
public class StationBalls
{
    private static final int RADIUS       = 50;
    private static final int COLOR        = Color.YELLOW;

    private final BubbleShooterView  bwv;
    private final int              mColor;
    private final int              mR;
    public        int              mX;
    public        int              mY;

    public StationBalls(BubbleShooterView bwv)
    {
        this.bwv = bwv;
        this.mColor= Color.YELLOW;
        this.mR     = RADIUS;
        this.mX     = 0;
        this.mY     = 0;
    }

    public void draw( Canvas d )
    {   int k=1;
        Paint paint = new Paint();
        paint.setColor( mColor );
        d.drawCircle( this.getX() + this.getR(), this.getY() + this.getR(), this.getR(), paint );
    }

    public static void drawblocks( Canvas d, int Xco,int Yco, int Rad, int randomInt )
    {          int[] colors =
            {
                    Color.CYAN,
                    Color.RED,
                    Color.BLUE,
                    Color.YELLOW,
                    Color.GREEN,
                    Color.DKGRAY,
                    Color.MAGENTA
            };
        Paint paint = new Paint();
        paint.setColor( colors[((int) Math.floor(Math.random() * colors.length))]);
        d.drawCircle( Xco, Yco, Rad, paint );
        //this.MakeBlocks();
    }

    public int getX() { return mX; }
    public int getY() { return mY; }
    public int getR() { return (bwv.getWidth())/20; }

    private void MakeBlocks()
    {
        int maxX = bwv.getWidth();
        int maxY = bwv.getHeight();

        int numx = maxX/(2*RADIUS);
        int numy = maxY/(4*RADIUS);


    }


    public int getWidth()
    {
        return bwv.getWidth();
    }


    public int getHeight()
    {
       return bwv.getHeight();
    }
}
