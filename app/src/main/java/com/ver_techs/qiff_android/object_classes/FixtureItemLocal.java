package com.ver_techs.qiff_android.object_classes;

//Class to store local fixture items
public class FixtureItemLocal{

    String teamName1, teamName2;
    String scoreTeam1, scoreTeam2;
    String timeDate;

    //default constructor
    public  FixtureItemLocal(String teamName1, String teamName2, String scoreTeam1, String scoreTeam2, String timeDate){
        this.teamName1=teamName1;
        this.teamName2=teamName2;
        this.scoreTeam1=scoreTeam1;
        this.scoreTeam2=scoreTeam2;
        this.timeDate=timeDate;
    }

    public String getTeamName1() {
        return teamName1;
    }

    public String getTeamName2() {
        return teamName2;
    }

    public String getScoreTeam1() {
        return scoreTeam1;
    }

    public String getScoreTeam2() {
        return scoreTeam2;
    }

    public String getTimeDate() {
        return timeDate;
    }

}