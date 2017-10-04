package com.example.lazarlyutakov.sharedcomuttingapp.utils;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.example.lazarlyutakov.sharedcomuttingapp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Lazar Lyutakov on 4.10.2017 г..
 */

public class DatabaseReader {

    private final String userId;
    private final FirebaseAuth auth;
    private final FirebaseDatabase database;
    private final DatabaseReference databaseReference;
    private User myUser;


    public DatabaseReader(){
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        FirebaseUser user = auth.getCurrentUser();
        userId = user.getUid();


    }

    public User getMyUser(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                myUser = readUserData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return myUser;
    }

    public User readUserData(DataSnapshot dataSnapshot){
        User currentUser = new User();

        for(DataSnapshot ds : dataSnapshot.getChildren()){

            currentUser.setFirstName(ds.child(userId).getValue(User.class).getFirstName());
            currentUser.setLastName(ds.child(userId).getValue(User.class).getLastName());
            currentUser.setUsername(ds.child(userId).getValue(User.class).getUsername());
            currentUser.setPassword(ds.child(userId).getValue(User.class).getPassword());
            currentUser.setEmail(ds.child(userId).getValue(User.class).getEmail());
            currentUser.setPhoneNumber(ds.child(userId).getValue(User.class).getPhoneNumber());

           // tvLoggedInUser.setText("Hello, " + currentUser.getUsername());
        }

        return currentUser;
    }
}
