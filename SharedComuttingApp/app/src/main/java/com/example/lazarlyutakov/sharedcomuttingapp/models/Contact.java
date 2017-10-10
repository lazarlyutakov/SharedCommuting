package com.example.lazarlyutakov.sharedcomuttingapp.models;

/**
 * Created by Lazar Lyutakov on 10.10.2017 г..
 */

public class Contact {

    private String contactName;
    private User user;

    public Contact(){

    }

    public Contact(String contactName, User user){
        setContactName(contactName);
        setUser(user);
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
