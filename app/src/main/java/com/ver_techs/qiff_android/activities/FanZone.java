package com.ver_techs.qiff_android.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.ver_techs.qiff_android.R;
import com.ver_techs.qiff_android.custom_adapters.ChatCustomAdapter;
import com.ver_techs.qiff_android.object_classes.ChatItem;
import com.ver_techs.qiff_android.object_classes.ChatItemLocal;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // When fields have been recieved

                        final Profile profile = Profile.getCurrentProfile(); //get current user profile details

                        try {
                            userName = object.getString("first_name"); //get first name from the json object
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //AsyncTask to send chat to parse
                        AsyncTask<Void, Void, Bitmap> t = new AsyncTask<Void, Void, Bitmap>() {
                            protected Bitmap doInBackground(Void... p) {
                                Bitmap bm = null; //initialize a bitmap
                                try {
                                    URL url = new URL("http://graph.facebook.com/" + profile.getId() + "/picture?type=small"); //the url of profile picture
                                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                                    boolean redirect = false; //boolean variable to check if there is a need to direct from url or not

                                    int status = connection.getResponseCode();
                                    if (status != HttpURLConnection.HTTP_OK) { //examine the response code of the http connection
                                        if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM || status == HttpURLConnection.HTTP_SEE_OTHER)
                                            redirect = true;
                                    }

                                    if (redirect) { //if there is a need to redirect
                                        // get redirect url from "location" header field
                                        String newUrl = connection.getHeaderField("Location");
                                        // open the new connnection again
                                        connection = (HttpURLConnection) new URL(newUrl).openConnection();
                                    }

                                    InputStream is = connection.getInputStream(); //get data from the connection
                                    BufferedInputStream bis = new BufferedInputStream(is);
                                    bm = BitmapFactory.decodeStream(bis); //decode it intoa bitmap
                                    bis.close(); //close the stream
                                    is.close(); //close the stream
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return bm;
                            }

                            protected void onPostExecute(Bitmap bm) {

                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                bm.compress(Bitmap.CompressFormat.JPEG, 100, stream); //compress the bitmap
                                byte[] bytearray= stream.toByteArray(); //convert the bitmap into a byte array for storing in parse
                                ParseFile file = new ParseFile("dp.jpg",bytearray); //convert the byte array to a parsefile

                                message = message_editText.getText().toString(); //get message from text box

                                if(!message.equals("")) {
                                    ChatItem chatItem = new ChatItem(userName, message, file); //create a new chatitem with username and message

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
                        };
                        t.execute(); //execute the async task
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
        query.addAscendingOrder("createdAt"); //order query results
        query.setLimit(20); //set an upper limit to number of chat item results to be returned
        query.findInBackground(new FindCallback<ChatItem>() {

            public void done(final List<ChatItem> chatItemList, ParseException e) {

                if (e == null) {

                    int i;
                    final int noOfChats = chatItemList.size();
                    // Access the array of results here
                    for (i = 0; i < chatItemList.size(); i++) {
                        Date date = chatItemList.get(i).getCreatedAt();
                        SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm dd MMM "); //time format for display with chat message
                        final String time1 = formatter1.format(date);
                        SimpleDateFormat formatter2 = new SimpleDateFormat("MM dd HH:mm "); //time format to help sort chat items accordingly to chronological order
                        final String time2 = formatter2.format(date);

                        ParseFile fileObject = chatItemList.get(i).getProfilePicture();
                        final int j = i;

                        if ((i % 2) == 0) { //alternate chats are shown on left and right side of the screen

                            fileObject.getDataInBackground(new GetDataCallback() { //fetch the profile picture in the background

                                public void done(byte[] data, ParseException e) {
                                    if (e == null) {
                                        // Decode the Byte array into Bitmap
                                        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);

                                        ChatItemLocal chatItemLocal = new ChatItemLocal(chatItemList.get(j).getUserName(), chatItemList.get(j).getChatMessage(), time1, "right", bmp, time2);
                                        chatItemArrayList.add(chatItemLocal);

                                    } else {
                                        Log.d("aaki", "There was a problem downloading the data.");
                                    }
                                }
                            });
                        } else {

                            fileObject.getDataInBackground(new GetDataCallback() {

                                public void done(byte[] data, ParseException e) {
                                    if (e == null) {
                                        // Decode the Byte array into Bitmap
                                        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);

                                        ChatItemLocal chatItemLocal = new ChatItemLocal(chatItemList.get(j).getUserName(), chatItemList.get(j).getChatMessage(), time1, "left", bmp, time2);
                                        chatItemArrayList.add(chatItemLocal);

                                    } else {
                                        Log.d("aaki", "There was a problem downloading the data.");
                                    }
                                }
                            });
                        }
                    }

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(noOfChats*50); //wait to download the image files and convert into bitmap and save
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() { //after the downloads waiting period is over, call the adapter and set it

                                    Comparator<ChatItemLocal> myComparator = new Comparator<ChatItemLocal>() {
                                        public int compare(ChatItemLocal obj1, ChatItemLocal obj2) {
                                            return obj1.getFullTime().compareTo(obj2.getFullTime());
                                        }
                                    };
                                    Collections.sort(chatItemArrayList, myComparator);

                                    chatCustomAdapter = new ChatCustomAdapter(FanZone.this, chatItemArrayList); //get a new istance of adapter for fixture view
                                    listView.setAdapter(chatCustomAdapter);
                                }
                            });
                        }
                    }).start();

                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });
    }

    public static Bitmap getFacebookProfilePicture(String urlString){
        Bitmap bitmap = null;
        URL url;
        try {
            url = new URL(urlString);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }
        catch (Exception e){
            e.printStackTrace();
        }
            return bitmap;
    }


    @Override
    public void onBackPressed(){ //on back button being pressed, move to main activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}