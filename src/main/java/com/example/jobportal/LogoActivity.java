package com.example.jobportal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class LogoActivity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 4000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start SplashScreenActivity after the specified time
                Intent splashIntent = new Intent(LogoActivity.this, SplashScreenActivity.class);
                startActivity(splashIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
