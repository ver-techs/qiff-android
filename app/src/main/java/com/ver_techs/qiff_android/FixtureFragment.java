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

/**
 * Created by Sony on 9/2/2015.
 */
public class FixtureFragment extends Fragment {

    ArrayList<FixtureItemLocal> fixtureItemArrayList;
    private WeakReference<MyAsyncTask> asyncTaskWeakRef;
    private ProgressDialog pDialog;
    View v;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_fixture, container, false);

        setRetainInstance(true); //configure the fragment instance to be retained on configuration change
        startNewAsyncTask();

        return v;
    }

    private void startNewAsyncTask() {
        MyAsyncTask asyncTask = new MyAsyncTask(this);
        this.asyncTaskWeakRef = new WeakReference<MyAsyncTask >(asyncTask );
        asyncTask.execute();
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<FixtureFragment> fragmentWeakRef;

        private MyAsyncTask (FixtureFragment fragment) {
            this.fragmentWeakRef = new WeakReference<FixtureFragment>(fragment);
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
                        Log.i("aaki", "task doing " + Integer.toString(fixtureItemArrayList.size()));
                    } else {
                        Log.d("item", "Error: " + e.getMessage());
                    }
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void response) {
            super.onPostExecute(response);
            if (this.fragmentWeakRef.get() != null) {
                Log.i("aaki", "task completed " + Integer.toString(fixtureItemArrayList.size()));

                FixtureCustomAdapter fixtureListAdapter = new FixtureCustomAdapter(fixtureItemArrayList);
                ListView fixtureList = (ListView) v.findViewById(R.id.list);
                fixtureList.setAdapter(fixtureListAdapter);

            }
        }
    }

}