package com.ver_techs.qiff_android.object_classes;

import com.parse.ParseClassName;
import com.parse.ParseObject;

//Class to store points table items on parse
@ParseClassName("PointsTableItem")
public class PointsTableItem extends ParseObject {

    //default constructor
    public  PointsTableItem(){
    }

    public String getTeamName() {
        return getString("teamName");
    }

    public String getTotal() {
        return getString("total");
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

    public String getGoalDifference() {
        return getString("goal_difference");
    }
}