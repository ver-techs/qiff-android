package com.ver_techs.qiff_android.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.support.v4.view.ViewPager;

import com.facebook.AccessToken;
import com.facebook.appevents.AppEventsLogger;
import com.parse.ParseObject;
import com.ver_techs.qiff_android.object_classes.LiveCommentaryItem;
import com.ver_techs.qiff_android.object_classes.PointsTableItem;
import com.ver_techs.qiff_android.R;
import com.ver_techs.qiff_android.custom_views.SlidingTabLayout;
import com.ver_techs.qiff_android.custom_adapters.ViewPagerAdapter;
import com.ver_techs.qiff_android.object_classes.ChatItem;
import com.ver_techs.qiff_android.object_classes.FixtureItem;
import com.ver_techs.qiff_android.object_classes.SuggestionItem;

// Main Activity class
public class MainActivity extends ActionBarActivity {

    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;

    CharSequence titles[] = {"Home","Fixture", "Points Table", "About"}; //sequence of title for tabs
    int numbOfTabs = 4;

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
        ParseObject.registerSubclass(LiveCommentaryItem.class);
        ParseObject.registerSubclass(SuggestionItem.class);

        Bundle extras = getIntent().getExtras();
        if(extras != null) { //check if mainactivity is being created normally or from broadcast reciever

            String title = extras.getString("title");
            String message = extras.getString("message");

            Log.i("aaki", "got title - " + title + " and message " + message);

        }
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles for the Tabs and Number Of Tabs
        adapter = new ViewPagerAdapter(this, getSupportFragmentManager(), titles, numbOfTabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.setCurrentItem(0); //setting default tab to tab number 1 (index starting from 0)

        // Assigning the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // This makes the tabs Space Evenly in Available width
        tabs.setCustomTabView(R.layout.layout_sliding_tab, 0);

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.color_white);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    @Override
    public void onBackPressed() {
        //if(isLoggedIn()) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        //}
    }

}