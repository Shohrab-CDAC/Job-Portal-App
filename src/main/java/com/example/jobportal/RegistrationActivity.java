package com.example.jobportal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText emailReg;
    private EditText passReg;

    private Button btnReg;
    private Button btnLogin;

    // Firebase Auth
    private FirebaseAuth mAuth;

    // Progress dialog
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgistration); // Corrected layout file name

        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);

        initializeViews();
    }

    private void initializeViews() {
        emailReg = findViewById(R.id.email_registration);
        passReg = findViewById(R.id.registration_password);

        btnReg = findViewById(R.id.btn_registration);
        btnLogin = findViewById(R.id.btn_login);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    private void registerUser() {
        String email = emailReg.getText().toString().trim();
        String pass = passReg.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailReg.setError("Required Field..");
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            passReg.setError("Required Field..");
            return;
        }

        mDialog.setMessage("Processing..");
        mDialog.show();

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mDialog.dismiss(); // Dismiss the dialog regardless of the result

                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistrationActivity.this, StudentActivity.class));
                    finish(); // Finish the current activity to prevent going back to it after registration
                } else {
                    Toast.makeText(getApplicationContext(), "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
