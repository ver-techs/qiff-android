package com.ver_techs.qiff_android;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PointsTableCustomAdapter extends BaseAdapter {

    private Context context;
    ArrayList<PointsTableItemLocal> pointsTableArrayList; //Local variable list that stores all points table items

    public PointsTableCustomAdapter(Context context, ArrayList<PointsTableItemLocal> pointsTableItemArray) {
        this.context = context;
        this.pointsTableArrayList = pointsTableItemArray;
        Log.i("aaki", "adapter - " + Integer.toString(pointsTableItemArray.size()));
    }

    @Override
    public int getCount() {
        return pointsTableArrayList.size(); //size of the list
    }

    @Override
    public Object getItem(int i) {
        return pointsTableArrayList.get(i);    // single item in the list
    }

    @Override
    public long getItemId(int i) {
        return i; // index number
    }

    @Override
    public View getView(int index, View view, final ViewGroup parent) {

        //Log.i("aaki", "getView called");
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext()); //inflate a list item
            view = inflater.inflate(R.layout.points_table_list_item, parent, false);
        }

        final PointsTableItemLocal pointsTableItemLocal = pointsTableArrayList.get(index); //get the points table item from the list to populate into the progress_spin

        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), context.getString(R.string.font_path));

        //set the textviews and imageviews
        TextView name_team_points_table = (TextView) view.findViewById(R.id.name_team_points_table);
        name_team_points_table.setText(pointsTableItemLocal.getTeamName());
        name_team_points_table.setTypeface(custom_font);

        TextView total_points_team_points_table = (TextView) view.findViewById(R.id.total_points_team_points_table);
        total_points_team_points_table.setText(pointsTableItemLocal.getTotal());
        total_points_team_points_table.setTypeface(custom_font);

        TextView wins_points_table = (TextView) view.findViewById(R.id.wins_points_table);
        wins_points_table.setText("W : " + pointsTableItemLocal.getWins());
        wins_points_table.setTypeface(custom_font);

        TextView draws_points_table = (TextView) view.findViewById(R.id.draws_points_table);
        draws_points_table.setText("D : " + pointsTableItemLocal.getDraws());
        draws_points_table.setTypeface(custom_font);

        TextView losses_points_table = (TextView) view.findViewById(R.id.losses_points_table);
        losses_points_table.setText("L : " + pointsTableItemLocal.getLosses());
        losses_points_table.setTypeface(custom_font);

        TextView played_points_table = (TextView) view.findViewById(R.id.goal_difference_points_table);
        played_points_table.setText("GD : " + pointsTableItemLocal.getGoalDifference());
        played_points_table.setTypeface(custom_font);

        ImageView image_team_points_table = (ImageView) view.findViewById(R.id.image_team_points_table);
        image_team_points_table.setImageResource(findTeamLogo(pointsTableItemLocal.getTeamName()));

        return view;
    }

    public int findTeamLogo(String teamName){
        // code to find corresponding image of respective teams

        int resource_id = 0;
        switch (teamName){
            case "NADHAM TCR" : resource_id = R.drawable.team_1; break;
            case "KMCC MLP" : resource_id = R.drawable.team_2; break;
            case "KMCC KKD" : resource_id = R.drawable.team_3; break;
            case "KMCC PKD" : resource_id = R.drawable.team_4; break;
            case "SKIA TVM" : resource_id = R.drawable.team_5; break;
            case "KMCC WND" : resource_id = R.drawable.team_6; break;
            case "CFQ PTNMTA" : resource_id = R.drawable.team_1; break;
            case "MAK KKD" : resource_id = R.drawable.team_2; break;
            case "EDSO EKM" : resource_id = R.drawable.team_3; break;
            case "MAMWAQ MLP" : resource_id = R.drawable.team_4; break;
            case "KMCC KNR" : resource_id = R.drawable.team_5; break;
            case "CFQ KKD" : resource_id =  R.drawable.team_6; break;
            case "TYC TCR" : resource_id = R.drawable.team_1; break;
            case "KMCC TCR" : resource_id = R.drawable.team_2; break;
            case "KMCC KSGD" : resource_id = R.drawable.team_3; break;
            case "KPAQ KKD" : resource_id = R.drawable.team_4; break;
        }
        return resource_id;
    }
}