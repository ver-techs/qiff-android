package com.ver_techs.qiff_android;

//Class to store local points table items
public class   PointsTableItemLocal{

    String teamName, played, wins, draws, losses;

    //default constructor
    public  PointsTableItemLocal(String teamName, String played, String wins, String draws, String losses){
        this.teamName=teamName;
        this.played=played;
        this.wins=wins;
        this.draws=draws;
        this.losses=losses;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getPlayed() {
        return played;
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
}