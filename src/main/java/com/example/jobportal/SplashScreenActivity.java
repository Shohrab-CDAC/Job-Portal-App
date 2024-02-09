package com.example.jobportal;
// SplashScreenActivity.java
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Button btnStudent = findViewById(R.id.btnStudent);
        Button btnRecruiter = findViewById(R.id.btnRecruiter);

        btnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the MainActivity when the Student button is clicked
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnRecruiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the RecruiterActivity when the Recruiter button is clicked
                Intent intent = new Intent(SplashScreenActivity.this, RecruiterLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
