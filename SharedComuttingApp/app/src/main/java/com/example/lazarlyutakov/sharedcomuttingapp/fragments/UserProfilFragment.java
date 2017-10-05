package com.example.lazarlyutakov.sharedcomuttingapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lazarlyutakov.sharedcomuttingapp.R;
import com.example.lazarlyutakov.sharedcomuttingapp.authentication.loggedIn.DrawerCreator;
import com.example.lazarlyutakov.sharedcomuttingapp.authentication.loggedIn.LoggedInActivity;
import com.example.lazarlyutakov.sharedcomuttingapp.utils.DatabaseReader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfilFragment extends Fragment {


    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private TextView fName;
    private TextView lName;
    private TextView phoneNumber;
    private TextView username;
    private TextView email;

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
        final DatabaseReader dbReader = new DatabaseReader();

        fName = (TextView) root.findViewById(R.id.signed_user_first_name);
        lName = (TextView) root.findViewById(R.id.signed_user_last_name);
        username = (TextView) root.findViewById(R.id.signed_user_username);
        phoneNumber = (TextView) root.findViewById(R.id.signed_user_phone_number);
        email = (TextView) root.findViewById(R.id.signed_user_email);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fName.setText(dbReader.readUserData(dataSnapshot).getFirstName());
                lName.setText(dbReader.readUserData(dataSnapshot).getLastName());
                username.setText(dbReader.readUserData(dataSnapshot).getUsername());
                phoneNumber.setText(dbReader.readUserData(dataSnapshot).getPhoneNumber());
                email.setText(dbReader.readUserData(dataSnapshot).getEmail());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return root;
    }

}