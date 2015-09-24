package com.ver_techs.qiff_android.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.ver_techs.qiff_android.R;

import java.util.Random;

// Activity that displays splash screen
public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Football Facts
        String[] facts = new String[]{"Football is the most played and most watched sport on earth !",
                "Football originated in China around 476 B.C. !",
                "Football goalies didn't have to wear different coloured shirts from their teammates until 1913 !",
                "Football players run an average of 9.65 kms during every game !",
                "Pele was the first to call football - the beautiful game !",
                "Neil Armstrong originally wanted to take a football to the moon !",
                "Football is like life, it requires perseverance, self-denial, hard work, sacrifice, dedication and respect for authority. - Vince Lombardi",
                "Some people think football is a matter of life and death. I don't like that attitude. I can assure them it is much more serious than that. -  Bill Shankly"};

        //Generate a Random number to display a random football fact
        Random rand = new Random();
        int randomNum = rand.nextInt(facts.length);

        final TextView factView = (TextView) findViewById(R.id.FootballFact);
        factView.setText(facts[randomNum]);

        final TextView poweredBy = (TextView) findViewById(R.id.PoweredBy);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), getString(R.string.font_path));

        factView.setTypeface(custom_font);
        poweredBy.setTypeface(custom_font);

        new Handler().postDelayed(new Runnable() {

            //Showing splash screen with a timer.

            @Override
            public void run() {
                // Executed once the timer is over, start main activity
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                // Close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}