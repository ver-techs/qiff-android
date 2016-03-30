package com.ver_techs.qiff_android.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.ver_techs.qiff_android.R;
import com.ver_techs.qiff_android.custom_views.CustomProgressDialog;
import com.ver_techs.qiff_android.object_classes.FixtureItem;
import com.ver_techs.qiff_android.object_classes.PointsTableItem;
import com.ver_techs.qiff_android.object_classes.PredictionQuestionsLocal;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by aakif on 27-03-2016.
 */

public class PredictionFragment extends Fragment{

    View v;
    private PredictionQuestionsLocal predictionQuestionsLocal;
    String s;
    private ProgressDialog nDialog; //progress dialog to show while parse query is running in background
    ImageView team1_logo, team2_logo;
    TextView name_team1, name_team2;

    public PredictionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_prediction, container, false);

        //Set and show the progress dialog
        nDialog = new CustomProgressDialog(getActivity());
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(false);
        nDialog.show();

        Bundle b = getArguments();
        predictionQuestionsLocal = new PredictionQuestionsLocal(b.getString("question"),b.getString("matchId"));

        TextView question = (TextView) v.findViewById(R.id.question);
        question.setText(predictionQuestionsLocal.getQuestion());

        team1_logo = (ImageView) v.findViewById(R.id.image_team1);
        team2_logo = (ImageView) v.findViewById(R.id.image_team2);
        name_team1 = (TextView) v.findViewById(R.id.name_team1);
        name_team2 = (TextView) v.findViewById(R.id.name_team2);

        new GetTeamNames(this).execute();
//        ParseQuery<FixtureItem> query = ParseQuery.getQuery("FixtureItem");
//        query.whereEqualTo("objectId", predictionQuestionsLocal.getMatchId());
//        query.getFirstInBackground(new GetCallback<FixtureItem>() {
//            public void done(FixtureItem object, ParseException e) {
//                if (object == null) {
//                    Log.d("aaki", "The getFirst request failed.");
//                } else {
//                    Log.d("aaki", "Retrieved the object.");
//                    Log.i("aaki", object.getTeamName1());
//                    Log.i("aaki", object.getTeamName2());
//                }
//            }
//        });

        return v;
    }

    private class GetTeamNames extends AsyncTask<Void,Void,Void>
    {
        private WeakReference<Fragment> weakRef;
        List<FixtureItem> findFriends = null;

        public GetTeamNames(Fragment frag){
            weakRef = new WeakReference< Fragment >(frag);
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Define the class we would like to query
            ParseQuery<FixtureItem> query3 = ParseQuery.getQuery(FixtureItem.class);
            query3.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);

            query3.whereEqualTo("objectId", predictionQuestionsLocal.getMatchId());
            // Execute the find asynchronously

            try {
                findFriends = query3.find();
            } catch (ParseException e) {
                e.printStackTrace();
                Log.d("aaki", "The getFirst request failed.");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            Fragment frag = weakRef.get();
            FixtureItem fixtureItem = null;
            if (frag != null){
                if(findFriends != null) {
                    Log.i("aaki", String.valueOf(findFriends.size()));
                    nDialog.cancel(); //cancel the dialog once load has completed
                    try {
                        fixtureItem = findFriends.get(0);
                    }
                    catch (NullPointerException e){
                        e.printStackTrace();
                    }
                    name_team1.setText(fixtureItem.getTeamName1());  //set team names
                    name_team2.setText(fixtureItem.getTeamName2());
                    team1_logo.setImageResource(findTeamLogo(fixtureItem.getTeamName1()));
                    team2_logo.setImageResource(findTeamLogo(fixtureItem.getTeamName2()));
                }
            }
        }
    }

    public int findTeamLogo(String teamName) {
        // code to find corresponding image of respective teams

        int resource_id = 0;
        switch (teamName) {
            case "NADHAM TCR":
                resource_id = R.drawable.kmcc_wnd;
                break;
            case "KMCC MLP":
                resource_id = R.drawable.kmcc_mlp;
                break;
            case "KMCC KKD":
                resource_id = R.drawable.kmcc_kkd;
                break;
            case "KMCC PKD":
                resource_id = R.drawable.kmcc_pkd;
                break;
            case "SKIA TVM":
                resource_id = R.drawable.skia_tvm;
                break;
            case "KMCC WND":
                resource_id = R.drawable.kmcc_wnd;
                break;
            case "CFQ PTNMTA":
                resource_id = R.drawable.cfq_ptnmta;
                break;
            case "MAK KKD":
                resource_id = R.drawable.mak_kkd;
                break;
            case "EDSO EKM":
                resource_id = R.drawable.edso_ekm;
                break;
            case "MAMWAQ MLP":
                resource_id = R.drawable.mamwaq_mlp;
                break;
            case "KMCC KNR":
                resource_id = R.drawable.kmcc_knr;
                break;
            case "CFQ KKD":
                resource_id = R.drawable.cfq_kkd;
                break;
            case "TYC TCR":
                resource_id = R.drawable.tyc_tcr;
                break;
            case "KMCC TCR":
                resource_id = R.drawable.kmcc_tcr;
                break;
            case "KMCC KSGD":
                resource_id = R.drawable.kmcc_ksgd;
                break;
            case "KPAQ KKD":
                resource_id = R.drawable.kpaq_kkd;
                break;
        }
        return resource_id;
    }

}