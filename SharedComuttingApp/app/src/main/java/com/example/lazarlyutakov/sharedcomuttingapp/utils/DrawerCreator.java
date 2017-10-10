package com.example.lazarlyutakov.sharedcomuttingapp.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.lazarlyutakov.sharedcomuttingapp.MainActivity;
import com.example.lazarlyutakov.sharedcomuttingapp.R;
import com.example.lazarlyutakov.sharedcomuttingapp.authentication.loggedIn.ListContactsActivity;
import com.example.lazarlyutakov.sharedcomuttingapp.authentication.loggedIn.LoggedInActivity;
import com.example.lazarlyutakov.sharedcomuttingapp.fragments.UserProfilFragment;
import com.google.firebase.auth.FirebaseAuth;
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
        final SecondaryDrawerItem main = new SecondaryDrawerItem().withIdentifier(2).withName("Main");
        final SecondaryDrawerItem contacts = new SecondaryDrawerItem().withIdentifier(2).withName("Contacts");
        final SecondaryDrawerItem logout = new SecondaryDrawerItem().withIdentifier(2).withName("Logout");




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
                        main,
                        contacts
                )
                .addDrawerItems(
                        logout
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if(main.isSelected()){
                            Intent intent = new Intent(activity, LoggedInActivity.class);
                            activity.startActivity(intent);

                        }
                        if(contacts.isSelected()){
                            Intent intent = new Intent(activity, ListContactsActivity.class);
                            activity.startActivity(intent);
                        }
                        if(logout.isSelected()){
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
