package com.example.shivang.mas;

/**
 * Created by Shivang on 08-11-2018.
 */


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class splash_main extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_main);
        final boolean trip = checklogin();
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if (trip) {
                    Intent i = new Intent(splash_main.this, MainActivity.class);
                    startActivity(i);
                }
                else
                {
                    Intent it = new Intent(splash_main.this,alreadylogin.class);
                    startActivity(it);
                }

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
    Boolean checklogin()
    {
        universaldat dt = new universaldat(this);
        try{
            dt.frcrt();
        }
        catch (Exception e)
        {
            Log.d("Mainactivity==login",e.toString());
        }
        Cursor cr = dt.getAcc();
        String inp="null";
        if (cr.moveToFirst())
        {
            do {
                inp = cr.getString(0);
            }while (cr.moveToNext());
        }
        if (inp.equals("null"))
        {
            return  true;
        }
        else
        {
            return false;
        }
    }

}
