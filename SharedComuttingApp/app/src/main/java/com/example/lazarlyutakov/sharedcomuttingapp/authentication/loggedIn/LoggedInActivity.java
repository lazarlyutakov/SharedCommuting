package com.example.lazarlyutakov.sharedcomuttingapp.authentication.loggedIn;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lazarlyutakov.sharedcomuttingapp.R;
import com.example.lazarlyutakov.sharedcomuttingapp.fragments.NeedRideFragment;
import com.example.lazarlyutakov.sharedcomuttingapp.fragments.OfferRideFragment;
import com.example.lazarlyutakov.sharedcomuttingapp.fragments.UserProfilFragment;
import com.example.lazarlyutakov.sharedcomuttingapp.utils.DatabaseHandler;
import com.example.lazarlyutakov.sharedcomuttingapp.utils.DrawerCreator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mehdi.sakout.fancybuttons.FancyButton;

public class LoggedInActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvLoggedInUser;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private String fName;
    private String lName;
    private Fragment userProfilFragment;
    private FancyButton btnOffer;
    private FancyButton btnNeed;
    private Fragment offerRideFragment;
    private Fragment needRideFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        tvLoggedInUser = (TextView)findViewById(R.id.tv_logged_user);
        btnOffer = (FancyButton)findViewById(R.id.btn_offer_ride);
        btnNeed = (FancyButton)findViewById(R.id.btn_need_ride);

        btnOffer.setOnClickListener(this);
        btnNeed.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        final DatabaseHandler dbReader = new DatabaseHandler();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tvLoggedInUser.setText("Hello, " + dbReader.readUserData(dataSnapshot).getUsername());
                fName = dbReader.readUserData(dataSnapshot).getFirstName();
                lName = dbReader.readUserData(dataSnapshot).getLastName();
                DrawerCreator drawer = new DrawerCreator(LoggedInActivity.this, auth, fName, lName);
                drawer.createDrawer(LoggedInActivity.this);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        userProfilFragment = new UserProfilFragment();

        Intent intent = getIntent();
        boolean extra = intent.getBooleanExtra("change", false);

        if(extra == false) {
            return;
        } else {
            userProfilFragment = new UserProfilFragment();
            changeFragment(R.id.user_profil_fragment, userProfilFragment);
        }
    }

    @Override
    public void onClick(View view) {
        offerRideFragment = new OfferRideFragment();
        needRideFragment = new NeedRideFragment();

        switch (view.getId()) {
            case R.id.btn_need_ride :
                changeFragment(R.id.need_ride_fragment, needRideFragment);
                break;
            case R.id.btn_offer_ride:
                changeFragment(R.id.offer_ride_fragment, offerRideFragment);
                break;
        }
    }

    private void changeFragment(Integer id, Fragment fragment){
        tvLoggedInUser.setVisibility(View.GONE);
        btnNeed.setVisibility(View.GONE);
        btnOffer.setVisibility(View.GONE);

        getFragmentManager()
                .beginTransaction()
                .replace(id, fragment)
                .commit();
    }
}
