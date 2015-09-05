package com.ver_techs.qiff_android;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sony on 9/2/2015.
 */
public class FixtureCustomAdapter extends BaseAdapter {

    List<FixtureItem> fixtureItemsList = getDataForListView();
    private Context context;

    public void FixtureCustomeAdapter(Context context){
        this.context=context;
    }

    @Override
    public int getCount() {

        // TODO Auto-generated method stub
        return fixtureItemsList.size();

    }

    @Override
    public FixtureItem getItem(int arg0) {

        // TODO Auto-generated method stub
        return fixtureItemsList.get(arg0);

    }

    @Override
    public long getItemId(int arg0) {

        // TODO Auto-generated method stub
        return arg0;

    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {

        if(arg1==null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            arg1 = inflater.inflate(R.layout.fixture_list_item, arg2,false);
        }

        TextView name_team1 = (TextView)arg1.findViewById(R.id.name_team1);
        TextView name_team2 = (TextView)arg1.findViewById(R.id.name_team2);

        FixtureItem fixtureItem = fixtureItemsList.get(arg0);

        name_team1.setText(fixtureItem.getTeamName1());
        name_team2.setText(fixtureItem.getTeamName2());

        return arg1;
    }

    public List<FixtureItem> getDataForListView()
    {
        final List<FixtureItem> fixtureItemArrayList = new ArrayList<FixtureItem>();

        // Parse query to get all FixtureItem objects from server

        ParseObject.registerSubclass(FixtureItem.class);
        // Define the class we would like to query
        ParseQuery<FixtureItem> query = ParseQuery.getQuery(FixtureItem.class);
        // Execute the find asynchronously
        query.findInBackground(new FindCallback<FixtureItem>() {

            public void done(List<FixtureItem> fixtureItemList, ParseException e) {

                if (e == null) {

                    // Access the array of results here
                    for(int i=0; i<fixtureItemList.size(); i++){

                        fixtureItemArrayList.add(fixtureItemList.get(i));
                        Log.i("fixture", fixtureItemList.get(i).getTeamName1());

                    }

                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });

        return fixtureItemArrayList;

    }

}
