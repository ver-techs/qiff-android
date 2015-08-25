package com.ver_techs.qiff_android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class HomeFragment extends Fragment {

    SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_home,container,false);

        ScrollView parentScroll=(ScrollView) v.findViewById(R.id.parentScroll);
        ScrollView childScroll=(ScrollView) v.findViewById(R.id.childScroll);

        parentScroll.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                getView().findViewById(R.id.childScroll).getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }

        });
        childScroll.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }

        });

        sharedPreferences = getActivity().getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);

        Button send_chat_button = (Button) v.findViewById(R.id.send_chat_button);
        send_chat_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showInputDialog();
                //Log.i("***username***", sharedPreferences.getString("UserName", "null"));
            }
        });

        return v;
    }

    protected void showInputDialog() {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.username_alert_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        final EditText userName_editText = (EditText) promptView.findViewById(R.id.userName);

        alertDialogBuilder
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

        final String userName  = userName_editText.getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserName", userName);
        editor.commit();

        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(userName);
        //user.setPassword("secret123");
        //user.setEmail("email@example.com");
        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    Log.i("***username success**", "successful signup");
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });
    }
}