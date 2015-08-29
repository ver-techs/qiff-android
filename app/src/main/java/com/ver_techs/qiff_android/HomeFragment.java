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
import android.widget.TableLayout;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;

public class HomeFragment extends Fragment {

    SharedPreferences sharedPreferences;
    EditText userName_editText, message_editText;
    String userName, message;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_home,container,false);

        //ensuring parent and child scroll views work fine on touch

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

        //Chat send button code - to get username first time, save as sharedpref, use it subsequently, and send chat messages

        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        message_editText = (EditText) v.findViewById(R.id.message_box);

        Button send_chat_button = (Button) v.findViewById(R.id.send_chat_button);
        send_chat_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(sharedPreferences.getString("UserName", "null").equals("null")){

                    //Get desired User Name from user
                    showInputDialog();
                    sendChatToParse();

                }
                else {
                    
                    //send chat with existing User Name
                    sendChatToParse();
                }
            }
        });

        // Parse query to get all chats from server

        // Define the class we would like to query
        ParseQuery<ChatItem> query = ParseQuery.getQuery(ChatItem.class);
        // Execute the find asynchronously
        query.findInBackground(new FindCallback<ChatItem>() {
            public void done(List<ChatItem> chatItemList, ParseException e) {
                if (e == null) {
                    // Access the array of results here
                    for(int i=0; i< chatItemList.size(); i++){

                        Log.i("recieved - ", chatItemList.get(i).getUserName() + " " + chatItemList.get(i).getChatMessage());

                    }
                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });

        // Polulating Fan zone with chat messages from Parse

        TableLayout t1;
        TableLayout tl = (TableLayout) v.findViewById(R.id.fan_zone_table);

        return v;
    }

    protected void sendChatToParse(){

        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("UserName", "null");
        message = message_editText.getText().toString();
        ChatItem chatItem = new ChatItem(userName, message);

        Log.i("***username***", chatItem.getUserName());
        Log.i("***message***", chatItem.getChatMessage());

        // Save the data to Parse whenever internet is available
        chatItem.saveEventually();

    }

    protected void showInputDialog() {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.username_alert_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        userName_editText = (EditText) promptView.findViewById(R.id.userName);

        alertDialogBuilder
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        userName  = userName_editText.getText().toString();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("UserName", userName);
                        editor.commit();
                        Log.i("reached", "reached here");

                    }
                });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }
}