package com.kyd3snik.surfmemes.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.kyd3snik.surfmemes.R;
import com.kyd3snik.surfmemes.ui.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent startIntent = new Intent(SplashActivity.this, LoginActivity.class);
                SplashActivity.this.startActivity(startIntent);
                SplashActivity.this.finish();
            }
        },300);
    }
}
