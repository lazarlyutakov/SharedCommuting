package com.example.lazarlyutakov.sharedcomuttingapp.authentication.loggedIn;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lazarlyutakov.sharedcomuttingapp.MainActivity;
import com.example.lazarlyutakov.sharedcomuttingapp.R;
import com.example.lazarlyutakov.sharedcomuttingapp.fragments.ButtonsFragment;
import com.example.lazarlyutakov.sharedcomuttingapp.fragments.UserProfilFragment;
import com.example.lazarlyutakov.sharedcomuttingapp.utils.DatabaseReader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mehdi.sakout.fancybuttons.FancyButton;

public class LoggedInActivity extends AppCompatActivity {

    private TextView tvLoggedInUser;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private String fName;
    private String lName;
    private Fragment userProfilFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        tvLoggedInUser = (TextView)findViewById(R.id.tv_logged_user);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        final DatabaseReader dbReader = new DatabaseReader();


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
            tvLoggedInUser.setVisibility(View.GONE);
            userProfilFragment = new UserProfilFragment();

            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.user_profil_fragment, userProfilFragment)
                    .commit();
        }


    }
}
