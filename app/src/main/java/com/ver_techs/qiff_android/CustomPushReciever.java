package com.ver_techs.qiff_android;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ravi on 01/06/15.
 */

public class CustomPushReciever extends ParsePushBroadcastReceiver {

    private Intent parseIntent;

    public CustomPushReciever() {
        super();
    }

    @Override
    protected void onPushReceive(Context context, Intent intent) {
        super.onPushReceive(context, intent);

        if (intent == null)
            return;

        try {
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));

            Log.i("aaki", "Push received: " + json);

            parseIntent = intent;

            parsePushJson(context, json);

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

    private void parsePushJson(Context context, JSONObject json) {
        try {

            JSONObject data = json.getJSONObject("data");
            String title = data.getString("team1");
            String message = data.getString("team2");

            Intent resultIntent = new Intent(context, MainActivity.class);

        } catch (JSONException e) {
            Log.e("aaki", "Push message json exception: " + e.getMessage());
        }
    }

}