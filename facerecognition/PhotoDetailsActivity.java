package com.ranjesh.facerecognition;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
public class PhotoDetailsActivity extends AppCompatActivity {

    ImageView imageView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_details);

        imageView = findViewById(R.id.imageView);
        String photoUrl = getIntent().getStringExtra("photoUrl");
        Picasso.get().load(photoUrl).into(imageView);
    }
}