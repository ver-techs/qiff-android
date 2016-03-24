package com.ver_techs.qiff_android.object_classes;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("PredictionQuestions")
/**
 * Created by aakif on 24-03-2016.
 */

public class PredictionQuestions extends ParseObject  {

// Class for holding prediction questions in parse

    //default constructor
    public PredictionQuestions(){
    }

    public PredictionQuestions(String question, String matchId) {
        super();
        setQuestion(question);
        setMatchId(matchId);
    }

    public void setQuestion(String question){
        put("question", question);
    }

    public void setMatchId(String matchId){
        put("matchId", matchId);
    }

    public String getQuestion() {
        return getString("question");
    }

    public String getMatchId() {
        return getString("matchId");
    }

}