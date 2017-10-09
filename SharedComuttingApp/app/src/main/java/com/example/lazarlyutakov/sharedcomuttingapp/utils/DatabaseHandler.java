package com.example.lazarlyutakov.sharedcomuttingapp.utils;

import android.app.Activity;
import android.location.Location;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.example.lazarlyutakov.sharedcomuttingapp.models.User;
import com.example.lazarlyutakov.sharedcomuttingapp.models.UserLocation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by Lazar Lyutakov on 4.10.2017 г..
 */

public class DatabaseHandler {

    private final String userId;
    private final FirebaseAuth auth;
    private final FirebaseDatabase database;
    private final DatabaseReference databaseReference;
    private User myUser;
    final List<User> usersWithCars = new ArrayList<>();


    public DatabaseHandler() {
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        FirebaseUser user = auth.getCurrentUser();
        userId = user.getUid();


    }

    public User getMyUser() {
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

    public User readUserData(DataSnapshot dataSnapshot) {
        User currentUser = new User();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            currentUser.setFirstName(ds.child(userId).getValue(User.class).getFirstName());
            currentUser.setLastName(ds.child(userId).getValue(User.class).getLastName());
            currentUser.setUsername(ds.child(userId).getValue(User.class).getUsername());
            currentUser.setPassword(ds.child(userId).getValue(User.class).getPassword());
            currentUser.setEmail(ds.child(userId).getValue(User.class).getEmail());
            currentUser.setPhoneNumber(ds.child(userId).getValue(User.class).getPhoneNumber());
            currentUser.setCarModel(ds.child(userId).getValue(User.class).getCarModel());
            currentUser.setSeatsAvailable(ds.child(userId).getValue(User.class).getSeatsAvailable());
            currentUser.setLatitude(ds.child(userId).getValue(User.class).getLatitude());
            currentUser.setLongitude(ds.child(userId).getValue(User.class).getLongitude());
        }

        return currentUser;
    }

    public void updateUserCoords(double latitude, double longitude) {
        FirebaseUser user = auth.getCurrentUser();
        String uId = user.getUid();

        Map<String, Object> updates = new HashMap<>();

        updates.put("Users/" + uId + "/latitude", latitude);
        updates.put("Users/" + uId + "/longitude", longitude);

        databaseReference.updateChildren(updates);
    }

    public void updateUserCarDetails(String car, String seatsAvailable) {
        FirebaseUser user = auth.getCurrentUser();
        String uId = user.getUid();

        Map<String, Object> updates = new HashMap<>();

        updates.put("Users/" + uId + "/" + "carModel", car);
        updates.put("Users/" + uId + "/" + "seatsAvailable", seatsAvailable);

        databaseReference.updateChildren(updates);
    }

    public io.reactivex.Observable<List<User>> findUserWithCars() {
        return io.reactivex.Observable.create(new ObservableOnSubscribe<List<User>>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<List<User>> e) throws Exception {

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            for (DataSnapshot eachUser : ds.getChildren()) {
                                User currUser = new User();
                                String currUserCarModel = eachUser.getValue(User.class).getCarModel();

                                if (currUserCarModel != null) {
                                    currUser = eachUser.getValue(User.class);
                                    usersWithCars.add(currUser);
                                }
                            }
                        }
                        e.onNext(usersWithCars);
                        e.onComplete();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        databaseReference.removeEventListener(this);
                    }
                });
            }
        });
    }
}
