package com.ranjesh.facerecognition;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
public class Search extends AppCompatActivity {
    EditText etSearchName;
    Button btnSearch;
    RecyclerView recyclerView;
    ComplaintAdapter complaintAdapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        etSearchName = findViewById(R.id.etSearchName);
        btnSearch = findViewById(R.id.btnSearch);
        recyclerView = findViewById(R.id.recyclerView);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Complaints");

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchName = etSearchName.getText().toString().trim();
                if (!TextUtils.isEmpty(searchName)) {
                    searchComplaintsByName(searchName);
                } else {
                    Toast.makeText(Search.this, "Enter a name to search", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Initialize RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void searchComplaintsByName(String searchName) {
        // Convert the searchName to lowercase for case-insensitive search
        String lowercaseSearchName = searchName.toLowerCase();

        // Use Query to search for complaints by name
        Query query = databaseReference.orderByChild("Name").startAt(lowercaseSearchName).endAt(lowercaseSearchName + "\uf8ff");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ComplaintData> searchResults = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ComplaintData complaintData = snapshot.getValue(ComplaintData.class);
                    if (complaintData != null) {
                        searchResults.add(complaintData);
                    }
                }

                if (!searchResults.isEmpty()) {
                    complaintAdapter = new ComplaintAdapter(searchResults);
                    recyclerView.setAdapter(complaintAdapter);
                } else {
                    Toast.makeText(Search.this, "No complaints found for the given name", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Search.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}