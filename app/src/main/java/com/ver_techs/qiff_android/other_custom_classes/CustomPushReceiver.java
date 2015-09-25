package com.ver_techs.qiff_android.other_custom_classes;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;
import com.ver_techs.qiff_android.activities.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

// Receiver class to receive parse push notifications
public class CustomPushReceiver extends ParsePushBroadcastReceiver {

    private Intent parseIntent;

    public CustomPushReceiver() {
        super();
    }

    @Override
    protected void onPushReceive(Context context, Intent intent) {
        super.onPushReceive(context, intent);

        if (isAppForground(context)) {
            // App is in Foreground, so get the data from the json and refresh app

            try {
                JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data")); // on receiving a parse push, get the json data out of it

                Log.i("aaki", "Push received foreground: " + json);

                parseIntent = intent; //save the intent into the class variable

                parsePushJsonAndUpdateApp(context, json); //call this function to parse the json and use it to update app content

            } catch (JSONException e) {
                Log.e("aaki", "Push message json exception: " + e.getMessage());
            }

        } else {
            // App is in Background, so no need to refresh app

            try {
                JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));

                Log.i("aaki", "Push received background: " + json);

                parseIntent = intent; //save the intent into the class variable

            } catch (JSONException e) {
                Log.e("aaki", "Push message json exception: " + e.getMessage());
            }

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

            JSONObject data = json.getJSONObject("data");
            String title = data.getString("title");
            String message = data.getString("message");

            Intent new_intent = new Intent(context,MainActivity.class);
            new_intent.putExtra("title",title);
            new_intent.putExtra("message",message);
            new_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            Log.i("aaki", "got to parsing");
            context.startActivity(new_intent); //start main activity from here to refresh app

        } catch (JSONException e) {
            Log.e("aaki", "Push message json exception: " + e.getMessage());
        }
    }

    //Method to check if app is in forground or not, ie if app is open or not
    public boolean isAppForground(Context mContext) {

        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(mContext.getPackageName())) {
                return false;
            }
        }

        return true;
    }

}