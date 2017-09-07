package com.example.gauravvenkatesh.game2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Gaurav Venkatesh on 4/21/2015.
 */
public  class  CosmicFactory  implements  TimeConscious {
    private final DashTillPuffSurfaceView bsv;
    Bitmap Hello1,Hello2,Hello3,Hello4,blackhole, BluePlanet, Cloud, Earth, GlossyPlanet, GoldenStar, Wallpapero, Neutron, ShinyStar, SpaceShip, Starone, Startwo;
    int Xco, Yco;
    int Rvelx,Rvely,vel;
    public int Wallx;
    public int Wally;
    public int MoveSpeed = 20;
    int OBJV;
    int clusterY1=0 ,clusterY2 = 400 , checkX=0;
    static int clusterY, clusterX;
    int [] xPoints = new int[40];
    int [] yPoints = new int[40];
    static int current = 0;
    static double score = 0;
    static int gamestate;

    public CosmicFactory(DashTillPuffSurfaceView bsv) {
        this.bsv = bsv;
        BitmapFactory.Options options = new BitmapFactory.Options();
        blackhole = BitmapFactory.decodeResource(bsv.getResources(), R.drawable.blackhole, options);
        BluePlanet = BitmapFactory.decodeResource(bsv.getResources(), R.drawable.blueplanet, options);
        Cloud = BitmapFactory.decodeResource(bsv.getResources(), R.drawable.cloud, options);
        Earth = BitmapFactory.decodeResource(bsv.getResources(), R.drawable.earth, options);
        GlossyPlanet = BitmapFactory.decodeResource(bsv.getResources(), R.drawable.glossyplanet, options);
        GoldenStar = BitmapFactory.decodeResource(bsv.getResources(), R.drawable.goldenstar, options);
        Neutron = BitmapFactory.decodeResource(bsv.getResources(), R.drawable.neutronstar, options);
        ShinyStar = BitmapFactory.decodeResource(bsv.getResources(), R.drawable.shinystar, options);
        SpaceShip = BitmapFactory.decodeResource(bsv.getResources(), R.drawable.spaceship, options);
        Starone = BitmapFactory.decodeResource(bsv.getResources(), R.drawable.starone, options);
        Startwo = BitmapFactory.decodeResource(bsv.getResources(), R.drawable.startwo, options);
        Wallpapero = BitmapFactory.decodeResource(bsv.getResources(), R.drawable.wallpaper, options);
        this.Wallx = bsv.getHeight();
        this.Wally = bsv.getWidth();
        this.Xco = 0;
        this.Yco = 0;
        /*
        for (int i =0;i<30;i++){
            xPoints[i] = - 100;
            yPoints[i] = - 100;
        }
        */
    }
    private static int[] Pictures =
            {
                    R.drawable.blackhole,R.drawable.blueplanet,
                    R.drawable.cloud,R.drawable.earth,
                    R.drawable.glossyplanet,R.drawable.goldenstar,
                    R.drawable.neutronstar,R.drawable.shinystar,
                    R.drawable.starone,R.drawable.startwo
            };
    int picname1 = new Random().nextInt(Pictures.length);
    int picname2 = new Random().nextInt(Pictures.length);
    int picname3 = new Random().nextInt(Pictures.length);
    int picname4 = new Random().nextInt(Pictures.length);

    public void RocketMove(int state)
    {
        gamestate = state;
        Rvelx=0;
        vel = (bsv.getHeight())/80;
        // moves the rocket downward
        if (gamestate == 0)
        {
            //checking if rocket hits bottom of screen
            if (((bsv.getHeight()/2) + (bsv.getHeight()/4) + Rvely) <= bsv.getHeight())
            {
                Rvely=  Rvely + vel;
            }
        }
        //moves the rocket upward
        if (gamestate == 1)
        {
            // checking if rocket hits top of screen
            if (((bsv.getHeight()/2)  + Rvely) >= 0)
            {
                Rvely=  Rvely - vel;
            }
        }
        //resetting game to initial state because player lost
        if (gamestate == -1){
            //score =0;

        }
    }
    public void SendValues(int y1,int y2,int X)
    {
        clusterY1 = y1;
        clusterY2 = y2;
        checkX = X;
    }

