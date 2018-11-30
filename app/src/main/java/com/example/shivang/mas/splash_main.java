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
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                    Intent i = new Intent(splash_main.this, serv_sync.class);
                    startActivity(i);

                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
