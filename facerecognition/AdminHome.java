package com.ranjesh.facerecognition;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import com.ranjesh.facerecognition.databinding.AdminHomeBinding;
public class AdminHome extends AppCompatActivity {
    AdminHomeBinding binding;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);
        binding = AdminHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        setupDrawer();

        binding.imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHome.this, Complaint.class));
            }
        });

        binding.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHome.this, Finder.class));
            }
        });
        binding.imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHome.this, ComplaintHistory.class));
            }
        });
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
                startActivity(new Intent(AdminHome.this, Complaint.class));
                break;
            case R.id.menu_finder:
                startActivity(new Intent(AdminHome.this, Finder.class));
                break;
            case R.id.menu_chatbox:
                startActivity(new Intent(AdminHome.this, Chatbox.class));
                break;
            case R.id.menu_feedback:
                startActivity(new Intent(AdminHome.this, Feedback.class));
                break;
            case R.id.menu_logout:
                startActivity(new Intent(AdminHome.this, Login.class));
                break;
            case R.id.home_menu:
                Toast.makeText(AdminHome.this, "Already Complaint page", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_about:
                startActivity(new Intent(AdminHome.this, AboutUs.class));
                break;
        }
        // Close the drawer after handling the click
        binding.drawer.closeDrawer(GravityCompat.START);
    }
    @Override
    public void onBackPressed() {
        if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
            binding.drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}







