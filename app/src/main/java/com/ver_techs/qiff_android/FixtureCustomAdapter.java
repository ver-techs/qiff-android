package com.ver_techs.qiff_android;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class FixtureCustomAdapter extends BaseAdapter {

    ArrayList<FixtureItem> fixtureItemArrayList;

    public FixtureCustomAdapter() {

        fixtureItemArrayList = new ArrayList<FixtureItem>();

//        // Parse query to get all FixtureItem objects from server
//
//        // Define the class we would like to query
//        ParseQuery<FixtureItem> query = ParseQuery.getQuery(FixtureItem.class);
//        // Execute the find asynchronously
//        query.findInBackground(new FindCallback<FixtureItem>() {
//
//            public void done(List<FixtureItem> fixtureItemList, ParseException e) {
//
//                if (e == null) {
//
//                    // Access the array of results here
//                    for (int i = 0; i < fixtureItemList.size() ; i++) {
//
//                        fixtureItemArrayList.add(fixtureItemList.get(i));
//
//                    }
//
//                } else {
//                    Log.d("item", "Error: " + e.getMessage());
//                }
//            }
//        });

        FixtureItem fixtureitem = new FixtureItem("team1", "team2", "3", "4", "junk");
        FixtureItem fixtureitem2 = new FixtureItem("team2", "team1", "3", "4", "junk");
        fixtureItemArrayList.add(fixtureitem);
        fixtureItemArrayList.add(fixtureitem2);

    }

    @Override
    public int getCount() {
        return fixtureItemArrayList.size();    // total number of elements in the list
    }

    @Override
    public Object getItem(int i) {
        return fixtureItemArrayList.get(i);    // single item in the list
    }

    @Override
    public long getItemId(int i) {
        return i;                   // index number
    }

    @Override
    public View getView(int index, View view, final ViewGroup parent) {

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.fixture_list_item, parent, false);
        }

        final FixtureItem fixtureItem = fixtureItemArrayList.get(index);

        TextView teamName1 = (TextView) view.findViewById(R.id.name_team1);
        teamName1.setText(fixtureItem.getTeamName1());

        TextView teamName2 = (TextView) view.findViewById(R.id.name_team2);
        teamName2.setText(fixtureItem.getTeamName2());

        TextView scoreTeam1 = (TextView) view.findViewById(R.id.score_team1);
        scoreTeam1.setText(fixtureItem.getScoreTeam1());

        TextView scoreTeam2 = (TextView) view.findViewById(R.id.score_team2);
        scoreTeam2.setText(fixtureItem.getScoreTeam2());

        TextView time = (TextView) view.findViewById(R.id.time);
        scoreTeam2.setText(fixtureItem.getTimeDate());
        //ImageView team1_logo = (ImageView) view.findViewById(R.id.image_team1);
        //team1_logo.setImageResource(R.drawable.team_1);

        //ImageView team2_logo = (ImageView) view.findViewById(R.id.image_team2);
        //team2_logo.setImageResource(R.drawable.team_2);

        return view;
    }
}