package com.example.lazarlyutakov.sharedcomuttingapp.authentication.loggedIn;

import android.content.Intent;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.lazarlyutakov.sharedcomuttingapp.R;
import com.example.lazarlyutakov.sharedcomuttingapp.models.User;
import com.example.lazarlyutakov.sharedcomuttingapp.utils.DatabaseHandler;
import com.example.lazarlyutakov.sharedcomuttingapp.utils.DrawerCreator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DriverDetailsActivity extends AppCompatActivity {

    public static final String DRIVER_DETAILS = "Driver details";

    private TextView tvDriverFirstName;
    private TextView tvDriverLastName;
    private TextView tvDriverPhoneNumber;
    private TextView tvDriverEmail;
    private TextView tvDriverCar;
    private TextView tvDriverSeatsAvailable;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private String fName;
    private String lName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_details);

        Intent intent = getIntent();

        User user = (User) intent.getSerializableExtra(DRIVER_DETAILS);

        tvDriverFirstName = (TextView) findViewById(R.id.driver_first_name);
        tvDriverLastName = (TextView) findViewById(R.id.driver_last_name);
        tvDriverPhoneNumber = (TextView) findViewById(R.id.driver_phone_number);
        tvDriverEmail = (TextView) findViewById(R.id.driver_email);
        tvDriverCar = (TextView) findViewById(R.id.driver_car);
        tvDriverSeatsAvailable = (TextView) findViewById(R.id.driver_seats_available);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        final DatabaseHandler dbReader = new DatabaseHandler();

        tvDriverFirstName.setText(user.getFirstName());
        tvDriverLastName.setText(user.getLastName());
        tvDriverPhoneNumber.setText(user.getPhoneNumber());
        tvDriverEmail.setText(user.getEmail());
        tvDriverCar.setText(user.getCarModel());
        tvDriverSeatsAvailable.setText(user.getSeatsAvailable());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fName = dbReader.readUserData(dataSnapshot).getFirstName();
                lName = dbReader.readUserData(dataSnapshot).getLastName();
                DrawerCreator drawer = new DrawerCreator(DriverDetailsActivity.this, auth, fName, lName);
                drawer.createDrawer(DriverDetailsActivity.this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

