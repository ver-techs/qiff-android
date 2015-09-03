package com.ver_techs.qiff_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
        List<FixtureItem> fixtureItemArrayList = new ArrayList<FixtureItem>();

        for(int i=0;i<10;i++)
        {

            FixtureItem fixtureItem = new FixtureItem();
            fixtureItem.teamName1 = fixtureItem.getTeamName1();
            fixtureItem.teamName2 = fixtureItem.getTeamName2();
            fixtureItemArrayList.add(fixtureItem);
        }

        return fixtureItemArrayList;

    }

}
