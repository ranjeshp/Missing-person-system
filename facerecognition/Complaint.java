package com.ranjesh.facerecognition;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ranjesh.facerecognition.databinding.ComplaintBinding;
import java.util.HashMap;

public class Complaint extends AppCompatActivity {

    ComplaintBinding binding;
    DatabaseReference databaseReference;
    SharedPreferences shrd;
    FirebaseAuth auth;
    FirebaseStorage storage;
    StorageReference storageReference;
    Uri imageUri;
    HashMap<String, String> map;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complaint);


        binding = ComplaintBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseReference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        shrd = getSharedPreferences("user", MODE_PRIVATE);
        map = new HashMap<>();
        setSupportActionBar(binding.toolbar);

        setupDrawer();
        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) {
                    uploadComplaintData();
                    Admindata();
                } else {
                    Toast.makeText(Complaint.this, "Please fill all the required fields", Toast.LENGTH_SHORT).show();
                }


            }
        });
        binding.selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openImageChooser();
            }
        });
    }
    private void Admindata() {
        String adminEmail = auth.getCurrentUser().getEmail();

        // Check if the current user is an admin based on their email ID
        if (isAdminUser(adminEmail)) {
            map.put("Name", binding.etName.getText().toString().trim());
            map.put("Surname", binding.etSurname.getText().toString().trim());
            map.put("Age", binding.etAge.getText().toString().trim());
            map.put("Location", binding.etLocation.getText().toString().trim());
            map.put("Contact", binding.contact.getText().toString().trim());

            String complaintId1 = databaseReference.child("Admin").child("Complaint").push().getKey();

            if (imageUri != null) {
                // Specify the path where the image will be stored in Firebase Storage
                StorageReference imageRef = storageReference.child("images/" + auth.getCurrentUser().getUid() + "_" + complaintId1 + "_image.jpg");

                imageRef.putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get the download URL of the uploaded image
                                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        map.put("ImageUrl", uri.toString());

                                        // Save the complaint data to the Realtime Database
                                        databaseReference.child("Admin").child("Complaint").child(auth.getCurrentUser().getUid()).child(complaintId1)
                                                .setValue(map)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Intent intent = new Intent(Complaint.this, PreviewComplaint.class);
                                                        // Pass the complaint ID to the preview activity
                                                        intent.putExtra("ComplaintId", complaintId1);
                                                        startActivity(intent);
                                                        Toast.makeText(Complaint.this, "Submit Successfully!", Toast.LENGTH_SHORT).show();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(Complaint.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Complaint.this, "Image upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                // If no image is selected, save the complaint data without an image URL
                databaseReference.child("Admin").child("Complaint").child(auth.getCurrentUser().getUid()).child(complaintId1)
                        .setValue(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Intent intent = new Intent(Complaint.this, PreviewComplaint.class);
                                // Pass the complaint ID to the preview activity
                                intent.putExtra("ComplaintId", complaintId1);
                                startActivity(intent);
                                Toast.makeText(Complaint.this, "Submit Successfully!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Complaint.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        } else {
            // Handle the case where the current user is not an admin
            Toast.makeText(Complaint.this, "", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isAdminUser(String userEmail) {
        // Implement your logic to determine if the user with the given email is an admin
        // For example, you can check if the email matches a predefined list of admin emails
        // Return true if the user is an admin, false otherwise
        return userEmail.equals("admin@example.com");
    }

    private void setupDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawer, binding.toolbar, R.string.navigation_open, R.string.navigation_close);
        binding.drawer.addDrawerListener(toggle);
        toggle.syncState();
        binding.navigationview.bringToFront();
        // Set item click listener for navigation drawer menu items
        binding.navigationview.setNavigationItemSelectedListener(item -> {

            handleNavigationItemClick(item);
            return true;
        });
    }

    private void handleNavigationItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_complaint:
                Toast.makeText(Complaint.this, "Already Complaint page", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_finder:
                startActivity(new Intent(Complaint.this, Finder.class));
                break;
            case R.id.menu_chatbox:
                startActivity(new Intent(Complaint.this, Chatbox.class));
                break;
            case R.id.menu_feedback:
                startActivity(new Intent(Complaint.this, Feedback.class));
                break;
            case R.id.menu_logout:
                startActivity(new Intent(Complaint.this, Login.class));
                break;
            case R.id.home_menu:
                startActivity(new Intent(Complaint.this, Home.class));
                break;
            case R.id.menu_about:
                startActivity(new Intent(Complaint.this, AboutUs.class));
                break;
        }

        // Close the drawer after handling the click
        binding.drawer.closeDrawer(GravityCompat.START);
    }
    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }


    private void uploadComplaintData() {

        map.put("Name", binding.etName.getText().toString().trim());
        map.put("Surname", binding.etSurname.getText().toString().trim());
        map.put("Age", binding.etAge.getText().toString().trim());
        map.put("Location", binding.etLocation.getText().toString().trim());
        map.put("Contact", binding.contact.getText().toString().trim());

        String complaintId = databaseReference.child("Users").child("Complaint").push().getKey();


        if (imageUri != null) {
            // Specify the path where the image will be stored in Firebase Storage
            StorageReference imageRef = storageReference.child("images/" + auth.getCurrentUser().getUid() + "_" + complaintId + "_image.jpg");

            imageRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get the download URL of the uploaded image
                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
//
                                    // Add the image URL to the complaint data
                                    map.put("ImageUrl", uri.toString());

                                    // Save the complaint data to the Realtime Database
                                    databaseReference.child("Users").child("Complaint").child(auth.getCurrentUser().getUid()).child(complaintId)
                                            .setValue(map)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    databaseReference.child("Users").child("Complaints").child(complaintId)
                                                            .setValue(map);
                                                    Intent intent = new Intent(Complaint.this, PreviewComplaint.class);
                                                    // Pass the complaint ID to the preview activity
                                                    intent.putExtra("ComplaintId", complaintId);
                                                    startActivity(intent);
                                                    Toast.makeText(Complaint.this, "Submit Successfully!", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(Complaint.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Complaint.this, "Image upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            // If no image is selected, save the complaint data without an image URL
            databaseReference.child("Users").child("Complaint").child(auth.getCurrentUser().getUid()).child(complaintId)
                    .setValue(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            databaseReference.child("Users").child("Complaints").child(complaintId)
                                    .setValue(map);
                            Intent intent = new Intent(Complaint.this, PreviewComplaint.class);
                            // Pass the complaint ID to the preview activity
                            intent.putExtra("ComplaintId", complaintId);
                            startActivity(intent);
                            Toast.makeText(Complaint.this, "Submit Successfully!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Complaint.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private boolean validateForm() {
        String namePattern = "^[a-zA-Z ]+$"; // Only letters and spaces are allowed in the name
        String contactPattern = "^[0-9]{10}$"; // Assuming a 10-digit contact number, you can adjust the pattern accordingly
        String agePattern = "^[0-9]{1,3}$";
        String name = binding.etName.getText().toString().trim();
        String surname = binding.etSurname.getText().toString().trim();
        String age = binding.etAge.getText().toString().trim();
        String location = binding.etLocation.getText().toString().trim();
        String contact = binding.contact.getText().toString().trim();

        if (!name.matches(namePattern)) {
            Toast.makeText(Complaint.this, "Invalid name. Please enter a valid name.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!surname.matches(namePattern)) {
            Toast.makeText(Complaint.this, "Invalid surname. Please enter a valid surname.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!age.matches(agePattern)) {
            Toast.makeText(Complaint.this, "Invalid age. Please enter a valid age.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!location.isEmpty() && !contact.matches(contactPattern)) {
            Toast.makeText(Complaint.this, "Invalid contact number. Please enter a 10-digit number.", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            // Display the selected image if needed
            binding.selectedImageView.setImageURI(imageUri);
        }
    }
}
