package com.example.mario.game_3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Gaurav Venkatesh on 5/12/2015.
 */
public class Menu {
    private final SuperMarioSuperView smsv;
    Bitmap MenuWall, diff, eassy, medd, hardd;


    public Menu(SuperMarioSuperView smsv) {
        this.smsv = smsv;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        MenuWall = BitmapFactory.decodeResource(smsv.getResources(), R.drawable.menuwall, options);
        diff = BitmapFactory.decodeResource(smsv.getResources(), R.drawable.level, options);
        eassy = BitmapFactory.decodeResource(smsv.getResources(), R.drawable.noob, options);
        medd = BitmapFactory.decodeResource(smsv.getResources(), R.drawable.amateur, options);
        hardd = BitmapFactory.decodeResource(smsv.getResources(), R.drawable.insane, options);
    }

    public void DrawWall(Canvas c) {
        Paint paint = new Paint();
        int Height = smsv.getHeight();
        int Width = smsv.getWidth();
        Rect dst = new Rect(0, 0, Width, Height);
        c.drawBitmap(MenuWall, null, dst, paint);
    }

    public void Diff(Canvas c) {
        Paint paint = new Paint();
        int Height = smsv.getHeight();
        int Width = smsv.getWidth();

        Rect dif = new Rect(Width / 2, 0, Width, Height / 4);
        Rect easy = new Rect(Width / 2 + Width / 10, Height / 4, Width - Width / 10, Height / 2);
        Rect med = new Rect(Width / 2 + Width / 10, Height / 2, Width - Width / 10, Height / 2 + Height / 4);
        Rect hard = new Rect(Width / 2 + Width / 10, Height / 2 + Height / 4, Width - Width / 10, Height);

        c.drawBitmap(diff, null, dif, paint);
        c.drawBitmap(eassy, null, easy, paint);
        c.drawBitmap(medd, null, med, paint);
        c.drawBitmap(hardd, null, hard, paint);
    }

    public int checkDifficulty(int X, int Y){
        int Height = smsv.getHeight();
        int Width = smsv.getWidth();
        // easy difficulty selected
        if (X > Width / 2 + Width / 10 && X < Width - Width / 10 && Y > Height / 4 && Y < Height / 2){
            return 10;
        }// medium difficulty selected
        else if (X > Width / 2 + Width / 10 && X < Width - Width / 10 && Y > Height / 2 && Y < Height / 2 + Height / 4){
            return 3;
        }// insane difficulty selected
        else if (X > Width / 2 + Width / 10 && X < Width - Width / 10 && Y > Height / 2 + Height / 4 && Y < Height){
            return 1;
        }
        else {
            return 0;
        }
    }


}
