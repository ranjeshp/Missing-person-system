package com.ranjesh.facerecognition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ranjesh.facerecognition.databinding.LoginBinding;

public class Login extends AppCompatActivity {
    FirebaseAuth auth;
    SharedPreferences shrd;
    LoginBinding binding;
    SharedPreferences.Editor editor;
    DatabaseReference databaseReference;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        binding = LoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        shrd = getSharedPreferences("user", MODE_PRIVATE);
        editor = shrd.edit();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        dialog = new Dialog(this);


        binding.textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.email.getText().toString().isEmpty() ||
                        binding.password.getText().toString().isEmpty()) {
                    Toast.makeText(Login.this, "Please fill the required fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (binding.radioUser.isChecked()) {
                        // User login
                        Login1();
                    } else if (binding.radioAdmin.isChecked()) {
                        // Admin login
                        loginAdmin();
                    }
                }
            }
        });
        binding.forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open a dialog or navigate to a new activity for password reset
                showPasswordResetDialog();
            }
        });
    }

    private void loginAdmin() {
        String email = binding.email.getText().toString().trim();
        String password = binding.password.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(Login.this, "Please fill in the required fields", Toast.LENGTH_SHORT).show();
        } else {
            // Check if the entered email and password match the admin credentials
            if (email.equals("admin@gmail.com") && password.equals("12345678")) {

                // Use FirebaseAuth to sign in the user
                Toast.makeText(this, "Admin login successful.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this, AdminHome.class));
            } else {
                // Admin login failed
                Toast.makeText(this, "Invalid admin credentials.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showPasswordResetDialog() {
        Toast.makeText(Login.this, "Implement Password Reset Functionality", Toast.LENGTH_SHORT).show();

        EditText emailEditText = new EditText(this);

        new AlertDialog.Builder(this)
                .setTitle("Reset Password")
                .setMessage("Enter your email to receive a password reset link.")
                .setView(emailEditText)
                .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String email = emailEditText.getText().toString().trim();
                        if (!email.isEmpty()) {
                            resetPassword(email);
                        } else {
                            Toast.makeText(Login.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    // Method to reset password using FirebaseAuth
    private void resetPassword(String email) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Login.this, "Failed to send reset email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void Login1() {
        String email = binding.email.getText().toString().trim();
        String password = binding.password.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(Login.this, "Please fill in the required fields", Toast.LENGTH_SHORT).show();
        } else {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            editor.putBoolean("isloginin", true);
                            editor.commit();
                            Toast.makeText(getApplicationContext(), "Success! Welcome back", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Home.class));
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}