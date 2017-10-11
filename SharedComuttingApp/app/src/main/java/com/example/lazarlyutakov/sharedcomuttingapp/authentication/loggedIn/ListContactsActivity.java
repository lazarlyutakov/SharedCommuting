package com.example.lazarlyutakov.sharedcomuttingapp.authentication.loggedIn;

import android.content.Context;
import android.content.Intent;
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
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListContactsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private String fName;
    private String lName;
    private ArrayAdapter<Contact> contactsAdapter;
    private ListView lvListedContacts;
    private Map<String, Contact> contacts;
    private List<Contact> contactList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contacts);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        lvListedContacts = (ListView)findViewById(R.id.lv_list_of_contacts);

        ExpandableHeightListView expandableListView = (ExpandableHeightListView) findViewById(R.id.lv_list_of_contacts);



        contactsAdapter = new ArrayAdapter<Contact>(this, android.R.layout.simple_list_item_1) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView view = null;
                if (convertView == null || !(convertView instanceof TextView)) {
                    LayoutInflater viewInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = (TextView) viewInflater.inflate(android.R.layout.simple_list_item_1, null);
                } else {
                    view = (TextView) convertView;
                }

                view.setText(getItem(position).getContactName());

                return view;
            }
        };


        final DatabaseHandler dbHandler = new DatabaseHandler();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currUser = dbHandler.readUserData(dataSnapshot);
                fName = dbHandler.readUserData(dataSnapshot).getFirstName();
                lName = dbHandler.readUserData(dataSnapshot).getLastName();
                DrawerCreator drawer = new DrawerCreator(ListContactsActivity.this, auth, fName, lName);
                drawer.createDrawer(ListContactsActivity.this);

                contacts = currUser.getContacts();
                Object[] keys = contacts.keySet().toArray();

                for(int i = 0; i < keys.length; i++){
                    Contact contact = contacts.get(keys[i]);
                    contactList.add(contact);
                }


                contactsAdapter.addAll(contactList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseReference.removeEventListener(this);

            }
        });

        lvListedContacts.setAdapter(contactsAdapter);
        expandableListView.setAdapter(contactsAdapter);
        expandableListView.setExpanded(true);
        lvListedContacts.setOnItemClickListener(ListContactsActivity.this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Contact clickedContact = contactList.get(i);

        Intent intent = new Intent(this, DriverDetailsActivity.class);
        intent.putExtra(DriverDetailsActivity.CLICKED_CONTACT, clickedContact);
        startActivity(intent);

    }
}
