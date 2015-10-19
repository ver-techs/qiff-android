package com.ver_techs.qiff_android.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.ver_techs.qiff_android.R;
import com.ver_techs.qiff_android.object_classes.ChatItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class FanZone extends Activity {

    EditText message_editText;
    String userName = "nothing", message;
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

        message_editText = (EditText) findViewById(R.id.message_box);

        message_editText.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // enable focus on message box and allow for viewing keypad and sending text
                view.setFocusable(true);
                view.setFocusableInTouchMode(true);
                return false;
            }
        });

        //Chat send button code - to get username first time, save as sharedpref, use it subsequently, and send chat messages

        Button send_chat_button = (Button) findViewById(R.id.send_chat_button);
        send_chat_button.setOnClickListener(new View.OnClickListener() {

                                                @Override
                                                public void onClick(View v) {
                                                    //send chat with existing User Name
                                                    sendChatToParse();
                                                }
                                            }

        );

        updateFanZoneWithParseChats();

    }

    protected void sendChatToParse(){

        /* make the facebook API call to get fb username */
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // When fields have been recieved

                        try {
                            userName = object.getString("first_name");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        message = message_editText.getText().toString(); //get message from text box

                        if(!message.equals("")) {
                            ChatItem chatItem = new ChatItem(userName, message); //create a new chatitem

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
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name");
        request.setParameters(parameters);
        request.executeAsync();

    }

    protected void updateFanZoneWithParseChats(){
        // Populating Fan zone with chat messages from Parse

        final TableLayout tl = (TableLayout) findViewById(R.id.fan_zone_table);

        tl.removeAllViews(); //refresh the fan zone, remove all existing rows from existing tablelayout

        // Parse query to get all chats from server

        // Define the class we would like to query
        ParseQuery<ChatItem> query = ParseQuery.getQuery(ChatItem.class);
        // Execute the find asynchronously
        query.addDescendingOrder("createdAt"); //order query results
        query.findInBackground(new FindCallback<ChatItem>() {

            public void done(List<ChatItem> chatItemList, ParseException e) {

                if (e == null) {

                    // Access the array of results here
                    for (int i = chatItemList.size() - 1; i >= 0; i--) {

                        LayoutInflater inflater = (LayoutInflater) (FanZone.this).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View tr_view = inflater.inflate(R.layout.layout_chat_row, null);;

                        TableRow tr_1 = new TableRow(FanZone.this);

                        TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams
                                (TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);

                        int leftMargin = 20;
                        int topMargin = 20;
                        int rightMargin=20;
                        int bottomMargin=20;

                        tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

                        tr_1.setLayoutParams(tableRowParams);

                        TextView fan_name = (TextView) tr_view.findViewById(R.id.userNameRow);
                        fan_name.setText(chatItemList.get(i).getUserName());
                        fan_name.setTextSize(16);
                        fan_name.setTypeface(custom_font, Typeface.BOLD);
                        fan_name.setTextColor(getResources().getColor(R.color.color_black));

                        TextView message = (TextView) tr_view.findViewById(R.id.messageRow);
                        message.setText(chatItemList.get(i).getChatMessage());
                        message.setTextSize(16);
                        message.setTypeface(custom_font, Typeface.BOLD);
                        message.setTextColor(getResources().getColor(R.color.color_black));

                        tr_1.addView(tr_view);
                        tr_1.setBackgroundResource(R.drawable.rectangle_rounded_corner_chat_bubble);

                        tl.addView(tr_1, tableRowParams);

                    }

                    final ScrollView fan_scroll = (ScrollView) findViewById(R.id.fan_zone_scroll);
                    fan_scroll.post(new Runnable() {
                        @Override
                        public void run() {
                            fan_scroll.fullScroll(View.FOCUS_DOWN);
                        }
                    });

                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
