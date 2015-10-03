package com.ver_techs.qiff_android.activities;

import java.util.Arrays;
import android.content.Intent;
import android.graphics.Paint;
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
import com.ver_techs.qiff_android.R;

public class FacebookLoginActivity extends FragmentActivity {

    private LoginButton loginButton;
    private TextView continueAsGuest;
    CallbackManager callbackManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fb_login);

        if(isLoggedIn()){
            Intent i = new Intent(FacebookLoginActivity.this, MainActivity.class);
            startActivity(i);
        }


        loginButton = (LoginButton) findViewById(R.id.login_button);
        continueAsGuest = (TextView) findViewById(R.id.continueAsGuest);

        continueAsGuest.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG); //underline continue as guest text

        continueAsGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(FacebookLoginActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        callbackManager = CallbackManager.Factory.create(); //create facebook callbackmanager

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.i("aaki", "fb login success");
                Intent i = new Intent(FacebookLoginActivity.this, MainActivity.class);
                startActivity(i);
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
    }

}
