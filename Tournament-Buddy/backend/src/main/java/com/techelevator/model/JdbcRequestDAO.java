package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcRequestDAO implements RequestDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcRequestDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void createTeamRequest(Request request) {
        String sql = "INSERT into teamrequest(user_id, team_id, message) VALUES(?,?,?);";
        jdbcTemplate.update(sql, request.getSenderId(), request.getRecipientId(), request.getMessage());
    }

    @Override
    public void deleteTeamRequest(Request request) {
        String sql = "DELETE FROM teamRequest WHERE user_id = ? AND team_id = ?";
        jdbcTemplate.update(sql, request.getSenderId(), request.getRecipientId());
        
    }

    @Override
    public List<Request> getRequestsByTournamentId(Long tournamentId) {
        List<Request> tournamentRequests = new ArrayList<>();

        String sql ="SELECT teams.team_name, message, team_id, tourney_id FROM tournamentrequest " +
        "JOIN teams ON tournamentrequest.team_id = teams.id " +
        "WHERE tourney_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, tournamentId);
        while(results.next()){
            Request newRequest = new Request();
            newRequest.setSenderName(results.getString("team_name"));
            newRequest.setMessage(results.getString("message"));
            newRequest.setSenderId(results.getLong("team_id"));
            newRequest.setRecipientId(results.getLong("tourney_id"));
            tournamentRequests.add(newRequest);
        }
       
        return tournamentRequests;
    }

    @Override
    public List<Request> getRequestsByTeamId(Long teamId) {
        List<Request> teamRequests = new ArrayList<>();

        String sql ="SELECT users.username, message, user_id, team_id FROM teamrequest " +
        "JOIN users ON teamrequest.user_id = users.id " +
        "WHERE team_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, teamId);
        while(results.next()){
            Request newRequest = new Request();
            newRequest.setSenderName(results.getString("username"));
            newRequest.setMessage(results.getString("message"));
            newRequest.setSenderId(results.getLong("user_id"));
            newRequest.setRecipientId(results.getLong("team_id"));
            teamRequests.add(newRequest);
        }
       
        return teamRequests;
    }

    @Override
    public void deleteTourneyRequest(Request request) {
        String sql = "DELETE FROM tournamentrequest WHERE team_id = ? AND tourney_id = ?";
        jdbcTemplate.update(sql, request.getSenderId(), request.getRecipientId());
    }

    @Override
    public void acceptTourneyRequest(Request request) {
        String sql = "INSERT INTO tournamentroster (tourney_id, team_id) VALUES (?,?)";
        jdbcTemplate.update(sql, request.getRecipientId(), request.getSenderId());

    }

    @Override
    public void createTournamentRequest(Request request) {
        String sql = "INSERT into tournamentrequest (team_id, tourney_id, message) VALUES(?,?,?);";
        jdbcTemplate.update(sql, request.getSenderId(), request.getRecipientId(), request.getMessage());
    }

}