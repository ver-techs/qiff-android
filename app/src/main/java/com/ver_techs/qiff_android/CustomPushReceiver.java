package com.ver_techs.qiff_android;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

// Receiver class to receive parse push notifications
public class CustomPushReceiver extends ParsePushBroadcastReceiver {

    private Intent parseIntent;

    public CustomPushReceiver() {
        super();
    }

    @Override
    protected void onPushReceive(Context context, Intent intent) {
        super.onPushReceive(context, intent);

        if (intent == null)
            return;

        try {
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data")); // on receiving a parse push, get the json data out of it

            Log.i("aaki", "Push received: " + json);

            parseIntent = intent; //save the intent into the class varibale

            parsePushJsonAndUpdateApp(context, json); //call this function to parse the json and use it to update app content

        } catch (JSONException e) {
            Log.e("aaki", "Push message json exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPushDismiss(Context context, Intent intent) {
        super.onPushDismiss(context, intent);
    }

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        super.onPushOpen(context, intent);
    }

    /**
     * Parses the push notification json
     */
    private void parsePushJsonAndUpdateApp(Context context, JSONObject json) {
        try {

            JSONObject data = json.getJSONObject("data"); //parse the push json
            String title = data.getString("team1");
            String message = data.getString("team2");

        } catch (JSONException e) {
            Log.e("aaki", "Push message json exception: " + e.getMessage());
        }
    }

}