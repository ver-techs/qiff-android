package com.ver_techs.qiff_android;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

// Adapter for Fixture tab
public class FixtureCustomAdapter extends BaseAdapter {

    ArrayList<FixtureItemLocal> fixtureItemArrayList; //Local variable list that stores all fixture items

    public FixtureCustomAdapter(ArrayList<FixtureItemLocal> fixtureItemArray) {
        this.fixtureItemArrayList=fixtureItemArray;
        Log.i("aaki",Integer.toString(fixtureItemArrayList.size()));
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
            LayoutInflater inflater = LayoutInflater.from(parent.getContext()); //inflate a list item layout
            view = inflater.inflate(R.layout.fixture_list_item, parent, false);
        }

        final FixtureItemLocal fixtureItemLocal = fixtureItemArrayList.get(index); //get the fixture item from the list to populate into the layout

        //set the textviews and imageviews
        TextView teamName1 = (TextView) view.findViewById(R.id.name_team1_fixture);
        teamName1.setText(fixtureItemLocal.getTeamName1());

        TextView teamName2 = (TextView) view.findViewById(R.id.name_team2_fixture);
        teamName2.setText(fixtureItemLocal.getTeamName2());

        TextView scoreTeam1 = (TextView) view.findViewById(R.id.score_team1_fixture);
        scoreTeam1.setText(fixtureItemLocal.getScoreTeam1());

        TextView scoreTeam2 = (TextView) view.findViewById(R.id.score_team2_fixture);
        scoreTeam2.setText(fixtureItemLocal.getScoreTeam2());

        TextView time = (TextView) view.findViewById(R.id.time_fixture);
        time.setText(fixtureItemLocal.getTimeDate());

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
}