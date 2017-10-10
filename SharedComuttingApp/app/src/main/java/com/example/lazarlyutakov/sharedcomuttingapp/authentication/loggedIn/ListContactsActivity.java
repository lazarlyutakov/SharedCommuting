package com.example.lazarlyutakov.sharedcomuttingapp.authentication.loggedIn;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lazarlyutakov.sharedcomuttingapp.R;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class ListContactsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private String fName;
    private String lName;
    private ArrayAdapter<String> contactsAdapter;
    private ListView lvListedContacts;
    private Map<String, Contact> contacts;
    private ArrayList contactNames = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contacts);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        lvListedContacts = (ListView)findViewById(R.id.lv_list_of_contacts);

        contactsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        final DatabaseHandler dbHandler = new DatabaseHandler();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fName = dbHandler.readUserData(dataSnapshot).getFirstName();
                lName = dbHandler.readUserData(dataSnapshot).getLastName();
                DrawerCreator drawer = new DrawerCreator(ListContactsActivity.this, auth, fName, lName);
                drawer.createDrawer(ListContactsActivity.this);

                Observable observable = dbHandler.findCurrUserContacts();
                observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<String>>() {
                            @Override
                            public void accept(List<String> names) throws Exception {
                                for (String name : names) {
                                    contactNames.add(name);
                                }

                                contactsAdapter.addAll(contactNames);
                            }
                        });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseReference.removeEventListener(this);

            }
        });

        lvListedContacts.setAdapter(contactsAdapter);
       // lvListedContacts.setOnItemClickListener(ListContactsActivity.this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}