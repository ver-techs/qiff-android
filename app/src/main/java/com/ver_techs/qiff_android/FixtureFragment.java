package com.ver_techs.qiff_android;

import android.app.ProgressDialog;
import android.os.AsyncTask;
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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

//Fixture fragment that contains fixture view
public class FixtureFragment extends Fragment {

    ArrayList<FixtureItemLocal> fixtureItemArrayList; //list of fixture items, loaded from parse
    private WeakReference<MyAsyncTask> asyncTaskWeakRef; //weak reference to asynctask
    View v;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_fixture, container, false);

        setRetainInstance(true); //configure the fragment instance to be retained on configuration change
        startNewAsyncTask(); //call async task to get fixture items from parse

        return v;
    }

    private void startNewAsyncTask() {
        MyAsyncTask asyncTask = new MyAsyncTask(this); //create a new instance of asynctask
        this.asyncTaskWeakRef = new WeakReference<MyAsyncTask >(asyncTask ); //set the seak reference
        asyncTask.execute(); //execute asynctask
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<FixtureFragment> fragmentWeakRef;

        private MyAsyncTask (FixtureFragment fragment) {
            this.fragmentWeakRef = new WeakReference<FixtureFragment>(fragment); //set the weak reference
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            // Parse query to get all FixtureItem objects from server

            fixtureItemArrayList = new ArrayList<FixtureItemLocal>();
            // Define the class we would like to query
//            ParseQuery<FixtureItem> query = ParseQuery.getQuery(FixtureItem.class);
//            query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
//
//            // Execute the find asynchronously
//            query.findInBackground(new FindCallback<FixtureItem>() {
//
//                public void done(List<FixtureItem> fixtureItemList, ParseException e) {
//
//                    if (e == null) {
//
//                        // Access the array of results here
//                        for (int i = 0; i < fixtureItemList.size(); i++) {
//                            FixtureItemLocal fixtureItemLocal = new FixtureItemLocal(fixtureItemList.get(i).getTeamName1(), fixtureItemList.get(i).getTeamName2(),
//                                    fixtureItemList.get(i).getScoreTeam1(), fixtureItemList.get(i).getScoreTeam2(), fixtureItemList.get(i).getTimeDate());
//                            fixtureItemArrayList.add(fixtureItemLocal);
//                        }
//                        Log.i("aaki", "task doing " + Integer.toString(fixtureItemArrayList.size()));
//                    } else {
//                        Log.d("item", "Error: " + e.getMessage());
//                    }
//                }
//            });

            FixtureItemLocal fixtureItemLocal1 = new FixtureItemLocal("MAK KKD", "KMCC WND", "-", "-", "17 Sept 8:30 pm");
            fixtureItemArrayList.add(fixtureItemLocal1);
            FixtureItemLocal fixtureItemLocal2 = new FixtureItemLocal("NADHAM TCR", "KMCC KKD", "-", "-", "17 Sept 10:00 pm");
            fixtureItemArrayList.add(fixtureItemLocal2);
            FixtureItemLocal fixtureItemLocal3 = new FixtureItemLocal("CFQ KKD", "KMCC KNR", "-", "-", "18 Sept 5:00 pm");
            fixtureItemArrayList.add(fixtureItemLocal3);
            FixtureItemLocal fixtureItemLocal4 = new FixtureItemLocal("SKIA TVM", "CFQ PTNMTA", "-", "-", "18 Sept 7:55 pm");
            fixtureItemArrayList.add(fixtureItemLocal4);
            FixtureItemLocal fixtureItemLocal5 = new FixtureItemLocal("EDSO EKM", "MAMWAQ MLP", "-", "-", "24 Sept 7:00 pm");
            fixtureItemArrayList.add(fixtureItemLocal5);
            FixtureItemLocal fixtureItemLocal6 = new FixtureItemLocal("KMCC MLP", "KMCC PKD", "-", "-", "24 Sept 8:30 pm");
            fixtureItemArrayList.add(fixtureItemLocal6);
            FixtureItemLocal fixtureItemLocal7 = new FixtureItemLocal("TYC TCR", "KMCC TCR", "-", "-", "25 Sept 5:50 pm");
            fixtureItemArrayList.add(fixtureItemLocal7);
            FixtureItemLocal fixtureItemLocal8 = new FixtureItemLocal("KMCC KSGD", "KPAQ KKD", "-", "-", "25 Sept 7:00 pm");
            fixtureItemArrayList.add(fixtureItemLocal8);
            FixtureItemLocal fixtureItemLocal9 = new FixtureItemLocal("MAK KKD", "CFQ KKD", "-", "-", "25 Sept 8:30 pm");
            fixtureItemArrayList.add(fixtureItemLocal9);
            FixtureItemLocal fixtureItemLocal10 = new FixtureItemLocal("CFQ PTNMTA", "KPAQ KKD", "-", "-", "1 Oct 7:00 pm");
            fixtureItemArrayList.add(fixtureItemLocal10);
            FixtureItemLocal fixtureItemLocal11 = new FixtureItemLocal("KMCC MLP", "TYC TCR", "-", "-", "1 Oct 8:30 pm");
            fixtureItemArrayList.add(fixtureItemLocal11);
            FixtureItemLocal fixtureItemLocal12 = new FixtureItemLocal("SKIA TVM", "KMCC KSGD", "-", "-", "8 Oct 7:00 pm");
            fixtureItemArrayList.add(fixtureItemLocal12);
            FixtureItemLocal fixtureItemLocal13 = new FixtureItemLocal("KMCC WND", "KMCC KNR", "-", "-", "8 Oct 8:30 pm");
            fixtureItemArrayList.add(fixtureItemLocal13);
            FixtureItemLocal fixtureItemLocal14 = new FixtureItemLocal("KMCC KKD", "MAMWAQ MLP", "-", "-", "9 Oct 5:30 pm");
            fixtureItemArrayList.add(fixtureItemLocal14);
            FixtureItemLocal fixtureItemLocal15 = new FixtureItemLocal("KMCC PKD", "KMCC TCR", "-", "-", "9 Oct 7:00 pm");
            fixtureItemArrayList.add(fixtureItemLocal15);
            FixtureItemLocal fixtureItemLocal16 = new FixtureItemLocal("NADHAM TCR", "EDSO EKM", "-", "-", "9 Oct 8:30 pm");
            fixtureItemArrayList.add(fixtureItemLocal16);
            FixtureItemLocal fixtureItemLocal17 = new FixtureItemLocal("MAK KKD", "KMCC KNR", "-", "-", "15 Oct 7:00 pm");
            fixtureItemArrayList.add(fixtureItemLocal17);
            FixtureItemLocal fixtureItemLocal18 = new FixtureItemLocal("CFQ PTNMTA", "KMCC KSGD", "-", "-", "15 Oct 8:30 pm");
            fixtureItemArrayList.add(fixtureItemLocal18);
            FixtureItemLocal fixtureItemLocal19 = new FixtureItemLocal("KMCC WND", "CFQ KKD", "-", "-", "16 Oct 7:30 pm");
            fixtureItemArrayList.add(fixtureItemLocal19);
            FixtureItemLocal fixtureItemLocal20 = new FixtureItemLocal("SKIA TVM", "KPAQ KKD", "-", "-", "16 Oct 9:00 pm");
            fixtureItemArrayList.add(fixtureItemLocal20);
            FixtureItemLocal fixtureItemLocal21 = new FixtureItemLocal("KMCC PKD", "TYC TCR", "-", "-", "22 Oct 7:00 pm");
            fixtureItemArrayList.add(fixtureItemLocal21);
            FixtureItemLocal fixtureItemLocal22 = new FixtureItemLocal("KMCC KKD", "EDSO EKM", "-", "-", "22 Oct 8:30 pm");
            fixtureItemArrayList.add(fixtureItemLocal22);
            FixtureItemLocal fixtureItemLocal23 = new FixtureItemLocal("KMCC MLP", "KMCC TCR", "-", "-", "23 Oct 7:30 pm");
            fixtureItemArrayList.add(fixtureItemLocal23);
            FixtureItemLocal fixtureItemLocal24 = new FixtureItemLocal("NADHAM TCR", "MAMWAQ MLP", "-", "-", "23 Oct 9:00 pm");
            fixtureItemArrayList.add(fixtureItemLocal24);

            return null;
        }

        @Override
        protected void onPostExecute(Void response) {
            super.onPostExecute(response);
            if (this.fragmentWeakRef.get() != null) {
                Log.i("aaki", "task completed " + Integer.toString(fixtureItemArrayList.size()));

                FixtureCustomAdapter fixtureListAdapter = new FixtureCustomAdapter(getActivity(), fixtureItemArrayList); //get a new istance of adapter for fixture view
                ListView fixtureList = (ListView) v.findViewById(R.id.list); //find the listview to load fixture items
                fixtureList.setAdapter(fixtureListAdapter); //set the adapter to the listview

            }
        }
    }

}