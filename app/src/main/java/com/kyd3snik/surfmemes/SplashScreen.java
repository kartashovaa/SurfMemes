package com.kyd3snik.surfmemes;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent startIntent = new Intent(SplashScreen.this, LoginScreen.class);
                SplashScreen.this.startActivity(startIntent);
                SplashScreen.this.finish();
            }
        },300);
    }
}
