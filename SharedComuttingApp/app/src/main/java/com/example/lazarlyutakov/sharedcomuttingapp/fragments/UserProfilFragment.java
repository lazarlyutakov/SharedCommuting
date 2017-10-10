package com.example.lazarlyutakov.sharedcomuttingapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lazarlyutakov.sharedcomuttingapp.R;
import com.example.lazarlyutakov.sharedcomuttingapp.models.User;
import com.example.lazarlyutakov.sharedcomuttingapp.utils.DatabaseHandler;
import com.google.android.gms.plus.model.people.Person;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfilFragment extends Fragment {

    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private TextView fName;
    private TextView lName;
    private TextView phoneNumber;
    private TextView username;
    private TextView email;
    private TextView car;
    private TextView seats;
    private DatabaseHandler dbReader;
    private User currUser;

    public UserProfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user_profil, container, false);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        dbReader = new DatabaseHandler();

        fName = (TextView) root.findViewById(R.id.signed_user_first_name);
        lName = (TextView) root.findViewById(R.id.signed_user_last_name);
        username = (TextView) root.findViewById(R.id.signed_user_username);
        phoneNumber = (TextView) root.findViewById(R.id.signed_user_phone_number);
        email = (TextView) root.findViewById(R.id.signed_user_email);
        car = (TextView) root.findViewById(R.id.signed_user_car);
        seats = (TextView) root.findViewById(R.id.signed_seats_available);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fName.setText(dbReader.readUserData(dataSnapshot).getFirstName());
                lName.setText(dbReader.readUserData(dataSnapshot).getLastName());
                username.setText(dbReader.readUserData(dataSnapshot).getUsername());
                phoneNumber.setText(dbReader.readUserData(dataSnapshot).getPhoneNumber());
                email.setText(dbReader.readUserData(dataSnapshot).getEmail());
                if(dbReader.readUserData(dataSnapshot).getCarModel() != null &&
                        dbReader.readUserData(dataSnapshot).getSeatsAvailable() != null) {
                    car.setText(dbReader.readUserData(dataSnapshot).getCarModel());
                    seats.setText(dbReader.readUserData(dataSnapshot).getSeatsAvailable());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseReference.removeEventListener(this);
            }
        });
        return root;
    }

}
