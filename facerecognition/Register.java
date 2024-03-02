package com.ranjesh.facerecognition;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ranjesh.facerecognition.databinding.RegisterBinding;
import java.util.HashMap;
public class Register extends AppCompatActivity {
    DatabaseReference databaseReference;
    SharedPreferences shrd;
    SharedPreferences.Editor editor;

    TextView rv;
    FirebaseAuth auth;
    RegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        rv = findViewById(R.id.already);
        auth = FirebaseAuth.getInstance();
        shrd = getSharedPreferences("user", MODE_PRIVATE);
        editor = shrd.edit();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.country_codes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.countryCode.setAdapter(adapter);


        databaseReference = FirebaseDatabase.getInstance().getReference();

        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.name.getText().toString().isEmpty() ||
                        binding.email.getText().toString().isEmpty() ||
                        binding.contact.getText().toString().isEmpty() ||
                        binding.password.getText().toString().isEmpty() ||
                        binding.confirmPassword.getText().toString().isEmpty()) {

                    Toast.makeText(Register.this, "please enter the required field", Toast.LENGTH_SHORT).show();
                } else {
                    RegisterUser();
                }
            }
        });

        rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }

    private void RegisterUser() {
        String contactPattern = "^[0-9]{10}$";
        String namePattern = "^[a-zA-Z ]+$"; // Only letters and spaces are allowed in the name
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";


        // Password should contain at least 8 characters, including at least one letter and one digit
        if (!binding.name.getText().toString().trim().matches(namePattern)) {
            // If the name doesn't match the pattern, show an error message
            Toast.makeText(Register.this, "Invalid name. Please enter a valid name.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!binding.contact.getText().toString().trim().matches(contactPattern)) {
            // If the contact number doesn't match the pattern, show an error message
            Toast.makeText(Register.this, "Invalid contact number. Please enter a 10-digit number.", Toast.LENGTH_SHORT).show();
            return;
        }

        String password = binding.password.getText().toString().trim();
        String confirmPassword = binding.confirmPassword.getText().toString().trim();

        if (!password.matches(passwordPattern)) {
            // If the password doesn't match the pattern, show an error message
            Toast.makeText(Register.this, "Invalid password. Please choose a password with at least 8 characters, including at least one uppercase letter, one lowercase letter, one digit, and one special character.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            // If the passwords don't match, show an error message
            Toast.makeText(Register.this, "Passwords do not match. Please enter matching passwords.", Toast.LENGTH_SHORT).show();
            return;
        }
        auth.createUserWithEmailAndPassword(
                binding.email.getText().toString().trim(),
                binding.password.getText().toString().trim()
        ).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                // Register user details in the Realtime Database
                saveUserDetailsToDatabase();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String errorMessage = "Registration failed. Please try again.";
                if (e instanceof FirebaseAuthUserCollisionException) {
                    errorMessage = "This email is already registered. Please use another email.";
                } else if (e instanceof FirebaseAuthWeakPasswordException) {
                    errorMessage = "Password is too weak. Please choose a stronger password.";
                } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    errorMessage = "Invalid email format. Please enter a valid email address.";
                }
                Toast.makeText(Register.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserDetailsToDatabase() {
        String selectedCountryCode = binding.countryCode.getSelectedItem().toString();
        String contact = binding.contact.getText().toString().trim();
        HashMap<String, String> map = new HashMap<>();
        map.put("Name", binding.name.getText().toString().trim());
        map.put("Email", binding.email.getText().toString().trim());
        map.put("Contact", selectedCountryCode + contact);
        map.put("password", binding.password.getText().toString().trim());

        databaseReference.child("Users").child(auth.getCurrentUser().getUid())
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        // Successfully registered and saved user details
                        Toast.makeText(Register.this, "Successful registration", Toast.LENGTH_SHORT).show();
                        // Start the login activity or any other activity as needed
                        startActivity(new Intent(Register.this, Login.class));
                        finish(); // Close the current activity if needed
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, "Failed to save user details", Toast.LENGTH_SHORT).show();
                        // Handle the failure, you might want to remove the user created in FirebaseAuth
                        auth.getCurrentUser().delete();
                    }
                });
    }
}
