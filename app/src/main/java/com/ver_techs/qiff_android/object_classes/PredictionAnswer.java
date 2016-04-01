package com.ver_techs.qiff_android.object_classes;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by aakif on 30-03-2016.
 */

@ParseClassName("PredictionAnswer")

public class PredictionAnswer extends ParseObject {
    // Class for holding prediction questions in parse

    //default constructor
    public PredictionAnswer(){
    }

    public PredictionAnswer(String matchId, String scoreTeam1, String scoreTeam2, String userFbId) {
        super();
        setMatchId(matchId);
        setScoreTeam1(scoreTeam1);
        setScoreTeam2(scoreTeam2);
        setUserFbId(userFbId);
    }

    public void setMatchId(String matchId){
        put("matchId", matchId);
    }

    public void setScoreTeam1(String scoreTeam1){
        put("scoreTeam1", scoreTeam1);
    }

    public void setScoreTeam2(String scoreTeam2){
        put("scoreTeam2", scoreTeam2);
    }

    public void setUserFbId(String userFbId) {
        put("userFacebookId", userFbId);
    }

    public String getMatchId() {
        return getString("matchId");
    }

    public String getScoreTeam1() {
        return getString("scoreTeam1");
    }

    public String getScoreTeam2() {
        return getString("scoreTeam2");
    }

    public String getUserFbId() {
        return getString("userFacebookId");
    }
}