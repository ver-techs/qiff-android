package com.ver_techs.qiff_android.fragments;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.ver_techs.qiff_android.object_classes.PointsTableItem;
import com.ver_techs.qiff_android.object_classes.PointsTableItemLocal;
import com.ver_techs.qiff_android.R;
import com.ver_techs.qiff_android.custom_adapters.PointsTableCustomAdapter;
import com.ver_techs.qiff_android.custom_views.CustomProgressDialog;

import java.util.ArrayList;
import java.util.List;

public class PointsTableFragment extends Fragment{

    ArrayList<PointsTableItemLocal> pointsItemArrayList1, pointsItemArrayList2, pointsItemArrayList3, pointsItemArrayList4; //list of points table items, loaded from parse
    private ProgressDialog nDialog;
    View v;
    int allFourQueriesDone=0; //variable to check if all 4 queries have been executed and done

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_points_table, container, false);

        //re-assign varibale to 0
        allFourQueriesDone=0;

        //Set and show the progress dialog
        nDialog = new CustomProgressDialog(getActivity());
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(false);
        nDialog.show();

        final TextView name_group_1 = (TextView) v.findViewById(R.id.name_group1);
        final TextView name_group_2 = (TextView) v.findViewById(R.id.name_group2);
        final TextView name_group_3 = (TextView) v.findViewById(R.id.name_group3);
        final TextView name_group_4 = (TextView) v.findViewById(R.id.name_group4);

        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.font_path));

        name_group_1.setTypeface(custom_font);
        name_group_2.setTypeface(custom_font);
        name_group_3.setTypeface(custom_font);
        name_group_4.setTypeface(custom_font);

        pointsItemArrayList1 = new ArrayList<PointsTableItemLocal>();

        // Define the class we would like to query
        ParseQuery<PointsTableItem> query1 = ParseQuery.getQuery(PointsTableItem.class);
        query1.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);

        query1.addDescendingOrder("total");
        query1.whereEqualTo("groupNo", "I");
        // Execute the find asynchronously
        query1.findInBackground(new FindCallback<PointsTableItem>() {

            public void done(List<PointsTableItem> pointsTableItemsList, ParseException e) {

                if (e == null) {

                    // Access the array of results here
                    for (int i = 0; i < pointsTableItemsList.size(); i++) {
                        PointsTableItemLocal pointsTableItemLocal = new PointsTableItemLocal(
                                pointsTableItemsList.get(i).getTeamName(), pointsTableItemsList.get(i).getTotal(),
                                pointsTableItemsList.get(i).getWins(), pointsTableItemsList.get(i).getDraws(),
                                pointsTableItemsList.get(i).getLosses(), pointsTableItemsList.get(i).getGoalDifference());
                        pointsItemArrayList1.add(pointsTableItemLocal);
                    }
                    //Log.i("aaki", "task doing  - " + Integer.toString(pointsItemArrayList1.size()));

                    allFourQueriesDone++;
                    if(allFourQueriesDone == 4) nDialog.cancel();
                    PointsTableCustomAdapter pointsTableListAdapter1 = new PointsTableCustomAdapter(getActivity(), pointsItemArrayList1); //get a new istance of adapter for fixture view
                    ListView pointsTableList1 = (ListView) v.findViewById(R.id.list_teams_group1); //find the listview to load fixture items
                    setListViewHeightBasedOnChildren(pointsTableList1);
                    pointsTableList1.setFocusable(false);
                    pointsTableList1.setAdapter(pointsTableListAdapter1); //set the adapter to the listview

                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });


        pointsItemArrayList2 = new ArrayList<PointsTableItemLocal>();
        // Define the class we would like to query
        ParseQuery<PointsTableItem> query2 = ParseQuery.getQuery(PointsTableItem.class);
        query2.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);

        query2.addDescendingOrder("total");
        query2.whereEqualTo("groupNo", "II");
        // Execute the find asynchronously
        query2.findInBackground(new FindCallback<PointsTableItem>() {

            public void done(List<PointsTableItem> pointsTableItemsList, ParseException e) {

                if (e == null) {

                    // Access the array of results here
                    for (int i = 0; i < pointsTableItemsList.size(); i++) {
                        PointsTableItemLocal pointsTableItemLocal = new PointsTableItemLocal(
                                pointsTableItemsList.get(i).getTeamName(), pointsTableItemsList.get(i).getTotal(),
                                pointsTableItemsList.get(i).getWins(), pointsTableItemsList.get(i).getDraws(),
                                pointsTableItemsList.get(i).getLosses(), pointsTableItemsList.get(i).getGoalDifference());
                        pointsItemArrayList2.add(pointsTableItemLocal);
                    }
                    //Log.i("aaki", "task doing  - " + Integer.toString(pointsItemArrayList2.size()));

                    allFourQueriesDone++;
                    if(allFourQueriesDone == 4) nDialog.cancel();
                    PointsTableCustomAdapter pointsTableListAdapter2 = new PointsTableCustomAdapter(getActivity(), pointsItemArrayList2); //get a new istance of adapter for fixture view
                    ListView pointsTableList2 = (ListView) v.findViewById(R.id.list_teams_group2); //find the listview to load fixture items
                    setListViewHeightBasedOnChildren(pointsTableList2);
                    pointsTableList2.setFocusable(false);
                    pointsTableList2.setAdapter(pointsTableListAdapter2); //set the adapter to the listview

                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });


        pointsItemArrayList3 = new ArrayList<PointsTableItemLocal>();

        // Define the class we would like to query
        ParseQuery<PointsTableItem> query3 = ParseQuery.getQuery(PointsTableItem.class);
        query3.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);

        query3.addDescendingOrder("total");
        query3.whereEqualTo("groupNo", "III");
        // Execute the find asynchronously
        query3.findInBackground(new FindCallback<PointsTableItem>() {

            public void done(List<PointsTableItem> pointsTableItemsList, ParseException e) {

                if (e == null) {

                    // Access the array of results here
                    for (int i = 0; i < pointsTableItemsList.size(); i++) {
                        PointsTableItemLocal pointsTableItemLocal = new PointsTableItemLocal(
                                pointsTableItemsList.get(i).getTeamName(), pointsTableItemsList.get(i).getTotal(),
                                pointsTableItemsList.get(i).getWins(), pointsTableItemsList.get(i).getDraws(),
                                pointsTableItemsList.get(i).getLosses(), pointsTableItemsList.get(i).getGoalDifference());
                        pointsItemArrayList3.add(pointsTableItemLocal);
                    }
                    //Log.i("aaki", "task doing  - " + Integer.toString(pointsItemArrayList3.size()));

                    allFourQueriesDone++;
                    if(allFourQueriesDone == 4) nDialog.cancel();
                    PointsTableCustomAdapter pointsTableListAdapter3 = new PointsTableCustomAdapter(getActivity(), pointsItemArrayList3); //get a new istance of adapter for fixture view
                    ListView pointsTableList3 = (ListView) v.findViewById(R.id.list_teams_group3); //find the listview to load fixture items
                    setListViewHeightBasedOnChildren(pointsTableList3);
                    pointsTableList3.setFocusable(false);
                    pointsTableList3.setAdapter(pointsTableListAdapter3); //set the adapter to the listview

                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });


        pointsItemArrayList4 = new ArrayList<PointsTableItemLocal>();

        // Define the class we would like to query
        ParseQuery<PointsTableItem> query4 = ParseQuery.getQuery(PointsTableItem.class);
        query4.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);

        query4.addDescendingOrder("total");
        query4.whereEqualTo("groupNo", "I");
        // Execute the find asynchronously
        query4.findInBackground(new FindCallback<PointsTableItem>() {

            public void done(List<PointsTableItem> pointsTableItemsList, ParseException e) {

                if (e == null) {

                    // Access the array of results here
                    for (int i = 0; i < pointsTableItemsList.size(); i++) {
                        PointsTableItemLocal pointsTableItemLocal = new PointsTableItemLocal(
                                pointsTableItemsList.get(i).getTeamName(), pointsTableItemsList.get(i).getTotal(),
                                pointsTableItemsList.get(i).getWins(), pointsTableItemsList.get(i).getDraws(),
                                pointsTableItemsList.get(i).getLosses(), pointsTableItemsList.get(i).getGoalDifference());
                        pointsItemArrayList4.add(pointsTableItemLocal);
                    }
                    //Log.i("aaki", "task doing  - " + Integer.toString(pointsItemArrayList4.size()));

                    allFourQueriesDone++;
                    if(allFourQueriesDone == 4) nDialog.cancel();
                    PointsTableCustomAdapter pointsTableListAdapter4 = new PointsTableCustomAdapter(getActivity(), pointsItemArrayList4); //get a new istance of adapter for fixture view
                    ListView pointsTableList4 = (ListView) v.findViewById(R.id.list_teams_group4); //find the listview to load fixture items
                    setListViewHeightBasedOnChildren(pointsTableList4);
                    pointsTableList4.setFocusable(false);
                    pointsTableList4.setAdapter(pointsTableListAdapter4); //set the adapter to the listview

                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });

        return v;

    }

    public static void setListViewHeightBasedOnChildren(ListView listView)
    {
        ListAdapter listAdapter = listView.getAdapter();
        if(listAdapter == null) return;
        if(listAdapter.getCount() <= 1) return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        int totalHeight = 0;
        View view = null;
        for(int i = 0; i < listAdapter.getCount(); i++)
        {
            view = listAdapter.getView(i, view, listView);
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

}