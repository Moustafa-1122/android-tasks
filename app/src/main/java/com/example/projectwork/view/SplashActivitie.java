package com.example.projectwork.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectwork.R;

public class SplashActivitie extends AppCompatActivity {

    private static final int SPLASH_DURATION = 3000; // 3 Second

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activitie);

        // Handler For wating 3 second
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
            // إIf login go to MainActivity
            intent = new Intent(SplashActivitie.this, Main1Activity.class);
        } else {
            //If not Login go to LoginActivity
            intent = new Intent(SplashActivitie.this, LoginActivity.class);
        }

        startActivity(intent);
        finish(); 
    }


}
