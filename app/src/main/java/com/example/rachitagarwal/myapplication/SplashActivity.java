package com.example.rachitagarwal.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.i("MY WINDOW APP", "MY SPLASH START");
        mp= MediaPlayer.create(this, R.raw.campus_window);
        mp.start();
        Thread t=new Thread()
        {
            public void run()
            {
                try{
                    sleep(2500);
                    Intent i=new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                }catch(Exception e){}
            }
        };
        t.start();
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mp.release();
       finish();
    }



}
