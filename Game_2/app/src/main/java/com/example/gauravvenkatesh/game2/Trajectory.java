package com.example.gauravvenkatesh.game2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Gaurav Venkatesh on 4/21/2015.
 */
public  class  Trajectory  implements  TimeConscious {
    private final DashTillPuffSurfaceView bsv;
    private ArrayList<Point> p = new ArrayList<>();
    public Trajectory(DashTillPuffSurfaceView bsv)
    {
        this.bsv=bsv;
       /*
        int valx = 1;//new Random().nextInt(bsv.getWidth());
        this.x = valx;
        int valy = new Random().nextInt(bsv.getHeight());
        this.y = 3;//valy;
        */
    }
    @Override
    public void tick(Canvas canvas) {

// As time  ticks , append  more  points  to the  trajectory  and
//  discard  those  points  that  have  crossed  the  entire
// span of the  screen.
        int x= bsv.getWidth();
        int y = new Random().nextInt(bsv.getHeight());
        int dx = (bsv.getWidth())/4;
        int dx2 = (bsv.getWidth())/150;

        if (p.size() == 0)
        {
            p.add(new Point(x,y));
        }
        else
        {
            if(p.get(p.size()-1).x <= bsv.getWidth() )
            {
                p.add(new Point(x+dx,y));
            }
        }
        for (int i = 0; i < p.size();i++)
        {
            int newX = p.get(i).x - dx2;
            int newY = p.get(i).y;
            p.set(i, new Point(newX,newY));
        }
        while (p.get(0).x < 0 )
        {
            p.remove(0);
        }


        draw(canvas);
    }


    private void draw(Canvas c) {
        Path path = new  Path();
        path.moveTo(p.get(0).x, p.get(0).y);
/* Move to  first  point */

        for( int i = 1; i < p.size(); ++i )
        {
            path.lineTo( p.get(i).x,p.get(i).y);
        }

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        //paint.setAlpha(200);

        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setPathEffect(new DashPathEffect(new float[]{15, 15}, 0));


// Set  paint  color , alpha , line  width , dashed  style , etc.

        c.drawPath( path , paint );

    }

    public int getPointY1()
    {
        return(p.get(p.size()-2).y);
    }
    public int getPointY2()
    {
        return(p.get(p.size()-1).y);
    }
    public int getPointX()
    {
        return(p.get(p.size()-1).x);
    }

}

