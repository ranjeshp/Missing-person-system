package com.ranjesh.facerecognition;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
public class PreviewComplaint extends AppCompatActivity {
    DatabaseReference databaseReference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.previewcomplaint);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        // Retrieve complaint ID from intent
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("ComplaintId")) {
            String complaintId = extras.getString("ComplaintId");
            displayComplaintData(complaintId);
        }
    }
    private void displayComplaintData(String complaintId) {
        DatabaseReference complaintRef = databaseReference.child("Users").child("Complaint").child(auth.getCurrentUser().getUid()).child(complaintId);
        complaintRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve data from the database
                    String name = dataSnapshot.child("Name").getValue(String.class);
                    String surname = dataSnapshot.child("Surname").getValue(String.class);
                    String age = dataSnapshot.child("Age").getValue(String.class);
                    String location = dataSnapshot.child("Location").getValue(String.class);
                    String contact = dataSnapshot.child("Contact").getValue(String.class);
                    String imageUrl = dataSnapshot.child("ImageUrl").getValue(String.class);

                    // Display data in TextViews or other UI components
                    TextView nameTextView = findViewById(R.id.name);
                    TextView surnameTextView = findViewById(R.id.surname);
                    TextView dateTextView = findViewById(R.id.date);
                    TextView  locationTextView = findViewById(R.id.location);
                    TextView  contactTextView = findViewById(R.id.contact);
                    ImageView imageView = findViewById(R.id.imageViewperson);
                    // Add other TextViews for age, location, contact, and image URL

                    nameTextView.setText("Name: " + name);
                    surnameTextView.setText("Surname: " + surname);
                    dateTextView.setText("Date: " + age);
                    locationTextView.setText("location: " + location);
                    contactTextView.setText("contact: " + contact);

                    // Set other TextViews accordingly
                    Picasso.get().load(imageUrl).into(imageView);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error if needed
            }
        });

    }
}