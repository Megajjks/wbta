package com.bulsy.wbtempest;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.InputStream;

/**
 * Created by ugliest on 3/10/15.
 */
public class EntryScreen extends Screen {
    MainActivity act;
    Paint p = new Paint();
    //Bitmap screenbtm, playbtm, exitbtm;
    Rect scaledDst = new Rect(); // generic rect for scaling
    Rect playBtnBounds = null;
    Rect exitBtnBounds = null;

    public EntryScreen(MainActivity act) {
        this.act = act;

        /*
        try {
            // load screen bg
            AssetManager assetManager = act.getAssets();
            InputStream inputStream = assetManager.open("entryscreen.png");
            screenbtm = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            inputStream = assetManager.open("playbtn.png");
            playbtm = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            inputStream = assetManager.open("exitbtn.png");
            exitbtm = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        }
        catch (Exception e) {
            // what to do with an exception here on android?
            Log.d(MainActivity.LOG_ID, "onTouch", e);
        }
        */
    }

    @Override
    public void update(View v) {
      // nothing to update
    }

    @Override
    public void draw(Canvas c, View v) {
        int width = v.getWidth();
        int height = v.getHeight();
        String playmsg = "PLAY";
        String exitmsg = "EXIT";

        // draw the screen
        c.drawRGB(0, 0, 0);
//        scaledDst.set(0, 0, width, height);
//        c.drawBitmap(screenbtm, null, scaledDst, p);

        p.setTypeface(act.getGameFont());
        p.setColor(Color.GREEN);  // dark greenish
        p.setTextSize(30);
        String txt = "WANNABE";
        c.drawText(txt, v.getWidth()/2-p.measureText(txt), height/4, p);
        p.setTextSize(100);
        txt = "TEMPEST";
        c.drawText(txt, (v.getWidth()-p.measureText(txt))/2, height/3, p);

        p.setTextSize(60);
        if (playBtnBounds == null) {
            // initialize button locations
            p.getTextBounds(playmsg, 0, playmsg.length() - 1, scaledDst);
            // weirdly, and annoyingly, the bounds we create magically move and shrink.
            // so...uh....compensate by making the expected touch area bigger.
            playBtnBounds = new Rect(width/4 - scaledDst.width(),
                    height *3/4 - scaledDst.height(),
                    width/4 + scaledDst.width(),
                    height *3/4 + scaledDst.height());
            p.getTextBounds(exitmsg, 0, exitmsg.length() - 1, scaledDst);
            exitBtnBounds = new Rect(width*3/4 - scaledDst.width(),
                    height *3/4 - scaledDst.height(),
                    width*3/4 + scaledDst.width(),
                    height *3/4 + scaledDst.height());
        }
        c.drawText(playmsg, playBtnBounds.left, playBtnBounds.bottom, p);
        c.drawText(exitmsg, exitBtnBounds.left, exitBtnBounds.bottom, p);

        // version/copyright line
        p.setTextSize(30);
        String msg = "v"+BuildConfig.VERSION_NAME;
        int xTextEnd = (int)(width*.99f);
        c.drawText(msg, xTextEnd-p.measureText(msg), height - 80, p);
        int w1 = scaledDst.width();
        msg = "BULSY GAMES 2015";
        c.drawText(msg, xTextEnd-p.measureText(msg), height - 40, p);
    }

    @Override
    public boolean onTouch(MotionEvent e) {
        if (playBtnBounds.contains((int)e.getX(), (int)e.getY()))
            act.startGame();
        if (exitBtnBounds.contains((int)e.getX(), (int)e.getY()))
            act.exit();

        // we don't care about followup events in this screen
        return false;
    }

}