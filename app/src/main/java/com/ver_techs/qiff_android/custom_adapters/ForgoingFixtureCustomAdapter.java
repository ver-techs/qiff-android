package com.ver_techs.qiff_android.custom_adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ver_techs.qiff_android.R;
import com.ver_techs.qiff_android.object_classes.FixtureItemLocal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.os.Handler;

// Adapter for Fixture tab
public class ForgoingFixtureCustomAdapter extends BaseAdapter {

    private Context context;
    ArrayList<FixtureItemLocal> fixtureItemArrayList; //Local variable list that stores all fixture items
    TextView time; //texview for dateTime in fixture
    Handler h; //handler to update dateTime field in fiture every minute
    int delay = 1000; //milliseconds to update dateTime field
    private static final int ITEM_TYPE_HEADER = 0;  // View Type for Headers
    private static final int ITEM_TYPE_REGULAR = 1; // View Type for Regular rows

    public ForgoingFixtureCustomAdapter(Context context, ArrayList<FixtureItemLocal> fixtureItemArray) {
        this.context = context;
        this.fixtureItemArrayList = fixtureItemArray;
        //Log.i("aaki",Integer.toString(fixtureItemArrayList.size()));
    }

    @Override
    public int getCount() {
        return fixtureItemArrayList.size(); //size of the list
    }

    @Override
    public Object getItem(int i) {
        return fixtureItemArrayList.get(i);    // single item in the list
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        boolean isSection = fixtureItemArrayList.get(position).isAHeader();

        if (isSection) {
            return ITEM_TYPE_HEADER;
        }
        else {
            return ITEM_TYPE_REGULAR;
        }
    }

    @Override
    public long getItemId(int i) {
        return i; // index number
    }

    @Override
    public View getView(int index, View convertView, final ViewGroup parent) {

        View view;
        int itemViewType = getItemViewType(index);

        //Log.i("aaki", "getView called");
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext()); //inflate a list item progress_spin

            if (itemViewType == ITEM_TYPE_HEADER) {
                // If its a section
                view = inflater.inflate(R.layout.layout_date_section_header, parent, false);
            }
            else {
                // Regular row
                view = inflater.inflate(R.layout.fixture_list_item, parent, false);
            }
        }
        else
        {
            view = convertView;
        }

        if(itemViewType == ITEM_TYPE_HEADER) {

            final FixtureItemLocal fixtureItemLocal = fixtureItemArrayList.get(index);

            Typeface custom_font = Typeface.createFromAsset(context.getAssets(), context.getString(R.string.font_path));

            TextView separatorTextView = (TextView) view.findViewById(R.id.header_text_view);
            separatorTextView.setText(fixtureItemLocal.getTimeDate());
            separatorTextView.setTypeface(custom_font);

        }
        else {

            final FixtureItemLocal fixtureItemLocal = fixtureItemArrayList.get(index); //get the fixture item from the list to populate into the progress_spin

            Typeface custom_font = Typeface.createFromAsset(context.getAssets(), context.getString(R.string.font_path));

            //set the textviews and imageviews
            TextView teamName1 = (TextView) view.findViewById(R.id.name_team1_fixture);
            teamName1.setText(fixtureItemLocal.getTeamName1());
            teamName1.setTypeface(custom_font);

            TextView teamName2 = (TextView) view.findViewById(R.id.name_team2_fixture);
            teamName2.setText(fixtureItemLocal.getTeamName2());
            teamName2.setTypeface(custom_font);

            TextView scoreTeam1 = (TextView) view.findViewById(R.id.score_team1_fixture);
            scoreTeam1.setText(fixtureItemLocal.getScoreTeam1());
            scoreTeam1.setTypeface(custom_font);

            TextView colon_fixture = (TextView) view.findViewById(R.id.colon_fixture);
            colon_fixture.setTypeface(custom_font);

            TextView scoreTeam2 = (TextView) view.findViewById(R.id.score_team2_fixture);
            scoreTeam2.setText(fixtureItemLocal.getScoreTeam2());
            scoreTeam2.setTypeface(custom_font);

            //code to assign background color to fixture list items and make other widgets visible
            if(fixtureItemLocal.getTimeDate().matches("FT")) //if match is over
                view.setBackgroundColor(context.getResources().getColor(R.color.color_tertiary));
            else if(fixtureItemLocal.getTimeDate().charAt(0) == 'S') { //if match is ongoing
                view.setBackgroundColor(context.getResources().getColor(R.color.color_main));
                colon_fixture.setTextColor(context.getResources().getColor(R.color.color_secondary)); //make colon visible
                teamName1.setTextColor(context.getResources().getColor(R.color.color_secondary));
                teamName2.setTextColor(context.getResources().getColor(R.color.color_secondary));
                teamName1.setTypeface(custom_font, Typeface.BOLD);
                teamName2.setTypeface(custom_font, Typeface.BOLD);
            }
            ImageView team1_logo = (ImageView) view.findViewById(R.id.image_team1_fixture);
            team1_logo.setImageResource(findTeamLogo(fixtureItemLocal.getTeamName1()));

            ImageView team2_logo = (ImageView) view.findViewById(R.id.image_team2_fixture);
            team2_logo.setImageResource(findTeamLogo(fixtureItemLocal.getTeamName2()));

        }

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