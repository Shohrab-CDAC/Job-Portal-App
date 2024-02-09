package com.example.jobportal;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RecruiterRegistrationActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText companyNameEditText;
    private EditText companyWebsiteEditText;
    private EditText gstCertificateEditText;
    private EditText companySizeEditText;
    private EditText companyLocationEditText;
    private EditText companyAddressEditText;
    private Button attachPdfButton;
    private TextView attachedFileNameTextView;
    private RadioButton pdfRadioButton;
    private Button signUpButton;
    private Button loginButton;

    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;

    // Define a constant for the PDF request code
    private static final int YOUR_PDF_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_registration);

        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);

        initializeViews();
    }

    private void initializeViews() {
        nameEditText = findViewById(R.id.editTextName);
        emailEditText = findViewById(R.id.editTextOfficialEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        companyNameEditText = findViewById(R.id.editTextCompanyName);
        companyWebsiteEditText = findViewById(R.id.editTextCompanyWebsite);
        gstCertificateEditText = findViewById(R.id.editTextGSTCertificate);
        companySizeEditText = findViewById(R.id.editTextCompanySize);
        companyLocationEditText = findViewById(R.id.editTextCompanyLocation);
        companyAddressEditText = findViewById(R.id.editTextCompanyAddress);
        attachPdfButton = findViewById(R.id.btnAttachPDF);
        attachedFileNameTextView = findViewById(R.id.textViewAttachedFileName);
        pdfRadioButton = findViewById(R.id.radioButtonPDF);
        signUpButton = findViewById(R.id.btnSignUpRecruiter);
        loginButton = findViewById(R.id.btnLoginRecruiter);

        pdfRadioButton.setClickable(false);
        pdfRadioButton.setFocusable(false);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerRecruiter();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecruiterRegistrationActivity.this, RecruiterLoginActivity.class));
            }
        });

        attachPdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add code to handle PDF attachment
                // You can open a file picker or implement your logic here
                // For example, you can use Intent to open a file picker
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent, YOUR_PDF_REQUEST_CODE);
            }
        });
    }

    private void registerRecruiter() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        boolean isPdfAttached = pdfRadioButton.isChecked();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || !isPdfAttached) {
            Toast.makeText(getApplicationContext(), "Email, Password, and PDF attachment are required", Toast.LENGTH_SHORT).show();
            return;
        }

        mDialog.setMessage("Processing..");
        mDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mDialog.dismiss();

                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RecruiterRegistrationActivity.this, HomeActivity.class));
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == YOUR_PDF_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // Get the file name from the URI
            String fileName = getFileName(data.getData());

            // Set the file name to the TextView
            attachedFileNameTextView.setText("Attached File: " + fileName);

            // Automatically check the radio button when a file is attached
            pdfRadioButton.setChecked(true);
        }
    }

    // Helper method to get the file name from the URI
    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}
