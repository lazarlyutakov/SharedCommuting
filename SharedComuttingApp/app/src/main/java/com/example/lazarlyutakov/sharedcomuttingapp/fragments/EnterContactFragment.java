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
import com.example.lazarlyutakov.sharedcomuttingapp.authentication.loggedIn.DriverDetailsActivity;
import com.example.lazarlyutakov.sharedcomuttingapp.models.Contact;
import com.example.lazarlyutakov.sharedcomuttingapp.models.User;
import com.example.lazarlyutakov.sharedcomuttingapp.utils.DatabaseHandler;
import com.example.lazarlyutakov.sharedcomuttingapp.utils.DrawerCreator;
import com.example.lazarlyutakov.sharedcomuttingapp.utils.Validator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnterContactFragment extends Fragment implements View.OnClickListener {


    private AutoCompleteTextView etEnterContactName;
    private FancyButton btnEnterContact;
    private User currDriver;
    private Validator validator;
    private Contact contact;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private DatabaseHandler dbHandler;
    private User loggedUser;

    public EnterContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_enter_contact, container, false);

        validator = new Validator();
        contact = new Contact();

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        dbHandler = new DatabaseHandler();

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
        final String contactName = etEnterContactName.getText().toString();
        contact.setContactName(contactName);
        contact.setDriver(currDriver);
        FirebaseUser fbUser = auth.getCurrentUser();
        String uId = fbUser.getUid();


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loggedUser = dbHandler.readUserData(dataSnapshot);
                Map<String, Contact> jj = loggedUser.getContacts();
                // contact.setOwner(loggedUser);
                jj.put(contactName, contact);
                System.out.println("BBBBBBBBBBBB " + jj.size());
                dbHandler.updateUserContacts(contact);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseReference.removeEventListener(this);
            }
        });


        Toast.makeText(getContext(), currDriver.getFirstName(), Toast.LENGTH_SHORT).show();
    }
}