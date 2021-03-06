package com.ver_techs.qiff_android.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.facebook.AccessToken;
import com.facebook.appevents.AppEventsLogger;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.ver_techs.qiff_android.R;
import com.ver_techs.qiff_android.custom_views.CustomProgressDialog;
import com.ver_techs.qiff_android.fragments.PredictionFragment;
import com.ver_techs.qiff_android.object_classes.PredictionQuestions;
import com.ver_techs.qiff_android.object_classes.PredictionQuestionsLocal;

import java.util.ArrayList;
import java.util.List;

public class Prediction extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ProgressDialog nDialog; //progress dialog to show while parse query is running in background
    private String[] titles = new String[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);

        //Set and show the progress dialog
        nDialog = new CustomProgressDialog(this);
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(false);
        nDialog.show();

        //Disable action bar on top of screen
        getSupportActionBar().hide();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                tabLayout.setupWithViewPager(viewPager);
            }
        }, 2000);

        titles[0] = "ONE";
        titles[1] = "TWO";
        titles[2] = "THREE";
        titles[3] = "FOUR";
        titles[4] = "FIVE";

        //set font to header in the fragment
        TextView prediction_text = (TextView) findViewById(R.id.prediction_text);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), getString(R.string.font_path));
        prediction_text.setTypeface(custom_font, Typeface.BOLD);

    }

    private void setupViewPager(ViewPager viewPager) {

        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        ParseQuery query = ParseQuery.getQuery("PredictionQuestions");
        query.findInBackground(new FindCallback<PredictionQuestions>() {

            public void done(List<PredictionQuestions> predictionQuestionsList, ParseException e) {

                if (e == null) {

                    // Access the array of results here
                    for (int i = 0; i < predictionQuestionsList.size(); i++) {

                        final PredictionQuestionsLocal predictionQuestionsLocal = new PredictionQuestionsLocal(
                                predictionQuestionsList.get(i).getString("question"), predictionQuestionsList.get(i).getString("matchId"));
                                Bundle args = new Bundle();
                                args.putString("question", predictionQuestionsLocal.getQuestion());
                                args.putString("matchId", predictionQuestionsLocal.getMatchId());
                                PredictionFragment pf = new PredictionFragment();
                                pf.setArguments(args);
                                adapter.addFrag(pf, titles[i]); //set question titles
                                adapter.notifyDataSetChanged();
                    }
                    nDialog.cancel(); //cancel the dialog once load has completed
                }
            }
        });

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<PredictionFragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(PredictionFragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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
    public void onBackPressed(){ //on back button being pressed, move to main activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
