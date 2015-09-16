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

        View v = inflater.inflate(R.layout.fragment_points_table, container, false);

        final TextView name_group_1 = (TextView) v.findViewById(R.id.name_group1);

        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.font_path));

        name_group_1.setTypeface(custom_font);

        return v;

    }

}