    @Override
    public void tick(Canvas c) {

        Paint paint = new Paint();
        BitmapFactory.Options options = new BitmapFactory.Options();
        Hello1 = BitmapFactory.decodeResource(bsv.getResources(), Pictures[picname1], options);
        Hello2 = BitmapFactory.decodeResource(bsv.getResources(), Pictures[picname2], options);
        Hello3 = BitmapFactory.decodeResource(bsv.getResources(), Pictures[picname3], options);
        Hello4 = BitmapFactory.decodeResource(bsv.getResources(), Pictures[picname4], options);

        int Height = bsv.getHeight();
        int Width = bsv.getWidth();
        int Rocx = Width/4;
        int Rocy = Height/2;

        Rect RockRec = new Rect(Rocx, Rocy+Rvely , Rocx + Rocx/2 , Rocy + Rocy/2 + Rvely );
        Rect dst = new Rect(Xco, 0, Width + Xco, Height);
        Rect dst2 = new Rect(Width + Xco, 0, (2*Width) +Xco, Height);

        int Rx1,Rx2,Ry1,Ry2;
        Rx1=Rocx;
        Ry1=Rocy+Rvely;
        Rx2=Rocx + Rocx/2;
        Ry2=Rocy + Rocy/2 + Rvely;

        //calculations for drawing clusters around
        int dy = clusterY2 - clusterY1;
        int clusterX1 = bsv.getWidth();
        int clusterX2 = clusterX1 + bsv.getWidth()/4;
        int dx = clusterX2 - clusterX1;
        int slope = dy/dx;
        int gap = bsv.getWidth()/30;
        int rangeY = slope*(clusterX2 - clusterX1) + clusterY1;
        clusterX = bsv.getWidth();
        //System.out.println(rangeY);
        Rect[] array = new Rect[40];
        //checks if its time to make new cluster
        if (checkX == bsv.getWidth()+bsv.getWidth()/4 - bsv.getWidth()/150 ) {
            //System.out.println("Entered loop which means that clusterY1 has a new value of :" +clusterY1);
            for (int i = 0; i < 5; i++) {

                clusterX = bsv.getWidth() + (i * gap) ;
                xPoints[current] = clusterX;
                //creating clusters above trajectory
                if (current  >= 0 && current <=9){
                    if (xPoints[0]  >=bsv.getWidth() ) {
                        picname1 = new Random().nextInt(Pictures.length);
                    }
                    //System.out.println(slope);
                    clusterY = new Random().nextInt(rangeY);
                    //clusterY = 50;
                    yPoints[current] = clusterY - Rocy/2 ; //50 -50;
                }
                else if (current  >= 20 && current <=29){
                    if (xPoints[20] >=bsv.getWidth()) {
                        picname2 = new Random().nextInt(Pictures.length);
                    }
                    clusterY = new Random().nextInt(rangeY);
                    //clusterY = 50;
                    yPoints[current] = clusterY - Rocy/2;// 50-50;
                }
                //creating clusters below trajectory
                else if (current  >= 10 && current <=19){
                    if (xPoints[10] >=bsv.getWidth()) {
                        picname3 = new Random().nextInt(Pictures.length);
                    }
                    clusterY = new Random().nextInt(bsv.getHeight() - rangeY);
                    //clusterY = rangeY +50;
                    //clusterY = bsv.getHeight();
                    yPoints[current] = clusterY + Rocy/3 +rangeY;
                    //clusterY = clusterY + rangeY + 50;
                }
                else if (current  >= 30 && current <=39){
                    if (xPoints[30] >=bsv.getWidth()) {
                        picname4 = new Random().nextInt(Pictures.length);
                    }
                    //System.out.println(slope);
                    clusterY = new Random().nextInt(bsv.getHeight() - rangeY);
                    //clusterY = rangeY +50;
                    //clusterY = bsv.getHeight();
                    yPoints[current] = clusterY + Rocy/3 + rangeY;
                }
                current++;
                //System.out.println(current);
                if (current == 40 ){
                    current = 0;
                }
            }
        }
        //moves clusters across screen with speed of 5
        for (int k = 0; k < 40; k++) {
            xPoints[k] = xPoints[k] - (bsv.getWidth())/150;
        }
        //makes array of rects with calculated x and y points
        for (int k = 0; k < 40; k++) {
            array[k] = new Rect(xPoints[k], yPoints[k], Rocx / 3 + xPoints[k], yPoints[k] + Rocy / 3);
        }

        c.drawBitmap(Wallpapero, null, dst, paint);
        c.drawBitmap(Wallpapero, null, dst2, paint);
        c.drawBitmap(SpaceShip,null,RockRec,paint);
        //puts images on the rects
        if (clusterY1 != 0) {
            for (int j = 0; j < 10; j++) {
                c.drawBitmap(Hello1, null, array[j], paint);
            }
            for (int j = 10; j < 20; j++) {
                c.drawBitmap(Hello2, null, array[j], paint);
            }
            for (int j = 20; j < 30; j++) {
                c.drawBitmap(Hello3, null, array[j], paint);
            }
            for (int j = 30; j < 40; j++) {
                c.drawBitmap(Hello4, null, array[j], paint);
            }
        }
        //Rect objects = new Rect( 2* OBJV , 50,  Rocx/3 + 2*OBJV, 50+Rocy/3);
//  Where to draw.
        MoveScreen();
//        ObjectCl();
        int crashCheck = CheckCrash(Rx1,Rx2,Ry1,Ry2,xPoints,yPoints,Rocx,Rocy);

         if (crashCheck==0)
         {
            gamestate = -1;
            score = 0;
         }
        MakeScore(c,Rocx);
//  Create  new  ``clusters'' of  cosmic  objects. Alternately  place
//  clusters  of  cosmic  objects  above  and  below  the  guiding
//  trajectory.

//  Randomly  select  the  type of  cluster  objects  from a list
// (e.g., stars , clouds , planets , etc.). So all  objects  in
// a cluster  are of the  same  type.

//  Remove  cosmic  objects (stars , planets , etc.) that  moved  out
// of the  scene.

    }


