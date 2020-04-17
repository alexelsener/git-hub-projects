package com.techelevator.model;

import java.util.List;
import java.util.Map;

public interface TournamentDao {

    public List<Tournament> getAllTournaments();

    public Tournament getTournamentById(long id);

    public List<Tournament> getTournamentsByUser(long id);

    public List<Tournament> getTournamentsByTeam(long id);

    public void createTournament(Tournament newTournament, Long userId);

    public boolean updateTournament(Tournament tournament);

    public Map<Integer,String> getTourneyWins(long id);

    public void endTournament(long id);

}