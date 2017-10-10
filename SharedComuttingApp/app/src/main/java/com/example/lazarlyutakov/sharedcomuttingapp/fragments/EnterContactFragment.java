package com.example.lazarlyutakov.sharedcomuttingapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.lazarlyutakov.sharedcomuttingapp.R;
import com.example.lazarlyutakov.sharedcomuttingapp.models.User;
import com.example.lazarlyutakov.sharedcomuttingapp.utils.Validator;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnterContactFragment extends Fragment implements View.OnClickListener {


    private AutoCompleteTextView etEnterContactName;
    private FancyButton btnEnterContact;
    private User currDriver;
    private Validator validator;

    public EnterContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_enter_contact, container, false);

        validator = new Validator();

        etEnterContactName = (AutoCompleteTextView)root.findViewById(R.id.et_enter_contact_name);
        btnEnterContact = (FancyButton)root.findViewById(R.id.btn_enter_contact);

        if (getArguments() != null) {
            currDriver = (User)getArguments().getSerializable("current driver");
        }

        btnEnterContact.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View view) {

        if(validator.validateContact(etEnterContactName) == false){
            return;
        }
        String contactName = etEnterContactName.getText().toString();
        Toast.makeText(getContext(), currDriver.getFirstName(), Toast.LENGTH_SHORT).show();
    }
}
