package com.example.gauravvenkatesh.game2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by Mario on 4/27/2015.
 */
public class Lines
{
    private final DashTillPuffSurfaceView bsv;
    private float x;
    private float y;


    public Lines(DashTillPuffSurfaceView bsv)
    {
        this.bsv= bsv;
        this.x=x;
        this.y=y;
    }
public void draw(Canvas d)
    {
        //int randy = new Random().nextInt(bsv.getHeight());
        int divx = bsv.getWidth()/4;
        int pointx1 = divx;
        LineDraw(d, pointx1);
    }

    public void LineDraw(Canvas d,int p)
    {



        Paint paint = new Paint();
        paint.setColor(Color.BLUE);

        int randy = new Random().nextInt(bsv.getHeight());
        int randy2 = new Random().nextInt(bsv.getHeight());
        d.drawLine(0,randy,p,randy2,paint);

        randy = randy2;
        randy2 = new Random().nextInt(bsv.getHeight());
        d.drawLine(p,randy,2*p,randy2,paint);

        randy = randy2;
        randy2 = new Random().nextInt(bsv.getHeight());
        d.drawLine(2*p,randy,3*p,randy2,paint);

        randy = randy2;
        randy2 = new Random().nextInt(bsv.getHeight());
        d.drawLine(3*p,randy,4*p,randy2,paint);


    }

}
