package com.ver_techs.qiff_android.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.ver_techs.qiff_android.R;
import com.ver_techs.qiff_android.custom_adapters.ChatCustomAdapter;
import com.ver_techs.qiff_android.custom_adapters.UpcomingFixtureCustomAdapter;
import com.ver_techs.qiff_android.object_classes.ChatItem;
import com.ver_techs.qiff_android.object_classes.ChatItemLocal;
import com.ver_techs.qiff_android.object_classes.FixtureItemLocal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//Activity class to display fan chats and allow user to send fan chats
public class FanZone extends Activity {

    EditText message_editText;
    String userName, message;
    Boolean touchedOnce = false; //boolean to determine if chatbox is being focused on for first time or not
    Typeface custom_font;
    ChatCustomAdapter chatCustomAdapter;
    ArrayList<ChatItemLocal> chatItemArrayList;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fan_zone);

        TextView fan_zone = (TextView) findViewById(R.id.fan_zone);
        EditText message_box = (EditText) findViewById(R.id.message_box);

        listView = (ListView) findViewById(R.id.msgview);
        chatItemArrayList = new ArrayList<ChatItemLocal>();

        custom_font = Typeface.createFromAsset(getAssets(), getString(R.string.font_path));

        fan_zone.setTypeface(custom_font);
        message_box.setTypeface(custom_font);

        message_box.setHintTextColor(getResources().getColor(R.color.color_secondary));

        //Code to check if user has focused on message edittext, if so, allow keyboard to pop up

        message_editText = (EditText) findViewById(R.id.message_box);

        message_editText.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // enable focus on message box and allow for viewing keypad and sending text
                view.setFocusable(true);
                view.setFocusableInTouchMode(true);
                return false;
            }
        });

        //Chat send button code - to send chat messages

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
                            userName = object.getString("first_name"); //get first name from the json object
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        message = message_editText.getText().toString(); //get message from text box

                        if(!message.equals("")) {
                            ChatItem chatItem = new ChatItem(userName, message); //create a new chatitem with username and message

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
        parameters.putString("fields", "first_name"); //fields to fetch during facebook api request
        request.setParameters(parameters);
        request.executeAsync(); //execute api call

    }

    protected void updateFanZoneWithParseChats(){
        // Populating Fan zone with chat messages from Parse

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

                        if((i % 2) == 0) { //alternate chats are shown on left and right side of the screen
                            ChatItemLocal chatItemLocal = new ChatItemLocal(chatItemList.get(i).getUserName(), chatItemList.get(i).getChatMessage(), "right");
                            chatItemArrayList.add(chatItemLocal);
                        }
                        else{
                            ChatItemLocal chatItemLocal = new ChatItemLocal(chatItemList.get(i).getUserName(), chatItemList.get(i).getChatMessage(), "left");
                            chatItemArrayList.add(chatItemLocal);
                        }
                    }

                    chatCustomAdapter = new ChatCustomAdapter(FanZone.this, chatItemArrayList); //get a new istance of adapter for fixture view
                    listView.setAdapter(chatCustomAdapter);

                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public void onBackPressed(){ //on back button being pressed, move to main activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}