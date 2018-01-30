package com.example.lazarlyutakov.sharedcomuttingapp.authentication.loggedIn;

import android.content.Intent;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lazarlyutakov.sharedcomuttingapp.R;
import com.example.lazarlyutakov.sharedcomuttingapp.fragments.EnterContactFragment;
import com.example.lazarlyutakov.sharedcomuttingapp.location.FindMyLocationActivity;
import com.example.lazarlyutakov.sharedcomuttingapp.models.Contact;
import com.example.lazarlyutakov.sharedcomuttingapp.models.User;
import com.example.lazarlyutakov.sharedcomuttingapp.utils.DatabaseHandler;
import com.example.lazarlyutakov.sharedcomuttingapp.utils.DrawerCreator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mehdi.sakout.fancybuttons.FancyButton;

public class DriverDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String DRIVER_DETAILS = "Driver details";
    public static final String CLICKED_CONTACT = "clicked contact";

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
    private FancyButton btnAddContact;
    private EnterContactFragment enterContactFragment;
    private LinearLayout llDriversDetails;
    private User user;
    private Contact contact;
    private FancyButton btnSendMessage;
    private PopupWindow msgPopupWindow;
    private LinearLayout driverDetailActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_driver_details);
        driverDetailActivity = (LinearLayout) findViewById(R.id.ll_driver_details);


        Intent intent = getIntent();

        // inflate the layout of the popup
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View msgPopup = inflater.inflate(R.layout.send_message_popup, null);

        // create the poppup
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        msgPopupWindow = new PopupWindow(msgPopup, width, height, focusable);


        user = (User) intent.getSerializableExtra(DRIVER_DETAILS);
        contact = (Contact)intent.getSerializableExtra(CLICKED_CONTACT);

        tvDriverFirstName = (TextView) findViewById(R.id.driver_first_name);
        tvDriverLastName = (TextView) findViewById(R.id.driver_last_name);
        tvDriverPhoneNumber = (TextView) findViewById(R.id.driver_phone_number);
        tvDriverEmail = (TextView) findViewById(R.id.driver_email);
        tvDriverCar = (TextView) findViewById(R.id.driver_car);
        tvDriverSeatsAvailable = (TextView) findViewById(R.id.driver_seats_available);
        btnAddContact = (FancyButton)findViewById(R.id.btn_add_driver_to_contacts);
        btnSendMessage = (FancyButton)findViewById(R.id.btn_send_message);
        llDriversDetails = (LinearLayout)findViewById(R.id.ll_driver_details);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        final DatabaseHandler dbReader = new DatabaseHandler();

        if(user != null) {
            tvDriverFirstName.setText(user.getFirstName());
            tvDriverLastName.setText(user.getLastName());
            tvDriverPhoneNumber.setText(user.getPhoneNumber());
            tvDriverEmail.setText(user.getEmail());
            tvDriverCar.setText(user.getCarModel());
            tvDriverSeatsAvailable.setText(user.getSeatsAvailable());
        }
        if(contact != null){
            btnAddContact.setVisibility(View.GONE);
            btnSendMessage.setVisibility(View.VISIBLE);

            tvDriverFirstName.setText(contact.getDriver().getFirstName());
            tvDriverLastName.setText(contact.getDriver().getLastName());
            tvDriverPhoneNumber.setText(contact.getDriver().getPhoneNumber());
            tvDriverEmail.setText(contact.getDriver().getEmail());
            tvDriverCar.setText(contact.getDriver().getCarModel());
            tvDriverSeatsAvailable.setText(contact.getDriver().getSeatsAvailable());
        }

        btnAddContact.setOnClickListener(this);
        btnSendMessage.setOnClickListener(this);

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
                databaseReference.removeEventListener(this);
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_add_driver_to_contacts :
                enterContactFragment = new EnterContactFragment();

                Bundle bundle = new Bundle();
                bundle.putSerializable("current driver", user);
                enterContactFragment.setArguments(bundle);

                llDriversDetails.setVisibility(View.GONE);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.enter_contact, enterContactFragment)
                        .commit();
                break;
            case R.id.btn_send_message:
                msgPopupWindow.showAtLocation(driverDetailActivity, Gravity.CENTER, 0, 0);
                //Toast.makeText(this, "messsage", Toast.LENGTH_SHORT).show();
                break;
        }


    }
}

