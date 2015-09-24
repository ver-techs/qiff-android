package com.ver_techs.qiff_android.custom_adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ver_techs.qiff_android.R;
import com.ver_techs.qiff_android.object_classes.FixtureItemLocal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

// Adapter for Fixture tab
public class FixtureCustomAdapter extends BaseAdapter {

    private Context context;
    ArrayList<FixtureItemLocal> fixtureItemArrayList; //Local variable list that stores all fixture items

    public FixtureCustomAdapter(Context context, ArrayList<FixtureItemLocal> fixtureItemArray) {
        this.context = context;
        this.fixtureItemArrayList = fixtureItemArray;
        //Log.i("aaki",Integer.toString(fixtureItemArrayList.size()));
    }

    @Override
    public int getCount() {
        return fixtureItemArrayList.size(); //size of the list
    }

    @Override
    public Object getItem(int i) {
        return fixtureItemArrayList.get(i);    // single item in the list
    }

    @Override
    public long getItemId(int i) {
        return i; // index number
    }

    @Override
    public View getView(int index, View view, final ViewGroup parent) {

        //Log.i("aaki", "getView called");
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext()); //inflate a list item progress_spin
            view = inflater.inflate(R.layout.fixture_list_item, parent, false);
        }

        final FixtureItemLocal fixtureItemLocal = fixtureItemArrayList.get(index); //get the fixture item from the list to populate into the progress_spin

        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), context.getString(R.string.font_path));

        //set the textviews and imageviews
        TextView teamName1 = (TextView) view.findViewById(R.id.name_team1_fixture);
        teamName1.setText(fixtureItemLocal.getTeamName1());
        teamName1.setTypeface(custom_font);

        TextView teamName2 = (TextView) view.findViewById(R.id.name_team2_fixture);
        teamName2.setText(fixtureItemLocal.getTeamName2());
        teamName2.setTypeface(custom_font);

        TextView scoreTeam1 = (TextView) view.findViewById(R.id.score_team1_fixture);
        scoreTeam1.setText(fixtureItemLocal.getScoreTeam1());
        scoreTeam1.setTypeface(custom_font);

        TextView colon_fixture = (TextView) view.findViewById(R.id.colon_fixture);
        colon_fixture.setTypeface(custom_font);

        TextView scoreTeam2 = (TextView) view.findViewById(R.id.score_team2_fixture);
        scoreTeam2.setText(fixtureItemLocal.getScoreTeam2());
        scoreTeam2.setTypeface(custom_font);

        TextView time = (TextView) view.findViewById(R.id.time_fixture);
        if(fixtureItemLocal.getTimeDate().contains("Start")) { //control logic for dateTime field to account for various fixture scenarios
            time.setText(elapsedMinutes(fixtureItemLocal.getTimeDate().replace("Start", ""))); //remove substring start
        }
        else if (fixtureItemLocal.getTimeDate().contains("Second")) {
            time.setText(elapsedMinutes(fixtureItemLocal.getTimeDate().replace("Second", ""))+ " (2)"); //remove subtsring second
        }
        else
            time.setText(fixtureItemLocal.getTimeDate());
        time.setTypeface(custom_font);

        ImageView team1_logo = (ImageView) view.findViewById(R.id.image_team1_fixture);
        team1_logo.setImageResource(findTeamLogo(fixtureItemLocal.getTeamName1()));

        ImageView team2_logo = (ImageView) view.findViewById(R.id.image_team2_fixture);
        team2_logo.setImageResource(findTeamLogo(fixtureItemLocal.getTeamName2()));

        return view;
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

    public String elapsedMinutes(String matchStartTime){ //function to calcultae minutes elapsed since game start for ongoing games
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm"); //using time format hh:mm
        Date timeNow = new Date(); //get current time
        int minutes=0; //no of minutes between current time and matchStartTime
        try {
            minutes = (int)
                    ((simpleDateFormat.parse(simpleDateFormat.format(timeNow)).getTime() -                //parse and get current time
                            simpleDateFormat.parse(matchStartTime.replace("Start", "")).getTime()         //parse and get matchStartTime
                    )*0.00001667);                                                                        //find the difference between the two in milliseconds, convert to minutes
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(minutes) + "\"";  //return the elapsed minutes wid double qoute at the end
    }
}