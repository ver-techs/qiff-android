package com.ver_techs.qiff_android.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.ver_techs.qiff_android.R;
import com.ver_techs.qiff_android.custom_views.CustomProgressDialog;
import com.ver_techs.qiff_android.object_classes.FixtureItem;
import com.ver_techs.qiff_android.object_classes.PredictionAnswer;
import com.ver_techs.qiff_android.object_classes.PredictionQuestionsLocal;

import org.json.JSONException;
import org.json.JSONObject;

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
    Button send;
    String user_id;

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

        final NumberPicker numberPicker1 = (NumberPicker) v.findViewById(R.id.score_team1_predicted);
        numberPicker1.setMaxValue(10);
        numberPicker1.setMinValue(0);
        numberPicker1.setWrapSelectorWheel(true);

        final NumberPicker numberPicker2 = (NumberPicker) v.findViewById(R.id.score_team2_predicted);
        numberPicker2.setMaxValue(10);
        numberPicker2.setMinValue(0);
        numberPicker2.setWrapSelectorWheel(true);

        send = (Button) v.findViewById(R.id.send_prediction);

        new GetTeamNames(this).execute();

        send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i("aaki", "rechd 1");
                /* make the facebook API call to get fb username */
                GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                // When fields have been recieved
                                Log.i("aaki", "rechd 2");
                                final Profile profile = Profile.getCurrentProfile(); //get current user profile details

                                try {
                                    user_id = object.getString("id"); //get id number from the json object
                                    Log.i("aaki", user_id);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                try{

                                    String scoreTeam1 = String.valueOf(numberPicker1.getValue());
                                    String scoreTeam2 = String.valueOf(numberPicker2.getValue());

                                    PredictionAnswer predictionAnswer = new PredictionAnswer(predictionQuestionsLocal.getMatchId(), scoreTeam1, scoreTeam2, user_id); //create a new chatitem

                                    // Save the data to Parse whenever internet is available
                                    predictionAnswer.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(com.parse.ParseException e) {

                                            if (e == null) {
                                                Log.i("aaki", "prediction answer sent to parse successfully");
                                            } else {
                                                Log.i("aaki", "prediction answer sending unsuccessful");
                                            }
                                        }
                                    });

                                }
                                catch(Exception ex){
                                    ex.printStackTrace();
                                }

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id"); //fields to fetch during facebook api request
                request.setParameters(parameters);
                request.executeAsync(); //execute api call
            }
        });

        return v;
    }

    private class GetTeamNames extends AsyncTask<Void,Void,Void>
    {
        private WeakReference<Fragment> weakRef;
        List<FixtureItem> question = null;

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
                question = query3.find();
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
                if(question != null) {

                    try {
                        fixtureItem = question.get(0);
                    }
                    catch (NullPointerException e){
                        e.printStackTrace();
                    }
                    name_team1.setText(fixtureItem.getTeamName1());  //set team names
                    name_team2.setText(fixtureItem.getTeamName2());
                    team1_logo.setImageResource(findTeamLogo(fixtureItem.getTeamName1()));
                    team2_logo.setImageResource(findTeamLogo(fixtureItem.getTeamName2()));
                    nDialog.cancel(); //cancel the dialog once load has completed
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