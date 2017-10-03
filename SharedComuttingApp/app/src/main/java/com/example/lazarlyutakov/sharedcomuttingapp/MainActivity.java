package com.example.lazarlyutakov.sharedcomuttingapp;

import android.content.Intent;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lazarlyutakov.sharedcomuttingapp.fragments.ButtonsFragment;

import mehdi.sakout.fancybuttons.FancyButton;

public class MainActivity extends AppCompatActivity {

    private Fragment btnsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnsFragment = new ButtonsFragment();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.btns_fragment, btnsFragment)
                .commit();
    }
}
