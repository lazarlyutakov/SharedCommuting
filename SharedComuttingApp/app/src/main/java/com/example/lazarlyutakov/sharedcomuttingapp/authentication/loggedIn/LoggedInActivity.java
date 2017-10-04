package com.example.lazarlyutakov.sharedcomuttingapp.authentication.loggedIn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lazarlyutakov.sharedcomuttingapp.MainActivity;
import com.example.lazarlyutakov.sharedcomuttingapp.R;
import com.example.lazarlyutakov.sharedcomuttingapp.utils.DatabaseReader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mehdi.sakout.fancybuttons.FancyButton;

public class LoggedInActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvLoggedInUser;
    private FancyButton btnLogout;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        tvLoggedInUser = (TextView)findViewById(R.id.tv_logged_user);
        btnLogout = (FancyButton)findViewById(R.id.btn_logout);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        FirebaseUser user = auth.getCurrentUser();
        userId = user.getUid();
        final DatabaseReader dbReader = new DatabaseReader();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tvLoggedInUser.setText("Hello, " + dbReader.readUserData(dataSnapshot).getUsername());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DrawerCreator drawer = new DrawerCreator(LoggedInActivity.this, auth);
        drawer.createDrawer(this);


        btnLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        auth.signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
