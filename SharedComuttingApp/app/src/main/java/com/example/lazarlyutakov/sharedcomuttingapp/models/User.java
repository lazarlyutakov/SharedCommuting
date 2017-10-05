package com.example.lazarlyutakov.sharedcomuttingapp.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lazar Lyutakov on 2.10.2017 г..
 */

public class User {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;
    private String email;
    private String carModel;
    private String seatsAvailable;

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

    public User(String username, String password, String firstName, String lastName, String phoneNumber, String email, String carModel,
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
}
