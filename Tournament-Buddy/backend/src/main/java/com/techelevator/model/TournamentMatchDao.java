package com.techelevator.model;

import java.util.List;

public interface TournamentMatchDao {

    public void createMatches(List<TournamentMatch> matches);

    public boolean finalizeMatches(List<TournamentMatch> matches);

    public List<TournamentMatch> getAllMatchesByTournamentRound(Long tournamentId, Long round);

    public List<Integer> getAllRoundsByTournamentId(long tournamentId);

    public boolean updateMatchScores(TournamentMatch tournamentMatch);

}