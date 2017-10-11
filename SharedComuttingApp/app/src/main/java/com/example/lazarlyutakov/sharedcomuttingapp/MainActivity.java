package com.example.lazarlyutakov.sharedcomuttingapp;

import android.content.Intent;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lazarlyutakov.sharedcomuttingapp.authentication.loggedIn.LoggedInActivity;
import com.example.lazarlyutakov.sharedcomuttingapp.fragments.ButtonsFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import mehdi.sakout.fancybuttons.FancyButton;

public class MainActivity extends AppCompatActivity {

    private Fragment btnsFragment;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        FirebaseUser loggedUser = auth.getCurrentUser();

        btnsFragment = new ButtonsFragment();

        if(loggedUser != null){
            Intent intent = new Intent(this, LoggedInActivity.class);
            startActivity(intent);
        }
        else{
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.btns_fragment, btnsFragment)
                    .commit();
        }
    }
}
