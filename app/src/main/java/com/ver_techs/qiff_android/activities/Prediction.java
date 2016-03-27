package com.ver_techs.qiff_android.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.appevents.AppEventsLogger;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.ver_techs.qiff_android.R;
import com.ver_techs.qiff_android.object_classes.PointsTableItem;
import com.ver_techs.qiff_android.object_classes.PointsTableItemLocal;
import com.ver_techs.qiff_android.object_classes.PredictionQuestions;
import com.ver_techs.qiff_android.object_classes.PredictionQuestionsLocal;

import java.util.List;

/**
 * Created by aakif on 14-03-2016.
 */
public class Prediction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);

        //Disable action bar on top of screen
        getSupportActionBar().hide();

        //set font to header in the fragment
        TextView prediction_text = (TextView) findViewById(R.id.prediction_text);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), getString(R.string.font_path));
        prediction_text.setTypeface(custom_font, Typeface.BOLD);

//        final TextView question = (TextView) findViewById(R.id.question);
//
//        ParseQuery query = ParseQuery.getQuery("PredictionQuestions");
//        query.findInBackground(new FindCallback<PredictionQuestions>() {
//
//            public void done(List<PredictionQuestions> predictionQuestionsList, ParseException e) {
//
//                if (e == null) {
//                    // Access the array of results here
//                    for (int i = 0; i < predictionQuestionsList.size(); i++) {
//                        PredictionQuestionsLocal predictionQuestionsLocal = new PredictionQuestionsLocal(
//                                predictionQuestionsList.get(i).getString("question"), predictionQuestionsList.get(i).getString("matchId"));
//                        question.setText(predictionQuestionsList.get(i).getString("question"));  //set team names
//                    }
//
//                }
//            }
//        });
    }

            @Override
            protected void onResume() {
                super.onResume();

                // Logs 'install' and 'app activate' App Events.
                AppEventsLogger.activateApp(this);
            }

            @Override
            protected void onPause() {
                super.onPause();

                // Logs 'app deactivate' App Event.
                AppEventsLogger.deactivateApp(this);
            }

            public boolean isLoggedIn() {
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                return accessToken != null;
            }

            @Override
            public void onBackPressed() {

            }
        }
