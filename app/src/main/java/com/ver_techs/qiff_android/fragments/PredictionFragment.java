package com.ver_techs.qiff_android.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ver_techs.qiff_android.R;
import com.ver_techs.qiff_android.custom_views.CustomProgressDialog;
import com.ver_techs.qiff_android.object_classes.PredictionQuestionsLocal;

/**
 * Created by aakif on 27-03-2016.
 */

public class PredictionFragment extends Fragment{

    View v;
    //private PredictionQuestionsLocal predictionQuestionsLocal;
    String s;
    private ProgressDialog nDialog; //progress dialog to show while parse query is running in background

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

        Bundle b = getArguments();

        final TextView question = (TextView) getActivity().findViewById(R.id.question);
        question.setText(b.getString("question"));

        return v;
    }

}