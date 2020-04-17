package com.techelevator.model;

import java.util.List;

public interface TournamentTeamDao {

    /**
     * get single team profile based on the team id.
     * 
     * @param tournamentId
     * @return all teams and captains for a single tournament.
     */
    public List<TournamentTeam> getTournamentRosterById(long tournamentId);

    /**
     * delete a single team profile based on the team id for a specific tournament.
     * 
     * @param tournamentId
     * @param teamId
     * @return all teams and captains for a single tournament.
     */
    public boolean deleteTeam(long tournamentId, long teamId);

    /**
     * delete a single team profile based on the team id for a specific tournament.
     * 
     * @param teamId
     * @return all teams and captains for a single tournament.
     */
    public TournamentTeam getTournamentTeamByTeamId(long teamId);
}