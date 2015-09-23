package com.ver_techs.qiff_android.fragments;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.ver_techs.qiff_android.custom_adapters.FixtureCustomAdapter;
import com.ver_techs.qiff_android.object_classes.FixtureItem;
import com.ver_techs.qiff_android.object_classes.FixtureItemLocal;
import com.ver_techs.qiff_android.R;
import com.ver_techs.qiff_android.custom_views.CustomProgressDialog;

import java.util.ArrayList;
import java.util.List;

//Fixture fragment that contains fixture view
public class FixtureFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ProgressDialog nDialog; //progress dialog to show while parse query is running in background
    ArrayList<FixtureItemLocal> fixtureItemArrayList; //list of fixture items, loaded from parse
    View v;
    private SwipeRefreshLayout swipeRefreshLayout;
    ListView fixtureList;
    FixtureCustomAdapter fixtureListAdapter;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_fixture, container, false);

        //Set and show the progress dialog
        nDialog = new CustomProgressDialog(getActivity());
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(false);
        nDialog.show();

        fixtureList = (ListView) v.findViewById(R.id.list_fixture); //find the listview to load fixture items
        fixtureListAdapter = new FixtureCustomAdapter(getActivity(), fixtureItemArrayList); //get a new istance of adapter for fixture view
        fixtureItemArrayList = new ArrayList<FixtureItemLocal>();
        swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_refresh_layout);

        setRetainInstance(true); //configure the fragment instance to be retained on configuration change

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Define the class we would like to query
        ParseQuery<FixtureItem> query = ParseQuery.getQuery(FixtureItem.class);
        query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);

        // Execute the find asynchronously
        query.findInBackground(new FindCallback<FixtureItem>() {

            public void done(List<FixtureItem> fixtureItemList, ParseException e) {

                if (e == null) {

                    // Access the array of results here
                    for (int i = 0; i < fixtureItemList.size(); i++) {
                        FixtureItemLocal fixtureItemLocal = new FixtureItemLocal(fixtureItemList.get(i).getTeamName1(), fixtureItemList.get(i).getTeamName2(),
                                fixtureItemList.get(i).getScoreTeam1(), fixtureItemList.get(i).getScoreTeam2(), fixtureItemList.get(i).getTimeDate());
                        fixtureItemArrayList.add(fixtureItemLocal);
                    }
                    //Log.i("aaki", "task doing " + Integer.toString(fixtureItemArrayList.size()));

                    nDialog.cancel();
                    fixtureList.setAdapter(fixtureListAdapter); //set the adapter to the listview

                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        Log.i("aaki", "refresh");
    }
}