package com.techelevator.model;

import java.util.List;

public interface RequestDAO {


    public void createTeamRequest(Request request);

    public void deleteTeamRequest(Request request);

    public List<Request> getRequestsByTournamentId(Long tournamentId);

    public List<Request> getRequestsByTeamId(Long teamId);

    public void deleteTourneyRequest(Request request);

    public void acceptTourneyRequest(Request request);

    public void createTournamentRequest(Request request);

}