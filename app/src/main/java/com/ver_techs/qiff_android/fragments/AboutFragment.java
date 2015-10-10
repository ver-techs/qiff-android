package com.ver_techs.qiff_android.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ver_techs.qiff_android.R;

public class AboutFragment extends Fragment{

    View v;
    Typeface custom_font;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_about, container, false);

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

        return v;
    }

}
