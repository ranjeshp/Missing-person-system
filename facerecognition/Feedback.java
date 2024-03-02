package com.ranjesh.facerecognition;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Feedback extends AppCompatActivity {

    EditText editTextFeedback;
    Button btnSubmitFeedback;
    TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        editTextFeedback = findViewById(R.id.editTextFeedback);
        btnSubmitFeedback = findViewById(R.id.btnSubmitFeedback);
        back = findViewById(R.id.back);

        btnSubmitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitFeedback();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Feedback.this, Home.class));
            }
        });
    }
    private void submitFeedback() {
        // Get the user ID from Firebase Authentication
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            // User is not authenticated, handle accordingly
            return;
        }
        String userId = user.getUid();
        String feedbackText = editTextFeedback.getText().toString().trim();

        if (!TextUtils.isEmpty(feedbackText)) {
            FeedbackModel FeedbackModel = new FeedbackModel(userId, feedbackText);

            // Save the feedback to Firebase Realtime Database
            DatabaseReference feedbackRef = FirebaseDatabase.getInstance().getReference("feedback");
            feedbackRef.push().setValue(FeedbackModel);
            Toast.makeText(this, "Feedback submitted. Thank you!", Toast.LENGTH_SHORT).show();
            // Clear the input field
            editTextFeedback.setText("");
        } else {
            Toast.makeText(this, "Please enter feedback", Toast.LENGTH_SHORT).show();
        }
    }
}