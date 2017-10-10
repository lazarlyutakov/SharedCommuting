package com.example.lazarlyutakov.sharedcomuttingapp.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lazar Lyutakov on 10.10.2017 г..
 */

public class Contact implements Serializable {

    private String contactName;
    private User owner;
    private User driver;


    public Contact(){

    }

    public Contact(String contactName, User driver){
        setContactName(contactName);
        setDriver(driver);
    }

    public Contact(String contactName, User owner, User driver){
        setContactName(contactName);
        setOwner(owner);
        setDriver(driver);
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }


    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("contactName", contactName);
       // result.put("owner", owner);
        result.put("driver", driver);

        return result;
    }
}
