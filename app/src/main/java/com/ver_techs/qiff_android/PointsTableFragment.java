package com.ver_techs.qiff_android;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by AAKIFAH RAHMAN on 9/17/2015.
 */
public class PointsTableFragment extends Fragment{

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_empty, container, false);

        final TextView live_commentary_text = (TextView) v.findViewById(R.id.live_commentary_text);
        final TextView predictionContest = (TextView) v.findViewById(R.id.predictionContest);
        final TextView fanChatRooms = (TextView) v.findViewById(R.id.fanChatRooms);

        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.font_path));

        live_commentary_text.setTypeface(custom_font);
        predictionContest.setTypeface(custom_font);
        fanChatRooms.setTypeface(custom_font);

        return v;

    }

}
