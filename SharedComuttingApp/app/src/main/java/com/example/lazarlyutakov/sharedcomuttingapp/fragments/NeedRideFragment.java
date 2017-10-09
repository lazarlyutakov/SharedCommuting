package com.example.lazarlyutakov.sharedcomuttingapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.lazarlyutakov.sharedcomuttingapp.R;
import com.example.lazarlyutakov.sharedcomuttingapp.authentication.loggedIn.LoggedInActivity;
import com.example.lazarlyutakov.sharedcomuttingapp.location.FindMyLocationActivity;
import com.example.lazarlyutakov.sharedcomuttingapp.models.User;
import com.example.lazarlyutakov.sharedcomuttingapp.utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class NeedRideFragment extends Fragment implements View.OnClickListener {


    private FancyButton btnSetLocationNeed;
    private FancyButton btnSearchForOffers;
    private AutoCompleteTextView tvRadiusOfSearch;
    private DatabaseHandler dbHandler;

    public NeedRideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_need_ride, container, false);

        btnSetLocationNeed = (FancyButton)root.findViewById(R.id.btn_set_location_need);
        btnSearchForOffers = (FancyButton)root.findViewById(R.id.btn_search_for_offers);
        tvRadiusOfSearch = (AutoCompleteTextView)root.findViewById(R.id.et_set_radius_of_search);

        dbHandler = new DatabaseHandler();

        btnSearchForOffers.setOnClickListener(this);
        btnSetLocationNeed.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search_for_offers :
                Observable observable = dbHandler.findUserWithCars();
                observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<User>>() {
                            @Override
                            public void accept(List<User> users) throws Exception {
                                List<User> usrs = new ArrayList<User>();
                                for(User item: users){
                                    System.out.println("LLLLLLLLLLL + " + item.getLatitude());
                                }

                                System.out.println("LNJKLNJLNN " + users.size());
                            }
                        });

                break;
            case R.id.btn_set_location_need:
                Intent intentLocation = new Intent(getActivity(), FindMyLocationActivity.class);
                startActivity(intentLocation);
                break;
        }

    }

    private void findNearbyDrivers(){
        Double radius = Double.parseDouble(tvRadiusOfSearch.getText().toString());

    }
}
