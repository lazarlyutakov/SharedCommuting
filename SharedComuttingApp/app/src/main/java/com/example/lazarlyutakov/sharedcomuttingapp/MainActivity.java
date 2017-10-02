package com.example.lazarlyutakov.sharedcomuttingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lazarlyutakov.sharedcomuttingapp.authentication.login.LoginActivity;
import com.example.lazarlyutakov.sharedcomuttingapp.authentication.register.RegisterActivity;

import mehdi.sakout.fancybuttons.FancyButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FancyButton btnSignIn;
    private FancyButton btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = (FancyButton)findViewById(R.id.btn_sign_in);
        btnRegister = (FancyButton)findViewById(R.id.btn_register);

        btnSignIn.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register :
                Intent regIntent = new Intent(this, RegisterActivity.class);
                startActivity(regIntent);
                break;
            case R.id.btn_sign_in:
                Intent signInIntent = new Intent(this, LoginActivity.class);
                startActivity(signInIntent);
                break;
        }

    }
}
