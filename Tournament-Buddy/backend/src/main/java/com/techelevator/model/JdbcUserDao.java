package com.techelevator.model;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.sql.DataSource;

import com.techelevator.authentication.PasswordHasher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcUserDao implements UserDao {

    private JdbcTemplate jdbcTemplate;
    private PasswordHasher passwordHasher;

    /**
     * Create a new user dao with the supplied data source and the password hasher
     * that will salt and hash all the passwords for users.
     *
     * @param dataSource     an SQL data source
     * @param passwordHasher an object to salt and hash passwords
     */
    @Autowired
    public JdbcUserDao(DataSource dataSource, PasswordHasher passwordHasher) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.passwordHasher = passwordHasher;
    }

    /**
     * Save a new user to the database. The password that is passed in will be
     * salted and hashed before being saved. The original password is never stored
     * in the system. We will never have any idea what it is!
     *
     * @param userName the user name to give the new user
     * @param password the user's password
     * @param role     the user's role
     * @return the new user
     */
    @Override
    public User saveUser(String userName, String firstName, String lastName, String email, String password,
            String role) {
        byte[] salt = passwordHasher.generateRandomSalt();
        String hashedPassword = passwordHasher.computeHash(password, salt);
        String saltString = new String(Base64.getEncoder().encode(salt));
        try {
            long newId = jdbcTemplate.queryForObject(
                    "INSERT INTO users(username, first_name, last_name, email, password, salt, role) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id",
                    Long.class, userName, firstName, lastName, email, hashedPassword, saltString, role);

            User newUser = new User();
            newUser.setId(newId);
            newUser.setUsername(userName);
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setEmail(email);
            newUser.setRole(role);
            return newUser;
        } catch (DuplicateKeyException e) {
            return null;
        }
    }

    @Override
    public void changePassword(User user, String newPassword) {
        byte[] salt = passwordHasher.generateRandomSalt();
        String hashedPassword = passwordHasher.computeHash(newPassword, salt);
        String saltString = new String(Base64.getEncoder().encode(salt));

        jdbcTemplate.update("UPDATE users SET password=?, salt=? WHERE id=?", hashedPassword, saltString, user.getId());
    }

    /**
     * Look for a user with the given username and password. Since we don't know the
     * password, we will have to get the user's salt from the database, hash the
     * password, and compare that against the hash in the database.
     *
     * @param userName the user name of the user we are checking
     * @param password the password of the user we are checking
     * @return true if the user is found and their password matches
     */
    @Override
    public User getValidUserWithPassword(String userName, String password) {
        String sqlSearchForUser = "SELECT * FROM users WHERE UPPER(username) = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSearchForUser, userName.toUpperCase());
        if (results.next()) {
            String storedSalt = results.getString("salt");
            String storedPassword = results.getString("password");
            String hashedPassword = passwordHasher.computeHash(password, Base64.getDecoder().decode(storedSalt));
            if (storedPassword.equals(hashedPassword)) {
                return mapResultToUser(results);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Get all of the users from the database.
     * 
     * @return a List of user objects
     */
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        String sqlSelectAllUsers = "SELECT id, username, role, first_name, last_name, email FROM users";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAllUsers);

        while (results.next()) {
            User user = mapResultToUser(results);
            users.add(user);
        }

        return users;
    }

    private User mapResultToUser(SqlRowSet results) {
        User user = new User();
        user.setId(results.getLong("id"));
        user.setUsername(results.getString("username"));
        user.setRole(results.getString("role"));
        user.setFirstName(results.getString("first_name"));
        user.setLastName(results.getString("last_name"));
        user.setEmail(results.getString("email"));
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        String sql = "SELECT id, username, role, email, first_name, last_name FROM users WHERE username = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);

        if (results.next()) {
            return mapResultToUser(results);
        } else {
            return null;
        }
    }

    @Override
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, " + "email = ? WHERE username = ?";
        jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(), user.getEmail(), user.getUsername());
        return true;
    }

    @Override
    public List<User> getUsersByTeam(long teamId) {
        List<User> teamRoster = new ArrayList<>();
        String sql = "SELECT id, username, role, email, first_name, last_name, teamroster.captain FROM users "
                + "JOIN teamroster ON teamroster.user_id = users.id "
                + "WHERE id IN (SELECT user_id FROM teamRoster WHERE team_id = ?) AND teamroster.team_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, teamId, teamId);
        while (results.next()) {
            User member = mapResultToUser(results);
            if (results.getBoolean("captain")) {
                member.setRole("captain");
            }
            teamRoster.add(member);
        }
        return teamRoster;
    }

    @Override
    public List<Long> getUsersCaptainedTeams(long userId) {
        List<Long> captainedTeams = new ArrayList<>();

        String sql = "SELECT team_id FROM teamroster WHERE user_id = ? AND captain = true";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            captainedTeams.add(results.getLong("team_id"));
        }

        return captainedTeams;
    }

    @Override
    public User getTournamentOwnerUsername(long tournamentId) {
        User user = new User();
        String sql = "SELECT a.username FROM users a "
                + "JOIN tournaments b on a.id = b.tournament_owner WHERE b.id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, tournamentId);
        if (results.next()) {
            user.setUsername(results.getString("username"));
        }
        return user;
    }

}