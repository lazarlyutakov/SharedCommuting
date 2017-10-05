package com.example.lazarlyutakov.sharedcomuttingapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lazarlyutakov.sharedcomuttingapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OfferRideFragment extends Fragment {


    public OfferRideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_offer_ride, container, false);
        return root;
    }

}
