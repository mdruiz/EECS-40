package com.example.gauravvenkatesh.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

import static java.lang.Math.sqrt;
import static java.lang.Math.tan;

public class RigidBall {

    private static int[] colors =
            {
                    Color.CYAN,
                    Color.RED,
                    Color.BLUE,
                    Color.YELLOW,
                    Color.GREEN,
                    Color.DKGRAY,
                    Color.MAGENTA
            };
    int rand = new Random().nextInt(colors.length);

    // Class properties
    private static final int RADIUS       = 75;
    private static final int MIN_VELOCITY = 3;
    private static final int MAX_VELOCITY = 3;
    private static final int COLOR        = Color.YELLOW;

    // Object properties
    private final BubbleShooterView  bwv;
    private final int              mR;
    private final int              mColor;
    private final int              mColor1;
    private       int              mVelX;
    private       int              mVelY;
    public        int              mX;
    public        int              mY;
    public        RigidBall[][] poparr =  new RigidBall[14][10];

    public RigidBall( BubbleShooterView bwv ) {
        Random rng  = new Random();
        int CLR = colors[rand];
        this.bwv    = bwv;
        //this.mColor = colors[rand];
        this.mColor = CLR;
        this.mColor1 = colors[rand];
        this.mR     = bwv.getWidth()/20;
        this.mX     = 0;
        this.mY     = 0;
        this.mVelX  = 0; //rng.nextInt(MAX_VELOCITY - MIN_VELOCITY + 1) + MIN_VELOCITY;
        this.mVelY  = 0; //rng.nextInt(MAX_VELOCITY - MIN_VELOCITY + 1) + MIN_VELOCITY;
    }

    public int getColor() {return mColor;}
    public void draw( Canvas d, int Xco, int Yco ) {
        Paint paint = new Paint();
        paint.setColor( mColor );

        d.drawCircle(this.getR() + Xco,  this.getR() + Yco, this.getR(), paint );
    }

    public void drawshootball( Canvas d )
    {
        Paint paint1 = new Paint();
        paint1.setColor( mColor);
        d.drawCircle( bwv.getWidth()/2,  bwv.getHeight()- this.getR(), this.getR(), paint1 );
    }
    public int shoot(Canvas d, int curX, int curY, RigidBall[][] bubbles) {
        Paint paint1 = new Paint();
        paint1.setColor( mColor);
        if ( mX == 0 && mY == 0)
        {
            mX = bwv.getWidth()/2;
            mY = bwv.getHeight() - this.getR();
        }
        d.drawCircle( this.getX(), this.getY(), this.getR(), paint1 );
        if ( mVelX == 0 && mVelY == 0) {
            double dx = (curX- bwv.getWidth()/2);
            double dy = (curY-bwv.getHeight() - this.getR());
            mVelX = (int) ((dx / sqrt((dx * dx) + (dy * dy))) * 20 );
            mVelY = (int) ((dy / sqrt((dx * dx) + (dy * dy))) *20 );
        }
        this.stepCoordinates();
        return this.checkCrash(bubbles);
        //popbub(GridX(mX, mY),GridY(mY),bubbles);

    }


    public void Lines(Canvas d, float xpo, float ypo)
    {
        int posX = bwv.getWidth();
        int posY = bwv.getHeight();
        Paint paint = new Paint();
        paint.setColor( Color.GRAY);
        d.drawLine(posX/2,posY,xpo,ypo,paint);
        //d.drawLine(0,ypo,xpo,ypo,paint);
        //d.drawLine(xpo,ypo,posX,ypo,paint);
        //d.drawLine(xpo,0,xpo,ypo,paint);
        //d.drawLine(xpo,ypo,xpo,posY,paint);
    }

    public int GridX (int x, int y) { // returns number of ball (column)
        int gridx;
        if (x > (bwv.getWidth()/2)) {
            gridx = (int) Math.floor((x) / (bwv.getWidth() / 10));
        }
        else{
            gridx = (int) Math.floor((x - this.getR()) / (bwv.getWidth() / 10));
        }
        if (GridY(y) % 2 == 1 && gridx == 9) {
            gridx--;
        }
        return gridx;
    }
    public int GridY ( int y) { // returns row number
        int gridy;
        gridy = (int) Math.floor(y / (bwv.getHeight() / 14));
        if (gridy == 14){
            gridy--;
        }
        return gridy;
    }
    public int checkCrash(RigidBall[][] bubbles) {
        int i = GridY(mY);
        int j = GridX(mX, mY);
        if (mY == 0 + this.getR()){
            mVelY = -mVelY; // since it crashed
            mVelX = -mVelX; // i move the ball one space back
            this.stepCoordinates(); // to the empty spot

            bubbles[GridY(mY)][GridX(mX,mY)] = this; // adds shot ball to the array

            popbub(GridY(mY),GridX(mX,mY),bubbles ); // checks if surrounding balls are of same color
            popfunc(poparr,bubbles); // pops bubbles
            return 2;
        }
        else if (bubbles[i][j] != null){ // if it crashes
            mVelY = -mVelY; // since it crashed
            mVelX = -mVelX; // i move the ball one space back
            this.stepCoordinates(); // to the empty spot

            bubbles[GridY(mY)][GridX(mX,mY)] = this; // adds shot ball to the array

            popbub(GridY(mY),GridX(mX,mY),bubbles ); // checks if surrounding balls are of same color
            popfunc(poparr,bubbles); // pops bubbles
/*
            if (mX > (bwv.getWidth()/2)) {
                bubbles[GridY(mY)][GridX(mX,mY)] = this;
            }
            else{
                bubbles[GridY(mY)][GridX(mX,mY)-1] = this;
            }
*/
            return 2; // changes gamestate
        }
        else { // hasn't crashed yet
            return 1;
        }
    }
    public int getX() { return mX; }
    public int getY() { return mY; }
    public int getR() { return (bwv.getWidth())/20; }

