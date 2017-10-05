package com.example.lazarlyutakov.sharedcomuttingapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.lazarlyutakov.sharedcomuttingapp.MainActivity;
import com.example.lazarlyutakov.sharedcomuttingapp.R;
import com.example.lazarlyutakov.sharedcomuttingapp.authentication.loggedIn.LoggedInActivity;
import com.example.lazarlyutakov.sharedcomuttingapp.fragments.UserProfilFragment;
import com.example.lazarlyutakov.sharedcomuttingapp.utils.DatabaseReader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by Lazar Lyutakov on 4.10.2017 г..
 */

public class DrawerCreator {

    private FirebaseAuth auth;
    private Drawer drawer;
    private Drawer.OnDrawerItemClickListener onDrawerItemClickListener;
    private Activity activity;
    private String fName;
    private String lName;
    private boolean toChange = false;

    public DrawerCreator() {

    }

    public DrawerCreator(Activity activity, FirebaseAuth auth, String fName, String lName){
        super();
        this.auth = FirebaseAuth.getInstance();
        this.activity = activity;
        this.fName = fName;
        this.lName = lName;

    }

    public void createDrawer(final Activity activity) {
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("asd");
        final SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName("Main");
        final SecondaryDrawerItem item3 = new SecondaryDrawerItem().withIdentifier(2).withName("Contacts");
        final SecondaryDrawerItem item4 = new SecondaryDrawerItem().withIdentifier(2).withName("Logout");




        this.drawer = new DrawerBuilder()
                .withAccountHeader(new AccountHeaderBuilder()
                        .withActivity(activity)
                        //.withHeaderBackground(R.drawable.ic_menu_gallery)
                        .addProfiles(
                                new ProfileDrawerItem()
                                        .withName(fName + " " + lName)
                        )
                        .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                            @Override
                            public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                                // opens fragment with user profile details
                                toChange = true;
                                Intent intent = new Intent(activity, LoggedInActivity.class);
                                intent.putExtra("change", toChange);
                                activity.startActivity(intent);
                                toChange = false;
                                return false;
                            }
                        })
                        .build())
                .withActivity(activity)
                //.withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        item3
                )
                .addDrawerItems(
                        item4
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if(item2.isSelected()){
                            Toast.makeText(activity, "clicked item 2", Toast.LENGTH_SHORT).show();

                        }
                        if(item3.isSelected()){
                            Toast.makeText(activity, "clicked item 3", Toast.LENGTH_SHORT).show();

                        }
                        if(item4.isSelected()){
                            auth.signOut();
                            Intent intent = new Intent(activity, MainActivity.class);
                            activity.startActivity(intent);
                            Toast.makeText(activity, "You signed out successfuly", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                })
                .build();
    }

}
