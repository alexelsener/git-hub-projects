package com.techelevator.model;

import java.util.List;

public interface UserDao {

    /**
     * Save a new user to the database. The password that is passed in will be
     * salted and hashed before being saved. The original password is never stored
     * in the system. We will never have any idea what it is!
     *
     * @param userName  the user name to give the new user
     * @param firstName user's first name.
     * @param lastName  user's last name.
     * @param email     user's email address.
     * @param password  the user's password
     * @param role      the user's role
     * @return the new user
     */
    public User saveUser(String userName, String firstName, String lastName, String email, String password,
            String role);

    public void changePassword(User user, String newPassword);

    /**
     * Look for a user with the given username and password. Since we don't know the
     * password, we will have to get the user's salt from the database, hash the
     * password, and compare that against the hash in the database.
     *
     * @param userName the user name of the user we are checking
     * @param password the password of the user we are checking
     * @return true if the user is found and their password matches
     */
    public User getValidUserWithPassword(String userName, String password);

    /**
     * Get all of the users from the database.
     * 
     * @return a List of user objects
     */
    public List<User> getAllUsers();

    public User getUserByUsername(String username);

    /**
     * Update the user name and email in the database
     * 
     * @return a boolean if it successful
     */
    public boolean updateUser(User user);

    /**
     * Get all users from the database on a specific team
     * 
     * @param teamId the id of the team the request was sent to
     * @return a List of user objects
     */
    public List<User> getUsersByTeam(long teamId);

    /**
     * Get all teams that a user is captain of
     * 
     * @param teamId the id of the user who is checking for teams they are captain
     *               of
     * @return a List of teamId
     */
    public List<Long> getUsersCaptainedTeams(long userId);

    /**
     * Get the user who is the tournament owner of a given tournament
     * 
     * @param tournamentId the id of the user who is checking for teams they are
     *                     captain of
     * @return the username of the tournament organizer.
     */

    public User getTournamentOwnerUsername(long tournamentId);

}
