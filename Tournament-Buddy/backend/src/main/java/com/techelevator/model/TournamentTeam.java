package com.techelevator.model;

public class TournamentTeam {

    private long teamId;
    private String teamName;
    private String captainUsername;
    private String captainEmail;

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getCaptainUsername() {
        return captainUsername;
    }

    public void setCaptainUsername(String captainUsername) {
        this.captainUsername = captainUsername;
    }

    public String getCaptainEmail() {
        return captainEmail;
    }

    public void setCaptainEmail(String captainEmail) {
        this.captainEmail = captainEmail;
    }

}