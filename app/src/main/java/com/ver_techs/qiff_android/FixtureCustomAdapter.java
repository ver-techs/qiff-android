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

public class FixtureCustomAdapter extends BaseAdapter {

    ArrayList<FixtureItemLocal> fixtureItemArrayList;

    public FixtureCustomAdapter(ArrayList<FixtureItemLocal> fixtureItemArray) {
        this.fixtureItemArrayList=fixtureItemArray;
    }

    @Override
    public int getCount() {
        return fixtureItemArrayList.size();
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

        final FixtureItemLocal fixtureItemLocal = fixtureItemArrayList.get(index);

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

        ImageView team1_logo = (ImageView) view.findViewById(R.id.image_team1);
        team1_logo.setImageResource(R.drawable.team_2);

        ImageView team2_logo = (ImageView) view.findViewById(R.id.image_team2);
        team2_logo.setImageResource(R.drawable.team_1);

        return view;
    }
}