    public void popbub( int arrx, int arry, RigidBall[][] bubbles)
    {
        //int arx,ary; // ( get the adress of the bubble launched in the array)
        int Clr = bubbles[arrx][arry].mColor;
        int x,x1,y,y1,x0,y0,x2,y2;
        //ARRX = Row
        //ARRY = Column

        x0  =  arrx; //(original x position of the shot ball)
        y0  =  arry; //(original y position of the shot ball)
        y   =  y0-1;
        y1  =  y0 +1;
        x   =   x0 - 1; // ( the positions to the left and right )
        x1  =   x0 + 1;

        if (arrx %2 == 0) //( based of even or odd change the y and y1 values representing the balls above)
        {
          y2 = y0-1;
        }
        else
        {
          y2 = y0+1;
        }

        System.out.println("x0="+x0+"\nx="+x+"\nx1="+x1+"\n y= " +y+ "y0= "+y0);
        //Checks ball one below it of the same index
        if( x0>0 &&  (bubbles[x1][y0]!= null) && ( (this.getColor())==(bubbles[x1][y0].getColor()) ) )
        {
            poparr[x0][y0] = bubbles[x0][y0];
            if (poparr[x1][y0] == null) {
                poparr[x1][y0] = bubbles[x1][y0]; //(Add on to new array for pop later.)
                popbub(x, y0, bubbles);
            }
        }
        //Checks the ball below with one index less or more based on even or odd
        if( x0>0 && y0>0 && (bubbles[x1][y2] != null) && ( this.getColor() == bubbles[x1][y2].getColor()) )
        {
            poparr[x0][y0] = bubbles[x0][y0];
            if (poparr[x1][y2] == null) {
                poparr[x1][y2] = bubbles[x1][y2];
                //bubbles[x0][y0] = null;
                popbub(x, y2, bubbles);
            }
        }




        //Checks ball one above it of the same index
        if( x0>0 &&  (bubbles[x][y0]!= null) && ( (this.getColor())==(bubbles[x][y0].getColor()) ) )
    {

        poparr[x0][y0] = bubbles[x0][y0];
        if (poparr[x][y0] == null) {
            poparr[x][y0] = bubbles[x][y0]; //(Add on to new array for pop later.)
            popbub(x, y0, bubbles);
        }
        System.out.println("1");
    }
        //Checks the ball above with one index less or more based on even or odd
        if( ((y0>0 && x0 % 2 == 0) || (x0 %2 ==1) )&& x0>0 && (bubbles[x][y2] != null) && ( this.getColor() == bubbles[x][y2].getColor()) )
        {
            poparr[x0][y0] = bubbles[x0][y0];
            if (poparr[x][y2] == null) {
                poparr[x][y2] = bubbles[x][y2];
                popbub(x, y2, bubbles);
            }
        }
        // Checks the ball to the right
        if( y0<9 && (bubbles[x0][y1] != null) && ( this.getColor() == bubbles[x0][y1].getColor()) ) {
            //bubbles[x0][y1].setalpha(0);
            poparr[x0][y0] = bubbles[x0][y0];
            if (poparr[x0][y1] == null) {
                poparr[x0][y1] = bubbles[x0][y1];
                popbub(x0, y1, bubbles);
                System.out.println("4");
            }
        }

           // Checks the ball to the left
        if(  y0>0 && (bubbles[x0][y] != null) && ( this.getColor() == bubbles[x0][y].getColor()) )
        {
            //bubbles[x0][y1].setalpha(0);
            poparr[x0][y0] = bubbles[x0][y0];
            if (poparr[x0][y] == null) {
                poparr[x0][y] = bubbles[x0][y];
                popbub(x0, y, bubbles);
                System.out.println("4");
            }
        }
    }

    public void popfunc(RigidBall[][] poparr,RigidBall[][] bubbles)
    {
        int k;
        for (int j=0;j<14;j++ )
        {
            for(int i=0;i<10;i++)
            {
                if (poparr[j][i] != null)
                {
                    bubbles[j][i]=null;
                    poparr[j][i]=null;
                }
            }
        }

        for (int j=0;j<14;j++ )
        {
            for(int i=0;i<10;i++)
            {
                if(j%2==0)
                {
                    k = i-1;

                }
                else
                {
                    k=i+1;
                }

                if ( (bubbles[j][i] != null) && (j>=1) && (j<=13) && bubbles[j-1][i]==null && bubbles[j-1][k]==null )
                {

                    bubbles[j][i]=null;
                }
            }
        }

    }

    // The laws of physics: Restricted frictionless motion :)
    public void stepCoordinates() {
        int maxX = bwv.getWidth() ;
        int maxY = bwv.getHeight() ;

        mX += mVelX;
        mY += mVelY;

        if ( mX > ( maxX - mR) )
        {
            mVelX = -mVelX;
            mX = maxX - mR;
        }
        else if ( mX < mR)
        {
            mVelX = -mVelX;
            mX = mR;
        }
        if ( mY > ( maxY - mR) )
        {
            mVelY = -mVelY;
            mY = maxY - mR;
        }
        else if ( mY < mR)
        {
            mVelY = -mVelY;
            mY = mR;
        }
    }
}
