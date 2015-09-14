package com.ver_techs.qiff_android;

import com.parse.ParseObject;
import com.parse.ParseClassName;

//Class to store fixture items on parse
@ParseClassName("FixtureItem")
public class FixtureItem extends ParseObject{

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