package com.example.lazarlyutakov.sharedcomuttingapp.models;

/**
 * Created by Lazar Lyutakov on 6.10.2017 г..
 */

public class UserLocation {

    private double latitude;
    private double longitude;

    public UserLocation(double latitude, double longitude){
        setLatitude(latitude);
        setLongitude(longitude);
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