    private int CheckCrash(int Rx1,int Rx2,int Ry1,int Ry2,int[] OBJx1,int[] OBJy1,int Rocx, int Rocy)
    {
        int[] OBJx2 = new int[40];
        int[] OBJy2 = new int[40];

        for(int i=0;i<40;i++)
        {
            int result1 =0;
            int result = 0;
            OBJx2[i]=OBJx1[i]+Rocx/3;
            OBJy2[i]=OBJy1[i]+Rocy/3;
            //System.out.println(" Rx1 "+Rx1+" Rx2 "+Rx2+ " Ry1 "+Ry1+" Ry2 "+Ry2);
            //System.out.println(" OBjx1 "+OBJx1[i]+" OBjx2 "+OBJx2[i]+ " OBJy1 "+OBJy1[i]+" OBJy2 "+OBJy2[i]);
            if (Rx1 > OBJx2[i] || OBJx1[i] > Rx2)
            {
                result = 1;
            }

            if (Ry1 > OBJy2[i] || OBJy1[i] > Ry2)
            {
              result1 = 1;
            }
            if (result == 0 && result1 == 0){
                return 0;
            }
        }
        //
        return 1;
    }

    private void ObjectCl()
    {
        int Width = bsv.getWidth();
        OBJV = OBJV-5 ;
        if (OBJV<= (-Width))
        {
            OBJV = bsv.getWidth();
            //picname = new Random().nextInt(Pictures.length);
        }

    }
    public void MoveScreen() {
        int Width = bsv.getWidth();
        int speedscreen = (bsv.getWidth())/150;
        Xco = Xco - speedscreen;

        if(Xco<(-Width))
        {
            Xco=0;
        }

    }


    private void MakeScore(Canvas c,int Rocx) {
        int x=  50;
        int y=  100;
        double k1=0;
        int k=0;
        score =  score + 0.1;
        k1 = (int)Math.floor(score);
        k= (int)k1;
        Paint paint = new Paint();
        paint.setTextSize(Rocx/5);
        c.drawText("Score : " + k, x, y, paint);

    }
}