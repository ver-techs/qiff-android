package com.ver_techs.qiff_android;

import com.parse.Parse;

/**
 * Created by Sony on 9/9/2015.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "2WLxFTr9dd04QQf8zClDnwgqvGeKPhmm9f03iaBB", "a4Ac6BAISHDRGU70dxc1mHgjXFfD1vPTyOvCV3DA");
    }
}