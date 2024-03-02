package com.ranjesh.facerecognition;

public class User {
    String Name , Surname ,Age ,Contact , Location, ImageUrl;

    public String getName() {
        return Name;
    }

    public String getSurname() {
        return Surname;
    }

    public String getAge() {
        return Age;
    }

    public String getContact() {
        return Contact;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getLocation() {
        return Location;
    }
}
