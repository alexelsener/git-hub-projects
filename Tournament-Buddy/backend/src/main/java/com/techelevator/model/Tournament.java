package com.techelevator.model;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Tournament {
    @NotNull
    private long tournamentId;
    @NotBlank(message = "Tournament name is required to continue with registration.")
    private String tournamentName;
    @NotBlank(message = "What sport/game you'll be playing is required to continue with registration.")
    private String game;
    @FutureOrPresent(message="Please provide a start date for the tournament.")
    private LocalDate startDate;
    @FutureOrPresent(message="Please provide an end date for the tournament.")
    private LocalDate endDate;
    @NotBlank(message = "Location is required to continue with registration.")
    private String location;
    @NotNull(message="A minimum entry fee of $5 is required for every tournament for facility costs.")
    @Min(5)
    private int entryFee;
    @NotBlank(message = "All tournaments must have a prize.")
    private String prizeDescription;
    @NotNull
    private int tournamentOwner;
    private boolean acceptingEntries;
    private boolean completed;

    public long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(long tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(int entryFee) {
        this.entryFee = entryFee;
    }

    public String getPrizeDescription() {
        return prizeDescription;
    }

    public void setPrizeDescription(String prizeDescription) {
        this.prizeDescription = prizeDescription;
    }

    public int getTournamentOwner() {
        return tournamentOwner;
    }

    public void setTournamentOwner(int tournamentOwner) {
        this.tournamentOwner = tournamentOwner;
    }

    public void setAcceptingEntries(Boolean acceptingEntries) {
        this.acceptingEntries = acceptingEntries;
    }

    public boolean isAcceptingEntries() {
        return acceptingEntries;
    }

    public void setCompleted(boolean completed){
        this.completed = completed;
    }

    public boolean isCompleted(){
        return completed;
    }
}