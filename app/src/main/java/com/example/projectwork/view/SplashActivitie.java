package com.example.projectwork.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectwork.R;

public class SplashActivitie extends AppCompatActivity {

    private static final int SPLASH_DURATION = 3000; // 3 ثواني

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activitie);

        // Handler للانتظار 3 ثواني
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLoginStatus();
            }
        }, SPLASH_DURATION);
    }

    private void checkLoginStatus() {
        SharedPreferences prefs = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);

        Intent intent;
        if (isLoggedIn) {
            // إذا مسجل دخول → روح على MainActivity
            intent = new Intent(SplashActivitie.this, Main1Activity.class);
        } else {
            // إذا مش مسجل دخول → روح على LoginActivity
            intent = new Intent(SplashActivitie.this, LoginActivity.class);
        }

        startActivity(intent);
        finish(); // إغلاق SplashActivity حتى ما يرجع عليها
    }


}