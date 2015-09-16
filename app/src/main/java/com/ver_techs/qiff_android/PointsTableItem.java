package com.ver_techs.qiff_android;

import com.parse.ParseClassName;
import com.parse.ParseObject;

//Class to store points table items on parse
@ParseClassName("FixtureItem")
public class PointsTableItem extends ParseObject {

    //default constructor
    public  PointsTableItem(){
    }

    public String getTeamName() {
        return getString("teamName");
    }

    public String getPlayed() {
        return getString("played");
    }

    public String getWins() {
        return getString("wins");
    }

    public String getDraws() {
        return getString("draws");
    }

    public String getLosses() {
        return getString("losses");
    }

}