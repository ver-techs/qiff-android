package com.ver_techs.qiff_android.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.parse.ConfigCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseConfig;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.ver_techs.qiff_android.object_classes.ChatItem;
import com.ver_techs.qiff_android.R;
import com.ver_techs.qiff_android.object_classes.LiveCommentaryItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

// Fragment that inflates the home screen
public class HomeFragment extends Fragment{

    SharedPreferences sharedPreferences;
    EditText userName_editText, message_editText;
    String userName, message;
    Boolean touchedOnce = false; //boolean to determine if chatbox is being focused on for first time or not
    Typeface custom_font;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.fragment_home,container,false);

        //setting font to all textviews
        TextView name_team1 = (TextView) v.findViewById(R.id.name_team1);
        TextView name_team2 = (TextView) v.findViewById(R.id.name_team2);
        TextView time = (TextView) v.findViewById(R.id.time);
        TextView score_team1 = (TextView) v.findViewById(R.id.score_team1);
        TextView colon = (TextView) v.findViewById(R.id.colon);
        TextView score_team2 = (TextView) v.findViewById(R.id.score_team2);
        TextView live_commentary = (TextView) v.findViewById(R.id.live_commentary);
        TextView fan_zone = (TextView) v.findViewById(R.id.fan_zone);
        EditText message_box = (EditText) v.findViewById(R.id.message_box);

        custom_font = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.font_path));

        name_team1.setTypeface(custom_font);
        name_team2.setTypeface(custom_font);
        time.setTypeface(custom_font);
        score_team1.setTypeface(custom_font);
        colon.setTypeface(custom_font);
        score_team2.setTypeface(custom_font);
        live_commentary.setTypeface(custom_font);
        fan_zone.setTypeface(custom_font);
        message_box.setTypeface(custom_font);

        message_box.setHintTextColor(getResources().getColor(R.color.color_secondary));

        //ensuring parent and child scrolls views work fine on touch

        ScrollView parentScroll = (ScrollView) v.findViewById(R.id.parentScroll);
        ScrollView childScroll1 = (ScrollView) v.findViewById(R.id.childScroll1);
        ScrollView childScroll2 = (ScrollView) v.findViewById(R.id.childScroll2);

        parentScroll.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                getView().findViewById(R.id.childScroll1).getParent().requestDisallowInterceptTouchEvent(false);
                getView().findViewById(R.id.childScroll2).getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }

        });
        childScroll1.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }

        });
        childScroll2.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }

        });

        //Code to check if user has focused on message edittext, if so, if there is no username yet, get from user

        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        message_editText = (EditText) v.findViewById(R.id.message_box);

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

        Button send_chat_button = (Button) v.findViewById(R.id.send_chat_button);
        send_chat_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //send chat with existing User Name
                sendChatToParse();
            }
        });

        //update fan zone contents
        updateFanZoneWithParseChats();
        //update live commentary content
        updateLiveCommentary();
        //update live score
        updateLiveScore();

        return v;
    }

    protected void updateLiveScore() {
        //populating live score

        ParseConfig.getInBackground(new ConfigCallback() { //call a background function to get parse config value for current or last match id
            @Override
            public void done(ParseConfig config, ParseException e) { //parse query successful
                String currentOrLastMatchId = config.getString("CurrentOrLastMatchId");
                //Log.d("aaki", currentOrLastMatchId);

                final TextView name_team1 = (TextView) v.findViewById(R.id.name_team1);
                final TextView name_team2 = (TextView) v.findViewById(R.id.name_team2);
                final TextView time = (TextView) v.findViewById(R.id.time);
                final TextView score_team1 = (TextView) v.findViewById(R.id.score_team1);
                final TextView colon = (TextView) v.findViewById(R.id.colon);
                final TextView score_team2 = (TextView) v.findViewById(R.id.score_team2);
                final ImageView team1_logo = (ImageView) v.findViewById(R.id.image_team1);
                final ImageView team2_logo = (ImageView) v.findViewById(R.id.image_team2);

                ParseQuery<ParseObject> query = ParseQuery.getQuery("FixtureItem");
                query.getInBackground(currentOrLastMatchId, new GetCallback<ParseObject>() { //get fixture item with id same as config value
                    public void done(ParseObject liveMatchItem, ParseException e) {
                        if (e == null) {

                            name_team1.setText(liveMatchItem.getString("teamName1"));  //set team names
                            name_team2.setText(liveMatchItem.getString("teamName2"));

                            if(liveMatchItem.getString("dateTime").contains("Start")) { //control logic for dateTime field to account for various fixture scenarios
                                time.setText(elapsedMinutes(liveMatchItem.getString("dateTime").replace("Start", ""))); //remove substring start
                            }
                            else if (liveMatchItem.getString("dateTime").contains("Second")) {
                                time.setText(elapsedMinutes(liveMatchItem.getString("dateTime").replace("Second", ""))+ " (2)"); //remove subtsring second
                            }
                            else
                                time.setText(liveMatchItem.getString("dateTime"));

                            score_team1.setText(liveMatchItem.getString("scoreTeam1")); //set scores
                            colon.setText(":");
                            score_team2.setText(liveMatchItem.getString("scoreTeam2"));

                            team1_logo.setImageResource(findTeamLogo(liveMatchItem.getString("teamName1")));
                            team2_logo.setImageResource(findTeamLogo(liveMatchItem.getString("teamName2")));

                        } else {
                            Log.i("aaki", "unsuccessful fetch of fixture item for live score update");
                        }

                    }
                });
            }
        });

    }

    protected void updateLiveCommentary(){
        // Populating Live Commentary zone with chat messages from Parse

        final TableLayout tl = (TableLayout) v.findViewById(R.id.live_commentary_table);

        tl.removeAllViews(); //refresh the fan zone, remove all existing rows from existing tablelayout

        // Parse query to get all live commentary from server

        // Define the class we would like to query
        ParseQuery<LiveCommentaryItem> query = ParseQuery.getQuery(LiveCommentaryItem.class);
        // Execute the find asynchronously
            query.addAscendingOrder("createdAt"); //order query results
        query.findInBackground(new FindCallback<LiveCommentaryItem>() {

            public void done(List<LiveCommentaryItem> liveCommentaryItemList, ParseException e) {

                if (e == null) {

                    // Access the array of results here
                    for (int i = liveCommentaryItemList.size() - 1; i >= 0; i--) {

                        TableRow tr_1 = new TableRow(getActivity());
                        tr_1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

                        TextView minute = new TextView(getActivity());
                        minute.setText(liveCommentaryItemList.get(i).getMinute());
                        minute.setTextSize(16);
                        minute.setTypeface(custom_font, Typeface.BOLD);
                        minute.setTextColor(getResources().getColor(R.color.color_secondary));
                        tr_1.addView(minute);// add the column to the table row here

                        TextView colon = new TextView(getActivity());
                        colon.setText(" :   ");
                        colon.setTextSize(16);
                        colon.setTypeface(custom_font);
                        colon.setTextColor(getResources().getColor(R.color.color_secondary));
                        tr_1.addView(colon);// add the column to the table row here

                        TextView commentary = new TextView(getActivity());
                        commentary.setText(liveCommentaryItemList.get(i).getCommentary());
                        commentary.setTextSize(16);
                        commentary.setTypeface(custom_font);
                        commentary.setTextColor(getResources().getColor(R.color.color_secondary));
                        tr_1.addView(commentary);// add the column to the table row here

                        tl.addView(tr_1, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                    }

                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });
    }

    protected void updateFanZoneWithParseChats(){
        // Polulating Fan zone with chat messages from Parse

        final TableLayout tl = (TableLayout) v.findViewById(R.id.fan_zone_table);

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

                        TableRow tr_1 = new TableRow(getActivity());
                        tr_1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

                        TextView fan_name = new TextView(getActivity());
                        fan_name.setText(chatItemList.get(i).getUserName());
                        fan_name.setTextSize(16);
                        fan_name.setTypeface(custom_font, Typeface.BOLD);
                        fan_name.setTextColor(getResources().getColor(R.color.color_secondary));
                        tr_1.addView(fan_name);// add the column to the table row here

                        TextView colon = new TextView(getActivity());
                        colon.setText(" :   ");
                        colon.setTextSize(16);
                        colon.setTypeface(custom_font);
                        colon.setTextColor(getResources().getColor(R.color.color_secondary));
                        tr_1.addView(colon);// add the column to the table row here

                        TextView message = new TextView(getActivity());
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

    protected void sendChatToParse(){

        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("UserName", "null"); //get username
        message = message_editText.getText().toString(); //get message from text box
        ChatItem chatItem = new ChatItem(userName, message); //create a new chatitem

        Log.i("***username***", chatItem.getUserName());
        Log.i("***message***", chatItem.getChatMessage());

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

        updateFanZoneWithParseChats(); //update fan zone to reflect new chat

    }

    protected void showInputDialog() {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.username_alert_dialog, null); //inflate dialog for username input
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

                        userName = userName_editText.getText().toString();
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

    public String elapsedMinutes(String matchStartTime){ //function to calcultae minutes elapsed since game start for ongoing games
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm"); //using time format hh:mm
        Date timeNow = new Date(); //get current time
        int minutes=0; //no of minutes between current time and matchStartTime
        try {
            minutes = (int)
                    ((simpleDateFormat.parse(simpleDateFormat.format(timeNow)).getTime() -                //parse and get current time
                            simpleDateFormat.parse(matchStartTime.replace("Start", "")).getTime()         //parse and get matchStartTime
                    )*0.00001667);                                                                        //find the difference between the two in milliseconds, convert to minutes
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(minutes) + "\"";  //return the elapsed minutes wid double qoute at the end
    }

    public int findTeamLogo(String teamName){
        // code to find corresponding image of respective teams

        int resource_id = 0;
        switch (teamName){
            case "NADHAM TCR" : resource_id = R.drawable.team_1; break;
            case "KMCC MLP" : resource_id = R.drawable.team_2; break;
            case "KMCC KKD" : resource_id = R.drawable.team_3; break;
            case "KMCC PKD" : resource_id = R.drawable.team_4; break;
            case "SKIA TVM" : resource_id = R.drawable.team_5; break;
            case "KMCC WND" : resource_id = R.drawable.team_6; break;
            case "CFQ PTNMTA" : resource_id = R.drawable.team_1; break;
            case "MAK KKD" : resource_id = R.drawable.team_2; break;
            case "EDSO EKM" : resource_id = R.drawable.team_3; break;
            case "MAMWAQ MLP" : resource_id = R.drawable.team_4; break;
            case "KMCC KNR" : resource_id = R.drawable.team_5; break;
            case "CFQ KKD" : resource_id =  R.drawable.team_6; break;
            case "TYC TCR" : resource_id = R.drawable.team_1; break;
            case "KMCC TCR" : resource_id = R.drawable.team_2; break;
            case "KMCC KSGD" : resource_id = R.drawable.team_3; break;
            case "KPAQ KKD" : resource_id = R.drawable.team_4; break;
        }
        return resource_id;
    }

}