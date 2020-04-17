package com.techelevator.model;

import java.util.List;

public interface TeamDao {

    /**
     * Save a new team to the database.
     *
     * @param teamName            the team name provided by the user team captain.
     * @param game                game the team is playing
     * @param acceptingNewMembers boolean: if true, accept new member requests.
     * @param teamBio             team bio provided by team captain.
     * @return the new team.
     */
    public Team createTeam(Team newTeam, Long userId);

    /**
     * get a list of all active teams for any user to browse;
     * 
     * @return List of Team objects.
     */
    public List<Team> getAllTeams();

    /**
     * get single team profile based on the team id.
     * 
     * @param teamId
     * @return all team information
     */
    public Team getTeamById(long id);

    /**
     * get all teams tied to a specific user
     * 
     * @param userId
     * @return all teams that a specific user belongs to
     */
    public List<Team> getTeamsByUser(long id);

    /**
     * update team name, game, and bio in the database
     * 
     * @return a boolean if successful
     */
    public boolean updateTeam(Team team);

    public List<Team> getTeamsForCaptain(String game, Long userId);

    /**
     * add user to team
     * 
     * @param request request being accepted
     * @param captainStatus boolean set to true if the user being added is a captain
     *                      and set to false if the user is not a captain
     * @return boolean for true if successful
     */
    public boolean addMember(Request request, boolean captainStatus);

    /**
     * delete user from team
     * 
     * @param userId user id of user being deleted
     * @param teamId team id of the team the user is being deleted from
     * @return boolean set to true if successful
     */
    public boolean deleteMember(long userId, long teamId);

}