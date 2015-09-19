package com.ver_techs.qiff_android;

//Class to store local points table items
public class   PointsTableItemLocal{

    String teamName, total, wins, draws, losses, goal_difference;

    //default constructor
    public  PointsTableItemLocal(String teamName, String total, String wins, String draws, String losses, String goal_difference){
        this.teamName=teamName;
        this.total=total;
        this.wins=wins;
        this.draws=draws;
        this.losses=losses;
        this.goal_difference=goal_difference;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTotal() {
        return total;
    }

    public String getWins() {
        return wins;
    }

    public String getDraws() {
        return draws;
    }

    public String getLosses() {
        return losses;
    }

    public String getGoalDifference() {
        return goal_difference;
    }
}