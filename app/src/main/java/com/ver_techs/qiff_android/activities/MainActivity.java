package com.ver_techs.qiff_android.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.view.ViewPager;

import com.parse.ParseObject;
import com.ver_techs.qiff_android.object_classes.PointsTableItem;
import com.ver_techs.qiff_android.R;
import com.ver_techs.qiff_android.custom_views.SlidingTabLayout;
import com.ver_techs.qiff_android.custom_adapters.ViewPagerAdapter;
import com.ver_techs.qiff_android.object_classes.ChatItem;
import com.ver_techs.qiff_android.object_classes.FixtureItem;

// Main Activity class
public class MainActivity extends ActionBarActivity {

    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;

    CharSequence titles[] = {"Fixture", "Home", "Points Table"}; //sequence of title for tabs
    int numbOfTabs = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sliding_tab);

        //Disable action bar on top of screen
        getSupportActionBar().hide();

        // Register the parse models
        ParseObject.registerSubclass(ChatItem.class);
        ParseObject.registerSubclass(FixtureItem.class);
        ParseObject.registerSubclass(PointsTableItem.class);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles for the Tabs and Number Of Tabs
        adapter = new ViewPagerAdapter(this, getSupportFragmentManager(), titles, numbOfTabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.setCurrentItem(1); //setting default tab to tab number 1 (index starting from 0)

        // Assigning the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // This makes the tabs Space Evenly in Available width
        tabs.setCustomTabView(R.layout.layout_sliding_tab, 0);

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.color_accent);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

    }

}