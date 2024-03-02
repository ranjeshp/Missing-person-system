package com.ranjesh.facerecognition;

public class Photo {
    private String imageUrl;

    public Photo() {
        // Default constructor required for DataSnapshot.getValue(Photo.class)
    }

    public Photo(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
