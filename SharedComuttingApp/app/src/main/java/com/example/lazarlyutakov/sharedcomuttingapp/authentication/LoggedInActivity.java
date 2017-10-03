package com.example.lazarlyutakov.sharedcomuttingapp.authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lazarlyutakov.sharedcomuttingapp.MainActivity;
import com.example.lazarlyutakov.sharedcomuttingapp.R;
import com.google.firebase.auth.FirebaseAuth;

import mehdi.sakout.fancybuttons.FancyButton;

public class LoggedInActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvLoggedInUser;
    private FancyButton btnLogout;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        tvLoggedInUser = (TextView)findViewById(R.id.tv_logged_user);
        btnLogout = (FancyButton)findViewById(R.id.btn_logout);
        auth = FirebaseAuth.getInstance();

        Intent intent = getIntent();

        Bundle bd = intent.getExtras();
        if(bd != null)
        {
            String loggedUser = (String) bd.get("username");
            tvLoggedInUser.setText("Hello, " + loggedUser);
        }

        btnLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        auth.signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
