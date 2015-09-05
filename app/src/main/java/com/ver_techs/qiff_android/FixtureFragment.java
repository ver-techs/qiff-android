package com.ver_techs.qiff_android;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Sony on 9/2/2015.
 */
public class FixtureFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_fixture, container, false);

        FixtureCustomAdapter fixtureListAdapter = new FixtureCustomAdapter();

        ListView fixtureList = (ListView) v.findViewById(R.id.fixtureListView);

        Log.i("aaki", "reached set adapter");
        fixtureList.setAdapter(fixtureListAdapter);

        return v;
    }
}