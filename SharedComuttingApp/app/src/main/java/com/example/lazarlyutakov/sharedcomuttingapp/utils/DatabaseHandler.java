package com.example.lazarlyutakov.sharedcomuttingapp.utils;

import android.support.annotation.NonNull;

import com.example.lazarlyutakov.sharedcomuttingapp.models.Contact;
import com.example.lazarlyutakov.sharedcomuttingapp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class DatabaseHandler {

    private final String userId;
    private final FirebaseAuth auth;
    private final FirebaseDatabase database;
    private final DatabaseReference databaseReference;
    final List<User> usersWithCars = new ArrayList<>();
    User loggedUser;

    public DatabaseHandler() {
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        FirebaseUser user = auth.getCurrentUser();
        userId = user.getUid();
    }

    public User readUserData(DataSnapshot dataSnapshot) {
        User currentUser = new User();

        for (DataSnapshot ds : dataSnapshot.child("Users").getChildren()) {

            if (ds.getKey().equals(userId)) {
                currentUser.setFirstName(ds.getValue(User.class).getFirstName());
                currentUser.setLastName(ds.getValue(User.class).getLastName());
                currentUser.setUsername(ds.getValue(User.class).getUsername());
                currentUser.setPassword(ds.getValue(User.class).getPassword());
                currentUser.setEmail(ds.getValue(User.class).getEmail());
                currentUser.setPhoneNumber(ds.getValue(User.class).getPhoneNumber());
                currentUser.setCarModel(ds.getValue(User.class).getCarModel());
                currentUser.setSeatsAvailable(ds.getValue(User.class).getSeatsAvailable());
                currentUser.setLatitude(ds.getValue(User.class).getLatitude());
                currentUser.setLongitude(ds.getValue(User.class).getLongitude());
            }
        }
        return currentUser;
    }

    public void updateUserContacts(Contact contact) {
        FirebaseUser user = auth.getCurrentUser();
        String uId = user.getUid();
       // String key = database.getReference().child("Contacts").push().getKey();
        Contact newContact = new Contact(contact.getContactName(), contact.getDriver());

        Map<String, Object> contactValues = newContact.toMap();

        Map<String, Object> updates = new HashMap<>();

        updates.put("Users/" + uId + "/" + "contacts", contactValues);
        updates.put("Contacts/" + uId, contactValues);

        databaseReference.updateChildren(updates);
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

    public io.reactivex.Observable<List<User>> findNearbyDrivers(final double radius) {
        return io.reactivex.Observable.create(new ObservableOnSubscribe<List<User>>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<List<User>> e) throws Exception {
                final List<User> nearbyDrivers = new ArrayList<User>();


                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        loggedUser = readUserData(dataSnapshot);
                        double latLogged = loggedUser.getLatitude();
                        double longLogged = loggedUser.getLongitude();

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            for (DataSnapshot eachUser : ds.getChildren()) {
                                User currUser = new User();
                                String currUserCarModel = eachUser.getValue(User.class).getCarModel();

                                if (currUserCarModel != null) {
                                    currUser = eachUser.getValue(User.class);

                                    double latCurr = currUser.getLatitude();
                                    double longCurr = currUser.getLongitude();

                                    double distanceBetweenEsers = distance(latLogged, latCurr, longLogged, longCurr);

                                    if(distanceBetweenEsers <= radius && distanceBetweenEsers != 0){
                                        nearbyDrivers.add(currUser);
                                    }
                                }
                            }
                        }
                        e.onNext(nearbyDrivers);
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


    public static double distance(double lat1, double lat2, double lon1, double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }
}
