package com.ver_techs.qiff_android.object_classes;

/**
 * Created by aakif on 24-03-2016.
 */
public class PredictionQuestionsLocal {

    String question, matchId;

    //default constructor
    public PredictionQuestionsLocal(String question, String matchId){
        this.question=question;
        this.matchId=matchId;
    }

    public String getQuestion() {
        return question;
    }

    public String getMatchId() {
        return matchId;
    }

}


