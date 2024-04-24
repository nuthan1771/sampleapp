package com.example.sampleapp;

public class Appuser {
    String email;
    String latitude;
    String longitude;
     String id; // Assuming the ID is a String

    // Other fields and methods

    // Getter and setter for ID
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLatitude() {
        return latitude;
    }
  public Appuser(){};
    public Appuser(String email, String latitude, String longitude) {
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
