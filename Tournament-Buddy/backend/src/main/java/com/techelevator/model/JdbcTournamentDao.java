package com.techelevator.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcTournamentDao implements TournamentDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTournamentDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Tournament> getAllTournaments() {
        List<Tournament> allTournaments = new ArrayList<>();
        String sql = "SELECT id, tourney_name, game, start_date, end_date, location, entry_fee, prize_desc, accepting_entries, tournament_owner, completed FROM "
                + "tournaments;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Tournament tournament = mapRowSetTournament(results);
            allTournaments.add(tournament);
        }
        return allTournaments;
    }

    private Tournament mapRowSetTournament(SqlRowSet results) {
        Tournament tournament = new Tournament();
        tournament.setTournamentId(results.getLong("id"));
        tournament.setTournamentName(results.getString("tourney_name"));
        tournament.setGame(results.getString("game"));
        tournament.setStartDate(results.getDate("start_date").toLocalDate());
        tournament.setEndDate(results.getDate("end_date").toLocalDate());
        tournament.setLocation(results.getString("location"));
        tournament.setEntryFee(results.getInt("entry_fee"));
        tournament.setPrizeDescription(results.getString("prize_desc"));
        tournament.setTournamentOwner(results.getInt("tournament_owner"));
        tournament.setAcceptingEntries(results.getBoolean("accepting_entries"));
        tournament.setCompleted(results.getBoolean("completed"));
        return tournament;
    }

    @Override
    public Tournament getTournamentById(long id) {
        String sql = "SELECT id, tourney_name, game, start_date, end_date, location, "
                + " entry_fee, prize_desc, tournament_owner, accepting_entries, completed FROM tournaments WHERE id = ? ;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()) {
            return mapRowSetTournament(results);
        }
        return null;
    }

    @Override
    public List<Tournament> getTournamentsByUser(long id) {
        List<Tournament> usersTournaments = new ArrayList<>();

        String sql = "SELECT id, tourney_name, game, start_date, end_date, location, "
                + " entry_fee, prize_desc, tournament_owner, accepting_entries, completed FROM tournaments "
                + "WHERE id IN (SELECT tourney_id FROM tournamentroster WHERE team_id "
                + "IN (SELECT team_id FROM teamroster WHERE user_id = ?)) GROUP BY id;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        while (results.next()) {
            usersTournaments.add(mapRowSetTournament(results));
        }
        return usersTournaments;
    }

    @Override
    public void createTournament(Tournament newTournament, Long userId) {
        String sql = "INSERT INTO tournaments (tourney_name, game, start_date, end_date, location, prize_desc, tournament_owner, entry_fee, accepting_entries) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql, newTournament.getTournamentName(),
                newTournament.getGame(), newTournament.getStartDate(), newTournament.getEndDate(),
                newTournament.getLocation(), newTournament.getPrizeDescription(), userId, newTournament.getEntryFee(),
                newTournament.isAcceptingEntries());
    }

    @Override
    public List<Tournament> getTournamentsByTeam(long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean updateTournament(Tournament tournament) {
        String sql = "UPDATE tournaments SET tourney_name = ?, entry_fee = ?, prize_desc = ?, location = ?, "
                + " start_date = ?, end_date = ? WHERE id = ?;";
        jdbcTemplate.update(sql, tournament.getTournamentName(), tournament.getEntryFee(),
                tournament.getPrizeDescription(), tournament.getLocation(), tournament.getStartDate(),
                tournament.getEndDate(), tournament.getTournamentId());
        return true;
    }

    @Override
    public Map<Integer, String> getTourneyWins(long id) {
        Map<Integer, String> tourneyWins = new LinkedHashMap<>();
        String sql = "SELECT c.team_name, count(b.winner_id) AS wins, sum(b.team_1_score) AS home_points, "
                + "sum(b.team_2_score) AS away_points, (sum(b.team_1_score) + sum(b.team_2_score)) AS total_points "
                + "FROM tournamentroster a JOIN tournamentmatch b ON b.winner_id = a.team_id "
                + "JOIN teams c ON a.team_id = c.id WHERE a.tourney_id = ? GROUP BY c.team_name "
                + "ORDER BY wins desc, total_points desc LIMIT 3";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        Integer place = 1;
        while (results.next()) {
            tourneyWins.put(place++, results.getString("team_name"));
        }

        return tourneyWins;
    }

    @Override
    public void endTournament(long id) {
        String sql = "UPDATE tournaments SET completed = true WHERE id = ?;";
        jdbcTemplate.update(sql, id);
    }

}