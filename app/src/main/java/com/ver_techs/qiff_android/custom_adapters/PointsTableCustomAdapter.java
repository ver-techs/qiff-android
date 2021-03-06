package com.ver_techs.qiff_android.custom_adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ver_techs.qiff_android.object_classes.PointsTableItemLocal;
import com.ver_techs.qiff_android.R;

import java.util.ArrayList;

public class PointsTableCustomAdapter extends BaseAdapter {

    private Context context;
    ArrayList<PointsTableItemLocal> pointsTableArrayList; //Local variable list that stores all points table items

    public PointsTableCustomAdapter(Context context, ArrayList<PointsTableItemLocal> pointsTableItemArray) {
        this.context = context;
        this.pointsTableArrayList = pointsTableItemArray;
        //Log.i("aaki", "adapter - " + Integer.toString(pointsTableItemArray.size()));
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
        name_team_points_table.setTypeface(custom_font, Typeface.BOLD);

        TextView total_points_team_points_table = (TextView) view.findViewById(R.id.total_points_team_points_table);
        total_points_team_points_table.setText(pointsTableItemLocal.getTotal());
        total_points_team_points_table.setTypeface(custom_font, Typeface.BOLD);

        TextView wins_points_table = (TextView) view.findViewById(R.id.wins_points_table);
        wins_points_table.setText("WIN : " + pointsTableItemLocal.getWins());
        wins_points_table.setTypeface(custom_font);
        wins_points_table.setTextColor(context.getResources().getColor(R.color.color_tertiary));

        TextView draws_points_table = (TextView) view.findViewById(R.id.draws_points_table);
        draws_points_table.setText("DRAW : " + pointsTableItemLocal.getDraws());
        draws_points_table.setTypeface(custom_font);
        draws_points_table.setTextColor(context.getResources().getColor(R.color.color_tertiary));

        TextView losses_points_table = (TextView) view.findViewById(R.id.losses_points_table);
        losses_points_table.setText("LOSS : " + pointsTableItemLocal.getLosses());
        losses_points_table.setTypeface(custom_font);
        losses_points_table.setTextColor(context.getResources().getColor(R.color.color_tertiary));

        TextView played_points_table = (TextView) view.findViewById(R.id.goal_difference_points_table);
        played_points_table.setText("GOAL DIFF : " + pointsTableItemLocal.getGoalDifference());
        played_points_table.setTypeface(custom_font);
        played_points_table.setTextColor(context.getResources().getColor(R.color.color_tertiary));

        ImageView image_team_points_table = (ImageView) view.findViewById(R.id.image_team_points_table);
        image_team_points_table.setImageResource(findTeamLogo(pointsTableItemLocal.getTeamName()));

        //if the teams are the top two in the respective groups, set background of their total points to green, else to red
        LinearLayout total_points_layout = (LinearLayout) view.findViewById(R.id.total_points_layout);
        if(pointsTableItemLocal.isTopTwoInGroup())
            total_points_layout.setBackgroundColor(context.getResources().getColor(R.color.color_primary));
        else
            total_points_layout.setBackgroundColor(context.getResources().getColor(R.color.color_complementary_1));

        return view;
    }

    public int findTeamLogo(String teamName){
        // code to find corresponding image of respective teams

        int resource_id = 0;
        switch (teamName){
            case "NADHAM TCR" : resource_id = R.drawable.kmcc_wnd; break;
            case "KMCC MLP" : resource_id = R.drawable.kmcc_mlp; break;
            case "KMCC KKD" : resource_id = R.drawable.kmcc_kkd; break;
            case "KMCC PKD" : resource_id = R.drawable.kmcc_pkd; break;
            case "SKIA TVM" : resource_id = R.drawable.skia_tvm; break;
            case "KMCC WND" : resource_id = R.drawable.kmcc_wnd; break;
            case "CFQ PTNMTA" : resource_id = R.drawable.cfq_ptnmta; break;
            case "MAK KKD" : resource_id = R.drawable.mak_kkd; break;
            case "EDSO EKM" : resource_id = R.drawable.edso_ekm; break;
            case "MAMWAQ MLP" : resource_id = R.drawable.mamwaq_mlp; break;
            case "KMCC KNR" : resource_id = R.drawable.kmcc_knr; break;
            case "CFQ KKD" : resource_id =  R.drawable.cfq_kkd; break;
            case "TYC TCR" : resource_id = R.drawable.tyc_tcr; break;
            case "KMCC TCR" : resource_id = R.drawable.kmcc_tcr; break;
            case "KMCC KSGD" : resource_id = R.drawable.kmcc_ksgd; break;
            case "KPAQ KKD" : resource_id = R.drawable.kpaq_kkd; break;
        }
        return resource_id;
    }
}