package com.triple.triple.Helper;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.triple.triple.Presenter.MainActivity;
import com.triple.triple.R;

/**
 * Created by Kevin on 2018/2/11.
 */

public class DrawerUtil {
    public static void getDrawer(final Activity activity, Toolbar toolbar) {
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem drawerEmptyItem= new PrimaryDrawerItem().withIdentifier(0).withName("");drawerEmptyItem.withEnabled(false);
        PrimaryDrawerItem drawer_home = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.title_home).withIcon(R.drawable.ic_home);
        PrimaryDrawerItem drawer_mytrips = new PrimaryDrawerItem().withIdentifier(2).withName(R.string.title_mytrips).withIcon(R.drawable.ic_suitcase);
        PrimaryDrawerItem drawer_search = new PrimaryDrawerItem().withIdentifier(3).withName(R.string.title_search).withIcon(R.drawable.ic_search);
        PrimaryDrawerItem drawer_travelstyle = new PrimaryDrawerItem().withIdentifier(4).withName(R.string.title_travelstyle).withIcon(R.drawable.ic_tag_faces);
        SecondaryDrawerItem drawer_help = new SecondaryDrawerItem().withIdentifier(5).withName(R.string.title_help).withIcon(R.drawable.ic_help);
        SecondaryDrawerItem drawer_settings = new SecondaryDrawerItem().withIdentifier(6).withName(R.string.title_settings).withIcon(R.drawable.ic_settings);
        SecondaryDrawerItem drawer_about = new SecondaryDrawerItem().withIdentifier(7).withName(R.string.title_about).withIcon(R.drawable.ic_info);

        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withCloseOnClick(true)
                .withSelectedItem(-1)
                .addDrawerItems(
                        drawerEmptyItem,drawerEmptyItem,drawerEmptyItem,
                        drawer_home,
                        drawer_mytrips,
                        drawer_search,
                        drawer_travelstyle,
                        new DividerDrawerItem(),
                        drawer_help,
                        drawer_settings,
                        drawer_settings
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem.getIdentifier() == 2 && !(activity instanceof MainActivity)) {
                            // load tournament screen
                            Intent intent = new Intent(activity, MainActivity.class);
                            view.getContext().startActivity(intent);
                        }
                        return true;
                    }
                })
                .build();
    }
}