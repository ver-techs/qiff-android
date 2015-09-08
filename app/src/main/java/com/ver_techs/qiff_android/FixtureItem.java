package com.ver_techs.qiff_android;

import com.parse.ParseObject;
import com.parse.ParseClassName;

/**
 * Created by Sony on 8/28/2015.
 */

@ParseClassName("FixtureItem")
public class FixtureItem extends ParseObject{

    String teamName1, teamName2;
    String scoreTeam1, scoreTeam2;
    String timeDate;

    //default constructor
    public  FixtureItem(){
    }

    public String getTeamName1() {

        return getString("teamName1");
    }

    public String getTeamName2() {

        return getString("teamName2");

    }

    public String getScoreTeam1() {

        return getString("scoreTeam1");
    }

    public String getScoreTeam2() {

        return getString("scoreTeam2");

    }

    public String getTimeDate() {

        return getString("dateTime");
    }

}
