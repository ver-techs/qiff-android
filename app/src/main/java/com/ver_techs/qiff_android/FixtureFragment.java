package com.ver_techs.qiff_android;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sony on 9/2/2015.
 */
public class FixtureFragment extends Fragment {

    ArrayList<FixtureItemLocal> fixtureItemArrayList;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_fixture, container, false);

        // Parse query to get all FixtureItem objects from server

        fixtureItemArrayList = new ArrayList<FixtureItemLocal>();
        // Define the class we would like to query
        ParseQuery<FixtureItem> query = ParseQuery.getQuery(FixtureItem.class);
        // Execute the find asynchronously
        query.findInBackground(new FindCallback<FixtureItem>() {

            public void done(List<FixtureItem> fixtureItemList, ParseException e) {

                if (e == null) {

                    // Access the array of results here
                    for (int i = 0; i < fixtureItemList.size() ; i++) {
                        FixtureItemLocal fixtureItemLocal = new FixtureItemLocal(fixtureItemList.get(i).getTeamName1(), fixtureItemList.get(i).getTeamName2(),
                                fixtureItemList.get(i).getScoreTeam1(), fixtureItemList.get(i).getScoreTeam2(), fixtureItemList.get(i).getTimeDate());
                        fixtureItemArrayList.add(fixtureItemLocal);
                    }

                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });


        FixtureCustomAdapter fixtureListAdapter = new FixtureCustomAdapter(fixtureItemArrayList);
        ListView fixtureList = (ListView) v.findViewById(R.id.list);
        fixtureList.setAdapter(fixtureListAdapter);

        return v;
    }
}