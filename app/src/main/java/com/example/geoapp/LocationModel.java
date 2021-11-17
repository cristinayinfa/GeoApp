package com.example.geoapp;

public class LocationModel {

    private int id;
    private String address;
    private String longitude;
    private String latitude;

    //Constructor
    public LocationModel(int id, String address, String longitude, String latitude) {
        this.id = id;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public LocationModel() {
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    // Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
