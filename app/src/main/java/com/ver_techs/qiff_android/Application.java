package com.ver_techs.qiff_android;

import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

/**
 * Created by Sony on 9/9/2015.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "2WLxFTr9dd04QQf8zClDnwgqvGeKPhmm9f03iaBB", "a4Ac6BAISHDRGU70dxc1mHgjXFfD1vPTyOvCV3DA");
        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParsePush.subscribeInBackground("QIFF", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Log.i("aaki", "Successfully subscribed to Parse channel QIFF !");
            }
        });
    }
}