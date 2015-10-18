package com.ver_techs.qiff_android.fragments;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ver_techs.qiff_android.R;
import com.ver_techs.qiff_android.activities.MainActivity;
import com.ver_techs.qiff_android.activities.Suggestion;

//Class that defines Fragment with details about QIFF
public class AboutFragment extends Fragment{

    View v;
    Typeface custom_font;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_about, container, false);

        //set custom font to all the text views
        custom_font = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.font_path));

        TextView match_schedule_text = (TextView) v.findViewById(R.id.about_text);
        match_schedule_text.setTypeface(custom_font, Typeface.BOLD);

        TextView version_text = (TextView) v.findViewById(R.id.version_text);
        version_text.setTypeface(custom_font, Typeface.ITALIC);

        TextView email_text = (TextView) v.findViewById(R.id.email_text);
        email_text.setTypeface(custom_font);

        TextView mobile_text = (TextView) v.findViewById(R.id.mobile_text);
        mobile_text.setTypeface(custom_font);

        TextView powered_by = (TextView) v.findViewById(R.id.powered_by);
        powered_by.setTypeface(custom_font);

        TextView suggestion_about = (TextView) v.findViewById(R.id.suggestion_about);
        suggestion_about.setPaintFlags(suggestion_about.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        suggestion_about.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Suggestion.class);
                startActivity(i);
            }
        });


        ImageView vertechs_logo_about = (ImageView) v.findViewById(R.id.vertechs_logo_about);
        vertechs_logo_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.facebook.com/Vertechs?fref=ts"));
                startActivity(intent);
            }
        });

        ImageView icon_facebook = (ImageView) v.findViewById(R.id.icon_facebook);
        icon_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.facebook.com/QatarIndianFootballForum"));
                startActivity(intent);
            }
        });

        return v;
    }

}
