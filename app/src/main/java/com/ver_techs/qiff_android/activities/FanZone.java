package com.ver_techs.qiff_android.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.ver_techs.qiff_android.R;
import com.ver_techs.qiff_android.object_classes.ChatItem;

import java.util.List;

public class FanZone extends Activity {

    SharedPreferences sharedPreferences;
    EditText userName_editText, message_editText;
    String userName, message;
    Boolean touchedOnce = false; //boolean to determine if chatbox is being focused on for first time or not
    Typeface custom_font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fan_zone);

        TextView fan_zone = (TextView) findViewById(R.id.fan_zone);
        EditText message_box = (EditText) findViewById(R.id.message_box);

        custom_font = Typeface.createFromAsset(getAssets(), getString(R.string.font_path));

        fan_zone.setTypeface(custom_font);
        message_box.setTypeface(custom_font);

        message_box.setHintTextColor(getResources().getColor(R.color.color_secondary));

        //Code to check if user has focused on message edittext, if so, if there is no username yet, get from user

        sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        message_editText = (EditText) findViewById(R.id.message_box);

        message_editText.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(touchedOnce == false) { //if message box is being focused first time, get username by showinputdialog()
                    if (sharedPreferences.getString("UserName", "null").equals("null")) {
                        //Get desired User Name from user
                        showInputDialog();
                    }
                    touchedOnce = true; //and set boolean to true
                }else{ //else enable focus on message box and allow for viewing keypad and sending text
                    view.setFocusable(true);
                    view.setFocusableInTouchMode(true);
                }

                return false;
            }
        });

        //Chat send button code - to get username first time, save as sharedpref, use it subsequently, and send chat messages

        Button send_chat_button = (Button) findViewById(R.id.send_chat_button);
        send_chat_button.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick (View v){
                    //send chat with existing User Name
                    sendChatToParse();
                }
            }

        );

        updateFanZoneWithParseChats();

    }


    protected void showInputDialog() {

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.username_alert_dialog, null); //inflate dialog for username input
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
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

                        userName = userName_editText.getText().toString();
                        if (userName.length() > 7) //if username is longer than seven characters, take only the first 7 characters
                            userName=userName.substring(0,6);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("UserName", userName);
                        editor.commit();
                        dialog.cancel(); //cancel dialog after clicking OK
                    }
                });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }

    protected void sendChatToParse(){

        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("UserName", "null"); //get username
        message = message_editText.getText().toString(); //get message from text box
        ChatItem chatItem = new ChatItem(userName, message); //create a new chatitem

        //Log.i("***username***", chatItem.getUserName());
        //Log.i("***message***", chatItem.getChatMessage());

        // Save the data to Parse whenever internet is available
        chatItem.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    updateFanZoneWithParseChats(); //if save was successful, update the fan zone chats to reflect new chat
                } else {
                }
            }
        });

        message_editText.setText(""); //clear the message box

    }

    protected void updateFanZoneWithParseChats(){
        // Polulating Fan zone with chat messages from Parse

        final TableLayout tl = (TableLayout) findViewById(R.id.fan_zone_table);

        tl.removeAllViews(); //refresh the fan zone, remove all existing rows from existing tablelayout

        // Parse query to get all chats from server

        // Define the class we would like to query
        ParseQuery<ChatItem> query = ParseQuery.getQuery(ChatItem.class);
        // Execute the find asynchronously
        query.addAscendingOrder("createdAt"); //order query results
        query.findInBackground(new FindCallback<ChatItem>() {

            public void done(List<ChatItem> chatItemList, ParseException e) {

                if (e == null) {

                    // Access the array of results here
                    for (int i = chatItemList.size() - 1; i >= 0; i--) {

                        TableRow tr_1 = new TableRow(FanZone.this);
                        tr_1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

                        TextView fan_name = new TextView(FanZone.this);
                        fan_name.setText(chatItemList.get(i).getUserName());
                        fan_name.setTextSize(16);
                        fan_name.setTypeface(custom_font, Typeface.BOLD);
                        fan_name.setTextColor(getResources().getColor(R.color.color_black));
                        tr_1.addView(fan_name);// add the column to the table row here

                        TextView colon = new TextView(FanZone.this);
                        colon.setText(" :   ");
                        colon.setTextSize(16);
                        colon.setTypeface(custom_font);
                        colon.setTextColor(getResources().getColor(R.color.color_secondary));
                        tr_1.addView(colon);// add the column to the table row here

                        TextView message = new TextView(FanZone.this);
                        message.setText(chatItemList.get(i).getChatMessage());
                        message.setTextSize(16);
                        message.setTypeface(custom_font);
                        message.setTextColor(getResources().getColor(R.color.color_secondary));
                        tr_1.addView(message);// add the column to the table row here

                        tl.addView(tr_1, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                    }

                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });
    }

}
