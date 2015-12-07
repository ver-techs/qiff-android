package com.ver_techs.qiff_android.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.parse.ConfigCallback;
import com.parse.FindCallback;
import com.parse.ParseConfig;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.ver_techs.qiff_android.activities.FacebookLoginActivity;
import com.ver_techs.qiff_android.custom_adapters.ForgoingFixtureCustomAdapter;
import com.ver_techs.qiff_android.custom_adapters.UpcomingFixtureCustomAdapter;
import com.ver_techs.qiff_android.object_classes.FixtureItem;
import com.ver_techs.qiff_android.object_classes.FixtureItemLocal;
import com.ver_techs.qiff_android.R;
import com.ver_techs.qiff_android.custom_views.CustomProgressDialog;

import java.util.ArrayList;
import java.util.List;

//Fixture fragment that contains fixture view
public class FixtureFragment extends Fragment {

    private ProgressDialog nDialog; //progress dialog to show while parse query is running in background
    ArrayList<FixtureItemLocal> fixtureItemArrayList; //list of fixture items, loaded from parse
    View v;
    private SwipeRefreshLayout swipeRefreshLayout;
    ListView fixtureList;
    ForgoingFixtureCustomAdapter forgoingFixtureListAdapter;
    UpcomingFixtureCustomAdapter upcomingFixtureCustomAdapter;
    int currentMatch;
    boolean isSeparator = false; //to check if current item should create a separator or not
    String dateTime;
    boolean upcomingSelected;
    FloatingActionButton floatingActionButton;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_fixture, container, false);

        //Set and show the progress dialog
        nDialog = new CustomProgressDialog(getActivity());
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(false);
        nDialog.show();

        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setEnabled(false); //disable the swipe refresh

        floatingActionButton = (FloatingActionButton) v.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), FacebookLoginActivity.class);
                startActivity(i);
            }
        });

        //set font to header in the fragment
        TextView match_schedule_text = (TextView) v.findViewById(R.id.match_schedule_text);
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), getActivity().getString(R.string.font_path));
        match_schedule_text.setTypeface(custom_font, Typeface.BOLD);

        Spinner spinner = (Spinner) v.findViewById(R.id.spinner_drop_down);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.drop_down_array, R.layout.spinner_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(spinnerAdapter);

        //set a listener to the spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if (position == 1) { //if Upcoming is selected, load the upcoming fixture items
                    upcomingSelected = true;
                    loadFixtureItems(upcomingSelected);
                }
                else{ //if Forgoing is selected, load the forgoing fixture items
                    upcomingSelected = false;
                    loadFixtureItems(upcomingSelected);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { //if no selection is done on the spinner drop, down, load the upcoming fixture items by default
                upcomingSelected = true;
                loadFixtureItems(upcomingSelected);
            }

        });

        return v;
    }

    public void loadFixtureItems(final boolean upcomingSelected){ //function to get fixture items from parse, and set adapter on it, in fixture fragment

        fixtureList = (ListView) v.findViewById(R.id.list_fixture); //find the listview to load fixture items
        fixtureItemArrayList = new ArrayList<FixtureItemLocal>();

        setRetainInstance(true); //configure the fragment instance to be retained on configuration change

        ParseConfig.getInBackground(new ConfigCallback() {

            @Override
            public void done(ParseConfig config, ParseException e) { //parse query successful

                // Define the class we would like to query
                ParseQuery<FixtureItem> query = ParseQuery.getQuery(FixtureItem.class);
                query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);

                if(upcomingSelected) //check which list items to fetch from parse - forgoing or upcmoming
                    query.whereNotEqualTo("matchCompleted", "FT");
                else
                    query.whereEqualTo("matchCompleted", "FT");

                if(upcomingSelected)
                    query.addAscendingOrder("createdAt"); //get results in ascending order of creation - for upcoming items
                else
                    query.addDescendingOrder("createdAt"); //get results in descending order of creation - for forgoing items

                // Execute the find asynchronously
                query.findInBackground(new FindCallback<FixtureItem>() {

                    public void done(List<FixtureItem> fixtureItemList, ParseException e) {

                        if (e == null) {

                            // Access the array of results here
                            for (int i = 0; i < fixtureItemList.size(); i++) {

                                isSeparator = false; //initialize the boolean variable to false

                                // If it is the first item then we need a separator header
                                if (i == 0) {
                                    isSeparator = true;
                                } else {
                                    // Get dates of current and previous fixture, check if date has changed, if so add a header to list
                                    String previousFixtureItemDate = fixtureItemList.get(i - 1).getTimeDate();
                                    String currentFixtureItemDate = fixtureItemList.get(i).getTimeDate();

                                    // Compare the dates for non-equality - ie compare first 6 characters of date
                                    if (previousFixtureItemDate.charAt(0) != currentFixtureItemDate.charAt(0) ||
                                            previousFixtureItemDate.charAt(1) != currentFixtureItemDate.charAt(1) ||
                                            previousFixtureItemDate.charAt(2) != currentFixtureItemDate.charAt(2) ||
                                            previousFixtureItemDate.charAt(3) != currentFixtureItemDate.charAt(3) ||
                                            previousFixtureItemDate.charAt(4) != currentFixtureItemDate.charAt(4) ||
                                            previousFixtureItemDate.charAt(5) != currentFixtureItemDate.charAt(5)) {
                                        isSeparator = true;
                                    }

                                }

                                /* If we need a separator, create a FixtureItem object and save it's date as the section
                                 header while passing everything else as null */
                                if (isSeparator) {
                                    String headerText;
                                    headerText = "  " + fixtureItemList.get(i).getTimeDate().substring(0, 6) + "  ";
                                    FixtureItemLocal fixtureItemLocal = new FixtureItemLocal("", "", "", "", headerText, "", true);
                                    fixtureItemArrayList.add(fixtureItemLocal);
                                }

                                //always create one fixture item to correspond to the object returned by query
                                FixtureItemLocal fixtureItemLocal = new FixtureItemLocal(fixtureItemList.get(i).getTeamName1(), fixtureItemList.get(i).getTeamName2(),
                                        fixtureItemList.get(i).getScoreTeam1(), fixtureItemList.get(i).getScoreTeam2(), fixtureItemList.get(i).getTimeDate(), fixtureItemList.get(i).getMatchStatus(),false);
                                fixtureItemArrayList.add(fixtureItemLocal);

                            }
                            //Log.i("aaki", "task doing " + Integer.toString(fixtureItemArrayList.size()));
                            //Log.i("aaki", String.valueOf(currentMatch));

                            nDialog.cancel(); //cancel the dialog once load has completed

                            if(upcomingSelected) { //set the appropriate adapter to the list based on dropdown selection

                                final Handler handler =new Handler();
                                final Runnable r = new Runnable() {
                                    public void run() {
                                        handler.postDelayed(this, 60000);

                                        upcomingFixtureCustomAdapter = new UpcomingFixtureCustomAdapter(getActivity(), fixtureItemArrayList); //get a new istance of adapter for fixture view
                                        fixtureList.setAdapter(upcomingFixtureCustomAdapter); //set the adapter to the listview
                                    }
                                };
                                handler.postDelayed(r, 0000);
                            }
                            else {
                                forgoingFixtureListAdapter = new ForgoingFixtureCustomAdapter(getActivity(), fixtureItemArrayList); //get a new istance of adapter for fixture view
                                fixtureList.setAdapter(forgoingFixtureListAdapter); //set the adapter to the listview
                            }

                        } else {
                            Log.d("item", "Error: " + e.getMessage());
                        }
                    }
                });

            }

        });
    }

}