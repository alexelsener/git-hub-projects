package com.techelevator.model;

public class TournamentMatch {

    private long id;
    private long tournamentId;
    private int round;
    private TournamentTeam homeTeam;
    private TournamentTeam awayTeam;
    private int homeScore;
    private int awayScore;
    private long winnerId;

    public long getId() {
        return id;
    }

    public long getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(long winnerId) {
        this.winnerId = winnerId;
    }

    public long getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public long getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public TournamentTeam getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(TournamentTeam awayTeam) {
        this.awayTeam = awayTeam;
    }

    public TournamentTeam getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(TournamentTeam homeTeam) {
        this.homeTeam = homeTeam;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(long tournamentId) {
        this.tournamentId = tournamentId;
    }

    public void setId(long id) {
        this.id = id;
    }

}