package com.ver_techs.qiff_android.activities;

import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseInstallation;

//Class created for code that is to be run only once, when app is initially launched
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "2WLxFTr9dd04QQf8zClDnwgqvGeKPhmm9f03iaBB", "a4Ac6BAISHDRGU70dxc1mHgjXFfD1vPTyOvCV3DA"); //Initializing parse
        ParseInstallation.getCurrentInstallation().saveInBackground(); //Getting installation object for parse push

        FacebookSdk.sdkInitialize(getApplicationContext()); // Initialize the Facebook SDK
    }
}