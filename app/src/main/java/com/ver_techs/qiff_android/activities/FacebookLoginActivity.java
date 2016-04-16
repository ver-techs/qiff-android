package com.ver_techs.qiff_android.activities;

import java.util.Arrays;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.ver_techs.qiff_android.R;

public class FacebookLoginActivity extends FragmentActivity {

    private LoginButton loginButton;
    CallbackManager callbackManager;
    String intent_message="null";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            intent_message = extras.getString("next_activity");
            if(isLoggedIn() && intent_message.equalsIgnoreCase("chat")){
                Intent i = new Intent(FacebookLoginActivity.this, FanZone.class);
                startActivity(i);
            }
            else
            if(isLoggedIn() && intent_message.equalsIgnoreCase("prediction")){
                Intent i = new Intent(FacebookLoginActivity.this, Prediction.class);
                startActivity(i);
            }
            else
            if(isLoggedIn() && intent_message.equalsIgnoreCase("status_update")){
                // Open fb dialog for sharing status on facebook
                ShareDialog shareDialog;
                shareDialog = new ShareDialog(this);
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("QIFF '16")
                            .setContentDescription(
                                    "The QIFF 2016 Android Application showcases Live scores, prediction games, fan chats and much more !")
                            .setContentUrl(Uri.parse("https://www.facebook.com/QatarIndianFootballForum"))
                            .build();
                    shareDialog.show(linkContent);
                }
            }
        }
        else
        if(isLoggedIn()){
            Intent i = new Intent(FacebookLoginActivity.this, MainActivity.class);
            startActivity(i);
        }

        setContentView(R.layout.activity_fb_login);

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setBackgroundResource(R.drawable.icon_facebook_reverse);
        loginButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);

        callbackManager = CallbackManager.Factory.create(); //create facebook callbackmanager

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.i("aaki", "fb login success");
                if(intent_message.equalsIgnoreCase("chat")) {
                    Intent i = new Intent(FacebookLoginActivity.this, FanZone.class);
                    startActivity(i);
                }
                else{
                    Intent i = new Intent(FacebookLoginActivity.this, Prediction.class);
                    startActivity(i);
                }
            }

            @Override
            public void onCancel() {
                // App code
                Log.i("aaki", "fb login cancel");
                Intent i = new Intent(FacebookLoginActivity.this, MainActivity.class);
                startActivity(i);
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.i("aaki", "fb login error");
            }
        });
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Intent i = new Intent(FacebookLoginActivity.this, MainActivity.class);
        startActivity(i);
    }

}
