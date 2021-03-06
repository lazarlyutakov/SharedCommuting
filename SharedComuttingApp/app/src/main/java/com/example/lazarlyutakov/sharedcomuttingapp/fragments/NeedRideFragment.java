package com.example.lazarlyutakov.sharedcomuttingapp.fragments;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lazarlyutakov.sharedcomuttingapp.R;
import com.example.lazarlyutakov.sharedcomuttingapp.authentication.loggedIn.DriverDetailsActivity;
import com.example.lazarlyutakov.sharedcomuttingapp.authentication.loggedIn.LoggedInActivity;
import com.example.lazarlyutakov.sharedcomuttingapp.location.FindMyLocationActivity;
import com.example.lazarlyutakov.sharedcomuttingapp.models.User;
import com.example.lazarlyutakov.sharedcomuttingapp.utils.DatabaseHandler;
import com.example.lazarlyutakov.sharedcomuttingapp.utils.Validator;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import mehdi.sakout.fancybuttons.FancyButton;

public class NeedRideFragment extends Fragment implements  View.OnClickListener, AdapterView.OnItemClickListener {

    private FancyButton btnSetLocationNeed;
    private FancyButton btnSearchForOffers;
    private AutoCompleteTextView tvRadiusOfSearch;
    private DatabaseHandler dbHandler;
    private FirebaseAuth auth;
    private Validator validator;
    private ListView lvNearbyDrivers;
    private ArrayAdapter<User> driversAdapter;
    final private ArrayList<User> list = new ArrayList<>();
    private LinearLayout llNeedRide;
    private FancyButton btnGoBack;

    public NeedRideFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_need_ride, container, false);

        btnSetLocationNeed = (FancyButton) root.findViewById(R.id.btn_set_location_need);
        btnSearchForOffers = (FancyButton) root.findViewById(R.id.btn_search_for_offers);
        btnGoBack = (FancyButton)root.findViewById(R.id.btn_go_back_need_ride);
        tvRadiusOfSearch = (AutoCompleteTextView) root.findViewById(R.id.et_set_radius_of_search);
        lvNearbyDrivers = (ListView) root.findViewById(R.id.lv_nearby_drivers);
        llNeedRide = (LinearLayout)root.findViewById(R.id.ll_need_ride);

        auth = FirebaseAuth.getInstance();

        ExpandableHeightListView expandableListView = (ExpandableHeightListView)root.findViewById(R.id.lv_nearby_drivers);

        dbHandler = new DatabaseHandler();
        validator = new Validator();

        btnSearchForOffers.setOnClickListener(this);
        btnSetLocationNeed.setOnClickListener(this);
        btnGoBack.setOnClickListener(this);


        driversAdapter = new ArrayAdapter<User>(getActivity(), android.R.layout.simple_list_item_1) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView view = null;
                if (convertView == null || !(convertView instanceof TextView)) {
                    LayoutInflater viewInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = (TextView) viewInflater.inflate(android.R.layout.simple_list_item_1, null);
                } else {
                    view = (TextView) convertView;
                }

                view.setText(getItem(position).getUsername());

                return view;
            }
        };

        lvNearbyDrivers.setAdapter(driversAdapter);
        expandableListView.setAdapter(driversAdapter);
        expandableListView.setExpanded(true);
        lvNearbyDrivers.setOnItemClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_search_for_offers:
                if (validator.validateSearchDistance(tvRadiusOfSearch) == false) {
                    return;
                }
                Double radius = Double.parseDouble(tvRadiusOfSearch.getText().toString());

                Observable observable = dbHandler.findNearbyDrivers(radius);
                observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<User>>() {
                            @Override
                            public void accept(List<User> users) throws Exception {
                                for (User usr : users) {
                                    list.add(usr);
                                    driversAdapter.add(usr);
                                }

                                llNeedRide.setVisibility(View.GONE);
                            }
                        });

                break;
            case R.id.btn_set_location_need:
                Intent intentLocation = new Intent(getActivity(), FindMyLocationActivity.class);
                startActivity(intentLocation);
                break;
            case R.id.btn_go_back_need_ride:
                Intent backToLoggedIn = new Intent(getActivity(), LoggedInActivity.class);
                startActivity(backToLoggedIn);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        User driverClicked = list.get(i);

        Intent intent = new Intent(getActivity(), DriverDetailsActivity.class);
        intent.putExtra(DriverDetailsActivity.DRIVER_DETAILS, driverClicked);
        getActivity().startActivity(intent);
    }
}

