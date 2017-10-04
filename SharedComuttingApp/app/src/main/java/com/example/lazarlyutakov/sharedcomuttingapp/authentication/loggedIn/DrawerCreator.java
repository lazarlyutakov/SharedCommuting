package com.example.lazarlyutakov.sharedcomuttingapp.authentication.loggedIn;

import android.app.Activity;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;

/**
 * Created by Lazar Lyutakov on 4.10.2017 г..
 */

public class DrawerCreator {

    public DrawerCreator() {

    }

    public void createDrawer(Activity activity) {
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("asd");
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName("qwe");

        Drawer drawer = new DrawerBuilder()
                .withActivity(activity)
                //.withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        new SecondaryDrawerItem().withName("kor")
                )
                .addDrawerItems(
                        item2,
                        new SecondaryDrawerItem().withName("Logout")
                )
                /*.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                    }*/
                //})
                .build();
    }
}
