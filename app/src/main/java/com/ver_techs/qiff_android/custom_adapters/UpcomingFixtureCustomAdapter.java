package com.ver_techs.qiff_android.custom_adapters;

import android.content.Context;
import android.graphics.Typeface;
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
import java.util.Date;

import android.os.Handler;

// Adapter for Fixture tab
public class UpcomingFixtureCustomAdapter extends BaseAdapter {

    private Context context;
    ArrayList<FixtureItemLocal> fixtureItemArrayList; //Local variable list that stores all fixture items
    TextView time; //texview for dateTime in fixture
    Handler h; //handler to update dateTime field in fiture every minute
    int delay = 1000; //milliseconds to update dateTime field
    private static final int ITEM_TYPE_HEADER = 0;  // View Type for Headers
    private static final int ITEM_TYPE_REGULAR = 1; // View Type for Regular rows

    public UpcomingFixtureCustomAdapter(Context context, ArrayList<FixtureItemLocal> fixtureItemArray) {
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
    public int getItemViewType(int position) { //get type of list item, whether it is regular or a header item
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
                view = inflater.inflate(R.layout.upcoming_fixture_list_item, parent, false);
            }
        }
        else
        {
            view = convertView;
        }

        if(itemViewType == ITEM_TYPE_HEADER) { // if list item is a header

            final FixtureItemLocal fixtureItemLocal = fixtureItemArrayList.get(index);

            Typeface custom_font = Typeface.createFromAsset(context.getAssets(), context.getString(R.string.font_path));

            TextView separatorTextView = (TextView) view.findViewById(R.id.header_text_view);
            separatorTextView.setText(fixtureItemLocal.getTimeDate());
            separatorTextView.setTypeface(custom_font, Typeface.BOLD);

        }
        else { //if list item is a regular fixture item

            final FixtureItemLocal fixtureItemLocal = fixtureItemArrayList.get(index); //get the fixture item from the list to populate into the progress_spin

            Typeface custom_font = Typeface.createFromAsset(context.getAssets(), context.getString(R.string.font_path));

            //set the textviews and imageviews
            TextView teamName1 = (TextView) view.findViewById(R.id.name_team1_fixture);
            teamName1.setText(fixtureItemLocal.getTeamName1());
            teamName1.setTypeface(custom_font);

            TextView teamName2 = (TextView) view.findViewById(R.id.name_team2_fixture);
            teamName2.setText(fixtureItemLocal.getTeamName2());
            teamName2.setTypeface(custom_font);

            TextView colon_fixture = (TextView) view.findViewById(R.id.colon_fixture);
            colon_fixture.setTypeface(custom_font);

            time = (TextView) view.findViewById(R.id.time);

            if(checkIfMatchDateIsToday(fixtureItemLocal.getTimeDate())){ //check if match is scheduled for today

                final Handler handler =new Handler();
                final Runnable r = new Runnable() {
                    public void run() {
                        handler.postDelayed(this, 60000);
                if(fixtureItemLocal.getMatchStatus().contains("Start"))  //control logic for dateTime field to account for various fixture scenarios
                    time.setText(elapsedMinutes(fixtureItemLocal.getMatchStatus().replace("Start", ""))); //remove substring start
                else if (fixtureItemLocal.getMatchStatus().contains("Second"))
                    time.setText(elapsedMinutes(fixtureItemLocal.getMatchStatus().replace("Second", ""))+ " (2) "); //remove subtsring second
                else{
                    int check = matchStartsIn60Minutes(fixtureItemLocal.getTimeDate()); //check if match starts in 60 minutes
                    if(check != -1)  //if yes, print countdown
                        time.setText("Counting down " + String.valueOf(check) + "\" !");
                    else //if not, say it is an upcoming match
                        time.setText("Upcoming Match !");
                }
                    }
                };
                handler.postDelayed(r, 0000);
            }
            else
                time.setText(fixtureItemLocal.getTimeDate().substring(6,14));
            time.setTypeface(custom_font, Typeface.ITALIC);

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

    public String elapsedMinutes(String matchStartTime){ //function to calcultae minutes elapsed since game start for ongoing games
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm"); //using time format hh:mm
        Date timeNow = new Date(); //get current time
        int minutes=0; //no of minutes between current time and matchStartTime
        try {
            minutes = (int)
                    ((simpleDateFormat.parse(simpleDateFormat.format(timeNow)).getTime() -                //parse and get current time
                            simpleDateFormat.parse(matchStartTime.replace("Start", "")).getTime()         //parse and get matchStartTime
                    )*0.00001667);                                                                        //find the difference between the two in milliseconds, convert to minutes
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return String.valueOf(minutes) + "\"";  //return the elapsed minutes wid double qoute at the end
    }

    public boolean checkIfMatchDateIsToday(String matchDate){ //function to check if a match is scheduled for today
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM hh:mm"); //using time format hh:mm
        Boolean result = false;
        Date timeNow = new Date(); //get current date
        int dateDifference, monthDifference;
        try { //get difference between match date and current date
            dateDifference = simpleDateFormat.parse(matchDate).getDate() - //parse the matchDate, get date
                    simpleDateFormat.parse(simpleDateFormat.format(timeNow)).getDate(); //parse the current date, get date
            monthDifference = simpleDateFormat.parse(matchDate).getMonth() - //parse the matchDate, get month
                    simpleDateFormat.parse(simpleDateFormat.format(timeNow)).getMonth(); //parse the current date, get month
            if(dateDifference == 0 && monthDifference == 0) //if the difference between days is zero, the match is scheduled for today
                result=true;
            else
                result=false;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int matchStartsIn60Minutes(String matchDate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM hh:mm"); //using time format hh:mm
        int result = -1;
        Date timeNow = new Date(); //get current date
        int minuteDifference = -1;
        try { //get difference between match date and current date
            minuteDifference =(int) ((simpleDateFormat.parse(matchDate).getTime() - //parse the matchDate, get time in milliseconds
                    simpleDateFormat.parse(simpleDateFormat.format(timeNow)).getTime())*0.00001667); //parse the current date, get time in milliseconds
            if(minuteDifference <= 60) //after converting to minutes, check if minute difference is less than 60
                result = minuteDifference; //if so return the countdown
            else
                result = -1; //if not, return false
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(result > 0)
            return result;

        return -1;
    }

}