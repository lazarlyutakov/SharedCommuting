package com.example.lazarlyutakov.sharedcomuttingapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lazarlyutakov.sharedcomuttingapp.MainActivity;
import com.example.lazarlyutakov.sharedcomuttingapp.R;
import com.example.lazarlyutakov.sharedcomuttingapp.authentication.login.LoginActivity;
import com.example.lazarlyutakov.sharedcomuttingapp.authentication.register.RegisterActivity;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class ButtonsFragment extends Fragment implements View.OnClickListener {


    private FancyButton btnSignIn;
    private FancyButton btnRegister;
    private View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_buttons, container, false);
        btnSignIn = root.findViewById(R.id.btn_sign_in);
        btnRegister = root.findViewById(R.id.btn_register);

        btnSignIn.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register :
                Intent regIntent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(regIntent);
                break;
            case R.id.btn_sign_in:
                Intent signInIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(signInIntent);
                break;
        }

    }
}
