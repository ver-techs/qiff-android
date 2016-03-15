package com.ver_techs.qiff_android.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.appevents.AppEventsLogger;
import com.ver_techs.qiff_android.R;

/**
 * Created by aakif on 14-03-2016.
 */
public class Prediction extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);

        //Disable action bar on top of screen
        getSupportActionBar().hide();

        //set font to header in the fragment
        TextView prediction_text = (TextView) findViewById(R.id.prediction_text);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), getString(R.string.font_path));
        prediction_text.setTypeface(custom_font, Typeface.BOLD);

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

    }
}
