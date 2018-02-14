package com.triple.triple.Helper;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;

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
import com.triple.triple.Presenter.MainActivity;
import com.triple.triple.Presenter.Mytrips.MytripsActivity;
import com.triple.triple.Presenter.Profile.ProfileActivity;
import com.triple.triple.Presenter.Profile.TravelStyleActivity;
import com.triple.triple.Presenter.Search.SearchActivity;
import com.triple.triple.R;

/**
 * Created by Kevin on 2018/2/11.
 */

public class DrawerUtil {
    public static void getDrawer(final Activity activity, Toolbar toolbar) {
        int iconColor = activity.getResources().getColor(R.color.icon_grey);
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem drawer_home = new PrimaryDrawerItem().withIdentifier(0).withName(R.string.title_home).withIcon(R.drawable.ic_home).withIconColor(iconColor).withIconTintingEnabled(true);
        PrimaryDrawerItem drawer_mytrips = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.title_mytrips).withIcon(R.drawable.ic_suitcase).withIconColor(iconColor).withIconTintingEnabled(true);
        PrimaryDrawerItem drawer_search = new PrimaryDrawerItem().withIdentifier(2).withName(R.string.title_search).withIcon(R.drawable.ic_search).withIconColor(iconColor).withIconTintingEnabled(true);
        PrimaryDrawerItem drawer_travelstyle = new PrimaryDrawerItem().withIdentifier(3).withName(R.string.title_travelstyle).withIcon(R.drawable.ic_tag_faces).withIconColor(iconColor).withIconTintingEnabled(true);
        SecondaryDrawerItem drawer_help = new SecondaryDrawerItem().withIdentifier(4).withName(R.string.title_help).withIcon(R.drawable.ic_help).withIconColor(iconColor).withIconTintingEnabled(true);
        SecondaryDrawerItem drawer_settings = new SecondaryDrawerItem().withIdentifier(5).withName(R.string.title_settings).withIcon(R.drawable.ic_settings).withIconColor(iconColor).withIconTintingEnabled(true);
        SecondaryDrawerItem drawer_about = new SecondaryDrawerItem().withIdentifier(6).withName(R.string.title_about).withIcon(R.drawable.ic_info).withIconColor(iconColor).withIconTintingEnabled(true);

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.side_nav_bar)
                .withSelectionListEnabledForSingleProfile(false)
                .addProfiles(
                        new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon(activity.getResources().getDrawable(R.drawable.icon))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();


        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withAccountHeader(headerResult)
                .withActivity(activity)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggle(false)
                .withSelectedItem(-1)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerWidthDp(280)
                .addDrawerItems(
                        drawer_home,
                        drawer_mytrips,
                        drawer_search,
                        drawer_travelstyle,
                        new DividerDrawerItem(),
                        drawer_help,
                        drawer_settings,
                        drawer_about
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            Intent intent = new Intent();
                            int id = (int) drawerItem.getIdentifier();
                            switch (id) {
                                case 0:
                                    intent.setClass(activity, MainActivity.class);
                                    break;
                                case 1:
                                    intent.setClass(activity, MytripsActivity.class);
                                    break;
                                case 2:
                                    intent.setClass(activity, SearchActivity.class);
                                    break;
                                case 3:
                                    intent.setClass(activity, TravelStyleActivity.class);
                                    break;
                                case 4:
                                    intent.setClass(activity, ProfileActivity.class);
                                    break;
                                case 5:
                                    intent.setClass(activity, ProfileActivity.class);
                                    break;
                                case 6:
                                    intent.setClass(activity, ProfileActivity.class);
                                    break;
                                default:
                                    intent.setClass(activity, MainActivity.class);
                            }
                            view.getContext().startActivity(intent);
                        return true;
                    }
                })
                .build();
    }
}