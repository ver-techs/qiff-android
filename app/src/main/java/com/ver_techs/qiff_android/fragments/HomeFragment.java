package com.ver_techs.qiff_android.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
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

import com.melnykov.fab.FloatingActionButton;
import com.parse.ConfigCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseConfig;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.ver_techs.qiff_android.activities.FanZone;
import com.ver_techs.qiff_android.activities.Suggestion;
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
    TextView aboutText;
    FloatingActionButton floatingActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.fragment_home,container,false);

        //setting font to all textviews
        aboutText = (TextView) v.findViewById(R.id.home_fragment_heading_text);
        TextView name_team1 = (TextView) v.findViewById(R.id.name_team1);
        TextView name_team2 = (TextView) v.findViewById(R.id.name_team2);
        TextView score_team1 = (TextView) v.findViewById(R.id.score_team1);
        TextView colon = (TextView) v.findViewById(R.id.colon_home);
        TextView time = (TextView) v.findViewById(R.id.time);
        TextView score_team2 = (TextView) v.findViewById(R.id.score_team2);
        TextView live_commentary = (TextView) v.findViewById(R.id.live_commentary);

        custom_font = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.font_path));

        name_team1.setTypeface(custom_font);
        name_team2.setTypeface(custom_font);
        score_team1.setTypeface(custom_font);
        colon.setTypeface(custom_font);
        time.setTypeface(custom_font);
        score_team2.setTypeface(custom_font);
        live_commentary.setTypeface(custom_font);


        //ensuring parent and child scrolls views work fine on touch

        //ScrollView parentScroll = (ScrollView) v.findViewById(R.id.parentScroll);
        ScrollView childScroll1 = (ScrollView) v.findViewById(R.id.childScroll1);

//        parentScroll.setOnTouchListener(new View.OnTouchListener() {
//
//            public boolean onTouch(View v, MotionEvent event) {
//                getView().findViewById(R.id.childScroll1).getParent().requestDisallowInterceptTouchEvent(false);
//                getView().findViewById(R.id.childScroll2).getParent().requestDisallowInterceptTouchEvent(false);
//                return false;
//            }
//
//        });
        childScroll1.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }

        });

        floatingActionButton = (FloatingActionButton) v.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), FanZone.class);
                startActivity(i);
            }
        });

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
                final TextView colon = (TextView) v.findViewById(R.id.colon_home);
                final TextView score_team2 = (TextView) v.findViewById(R.id.score_team2);
                final ImageView team1_logo = (ImageView) v.findViewById(R.id.image_team1);
                final ImageView team2_logo = (ImageView) v.findViewById(R.id.image_team2);

                ParseQuery<ParseObject> query = ParseQuery.getQuery("FixtureItem");
                query.getInBackground(currentOrLastMatchId, new GetCallback<ParseObject>() { //get fixture item with id same as config value
                    public void done(ParseObject liveMatchItem, ParseException e) {
                        if (e == null) {

                            name_team1.setText(liveMatchItem.getString("teamName1"));  //set team names
                            name_team2.setText(liveMatchItem.getString("teamName2"));

                            if(checkIfMatchDateIsToday(liveMatchItem.getString("dateTime"))){ //check if match is scheduled for today
                                //change home fragment header according to if match is today or not
                                aboutText.setText("TODAY'S MATCH");

                                if(liveMatchItem.getString("matchCompleted").contains("Start"))  //control logic for dateTime field to account for various fixture scenarios
                                    time.setText(elapsedMinutes(liveMatchItem.getString("matchCompleted").replace("Start", ""))); //remove substring start
                                else if (liveMatchItem.getString("matchCompleted").contains("Second"))
                                    time.setText(elapsedMinutes(liveMatchItem.getString("matchCompleted").replace("Second", ""))+ " (2)"); //remove subtsring second
                                else{
                                    int check = matchStartsIn60Minutes(liveMatchItem.getString("dateTime")); //check if match starts in 60 minutes
                                    if(check != -1)  //if yes, print countdown
                                        time.setText("Counting down " + String.valueOf(check) + "\" !");
                                    else //if not, say it is an upcoming match
                                        time.setText("Upcoming Match !");
                                }
                            }
                            else {
                                aboutText.setText("LAST MATCH");
                                time.setText(liveMatchItem.getString("dateTime"));
                            }
                            score_team1.setText(liveMatchItem.getString("scoreTeam1")); //set scores
                            colon.setText("X");
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
                        minute.setTextColor(getResources().getColor(R.color.color_complementary_1));
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

    public boolean checkIfMatchDateIsToday(String matchDate){ //function to check if a match is scheduled for today
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM hh:mm"); //using time format hh:mm
        Boolean result = false;
        Date timeNow = new Date(); //get current date
        int dateDifference, monthDifference;
        try { //get difference between match date and current date
            dateDifference = simpleDateFormat.parse(matchDate).getDate() - //parse the matchDate, get date
                    simpleDateFormat.parse(simpleDateFormat.format(timeNow)).getDate(); //parse the current date, get date
            monthDifference = simpleDateFormat.parse(matchDate).getMonth() - //parse the matchDate, get month
                    simpleDateFormat.parse(simpleDateFormat.format(timeNow)).getMonth(); //parse the current date, get month
            if(dateDifference == 0 && monthDifference == 0) //if the difference between days is zero, the match is scheduled for today
                result=true;
            else
                result=false;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int matchStartsIn60Minutes(String matchDate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM hh:mm"); //using time format hh:mm
        int result = -1;
        Date timeNow = new Date(); //get current date
        int minuteDifference = -1;
        try { //get difference between match date and current date
            minuteDifference =(int) ((simpleDateFormat.parse(matchDate).getTime() - //parse the matchDate, get time in milliseconds
                    simpleDateFormat.parse(simpleDateFormat.format(timeNow)).getTime())*0.00001667); //parse the current date, get time in milliseconds
            if(minuteDifference <= 60) //after converting to minutes, check if minute difference is less than 60
                result = minuteDifference; //if so return the countdown
            else
                result = -1; //if not, return false
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        if(result > 0)
            return result;

        return -1;
    }


    public int findTeamLogo(String teamName){
        // code to find corresponding image of respective teams

        int resource_id = 0;
        switch (teamName){
            case "NADHAM TCR" : resource_id = R.drawable.kmcc_wnd; break;
            case "KMCC MLP" : resource_id = R.drawable.kmcc_mlp; break;
            case "KMCC KKD" : resource_id = R.drawable.kmcc_kkd; break;
            case "KMCC PKD" : resource_id = R.drawable.kmcc_pkd; break;
            case "SKIA TVM" : resource_id = R.drawable.skia_tvm; break;
            case "KMCC WND" : resource_id = R.drawable.kmcc_wnd; break;
            case "CFQ PTNMTA" : resource_id = R.drawable.cfq_ptnmta; break;
            case "MAK KKD" : resource_id = R.drawable.mak_kkd; break;
            case "EDSO EKM" : resource_id = R.drawable.edso_ekm; break;
            case "MAMWAQ MLP" : resource_id = R.drawable.mamwaq_mlp; break;
            case "KMCC KNR" : resource_id = R.drawable.kmcc_knr; break;
            case "CFQ KKD" : resource_id =  R.drawable.cfq_kkd; break;
            case "TYC TCR" : resource_id = R.drawable.tyc_tcr; break;
            case "KMCC TCR" : resource_id = R.drawable.kmcc_tcr; break;
            case "KMCC KSGD" : resource_id = R.drawable.kmcc_ksgd; break;
            case "KPAQ KKD" : resource_id = R.drawable.kpaq_kkd; break;
        }
        return resource_id;
    }

}