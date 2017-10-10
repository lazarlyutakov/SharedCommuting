package com.example.lazarlyutakov.sharedcomuttingapp.models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Lazar Lyutakov on 2.10.2017 г..
 */

public class User implements Serializable {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;
    private String email;
    private String carModel;
    private String seatsAvailable;
    private double latitude;
    private double longitude;
    private Map<String, Object> contacts = new HashMap<String, Object>();

    public User() {

    }

    public User(String username, String password, String email) {
        setUsername(username);
        setPassword(password);
        setEmail(email);
    }
    public User(String username, String password, String firstName, String lastName, String phoneNumber, String email) {
        setUsername(username);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPhoneNumber(phoneNumber);
    }

    public User(String username, String password, String firstName, String lastName, String phoneNumber, String email,
                String carModel,
                String seatsAvailable) {
        setUsername(username);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setCarModel(carModel);
        setSeatsAvailable(seatsAvailable);
    }

    public User(String username, String password, String firstName, String lastName, String phoneNumber, String email,
                String carModel,
                String seatsAvailable,
                double latitude,
                double longitude) {
        setUsername(username);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setCarModel(carModel);
        setSeatsAvailable(seatsAvailable);
        setLatitude(latitude);
        setLongitude(longitude);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(String seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
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

    public Map<String, Object> getContacts() {
        return contacts;
    }

    public void setContacts(Map<String, Object> contacts) {
        this.contacts = contacts;
    }
}
