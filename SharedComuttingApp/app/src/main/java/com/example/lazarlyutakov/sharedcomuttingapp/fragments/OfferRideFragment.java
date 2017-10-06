package com.example.lazarlyutakov.sharedcomuttingapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.example.lazarlyutakov.sharedcomuttingapp.R;
import com.example.lazarlyutakov.sharedcomuttingapp.authentication.loggedIn.LoggedInActivity;
import com.example.lazarlyutakov.sharedcomuttingapp.location.FindMyLocationActivity;
import com.example.lazarlyutakov.sharedcomuttingapp.utils.DatabaseHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class OfferRideFragment extends Fragment implements View.OnClickListener {


    private AutoCompleteTextView etCar;
    private AutoCompleteTextView etSeatsAvailable;
    private FancyButton btnSubmitOffer;
    private DatabaseReference database;
    private FirebaseAuth auth;
    private DatabaseHandler dbHandler;
    private String car;
    private String seatsAvailable;
    private FancyButton btnSetLocation;

    public OfferRideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_offer_ride, container, false);

        etCar = (AutoCompleteTextView) root.findViewById(R.id.et_car);
        etSeatsAvailable = (AutoCompleteTextView) root.findViewById(R.id.et_available_seats);

        btnSubmitOffer = (FancyButton) root.findViewById(R.id.btn_offer_submit);
        btnSubmitOffer.setOnClickListener(this);

        btnSetLocation = (FancyButton) root.findViewById(R.id.btn_set_location_offer);
        btnSetLocation.setOnClickListener(this);

        database = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        dbHandler = new DatabaseHandler();

        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_offer_submit :
                car = etCar.getText().toString();
                seatsAvailable = etSeatsAvailable.getText().toString();
                dbHandler.updateUserCarDetails(car, seatsAvailable);
                Intent intent = new Intent(getActivity(), LoggedInActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_set_location_offer:
                Intent intentLocation = new Intent(getActivity(), FindMyLocationActivity.class);
                startActivity(intentLocation);
                break;
        }

    }
}
