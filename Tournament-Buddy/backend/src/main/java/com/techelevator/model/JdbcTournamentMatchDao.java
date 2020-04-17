package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcTournamentMatchDao implements TournamentMatchDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TournamentTeamDao tournamentTeamDao;

    @Autowired
    public JdbcTournamentMatchDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private TournamentMatch mapRowSetTournamentMatch(SqlRowSet results) {
        TournamentMatch tournamentMatch = new TournamentMatch();
        tournamentMatch.setId(results.getLong("match_id"));
        tournamentMatch.setTournamentId(results.getLong("tourney_id"));
        tournamentMatch.setRound(results.getInt("round_number"));
        tournamentMatch.setHomeTeam(tournamentTeamDao.getTournamentTeamByTeamId(results.getLong("team_1_id")));
        tournamentMatch.setAwayTeam(tournamentTeamDao.getTournamentTeamByTeamId(results.getLong("team_2_id")));
        tournamentMatch.setHomeScore(results.getInt("team_1_score"));
        tournamentMatch.setAwayScore(results.getInt("team_2_score"));
        tournamentMatch.setWinnerId(results.getLong("winner_id"));
        return tournamentMatch;
    }

    @Override
    public void createMatches(List<TournamentMatch> matches) {
        for (TournamentMatch tournamentMatch : matches) {
            String sql = "INSERT INTO tournamentMatch (tourney_id, round_number, team_1_id, team_2_id) "
                    + "VALUES (?, ?, ?, ?)";
            if (tournamentMatch.getAwayTeam() == null) {
                jdbcTemplate.update(sql, tournamentMatch.getTournamentId(), tournamentMatch.getRound(),
                        tournamentMatch.getHomeTeam().getTeamId(), null);
            } else {
                jdbcTemplate.update(sql, tournamentMatch.getTournamentId(), tournamentMatch.getRound(),
                        tournamentMatch.getHomeTeam().getTeamId(), tournamentMatch.getAwayTeam().getTeamId());
            }
        }
    }

    @Override
    public boolean finalizeMatches(List<TournamentMatch> matches) {
        for (TournamentMatch tournamentMatch : matches) {

            String sql = "UPDATE tournamentMatch SET team_1_score = ?, team_2_score = ? winner_id = ? WHERE id = ?;";
            jdbcTemplate.update(sql, tournamentMatch.getHomeScore(), tournamentMatch.getAwayScore(),
                    tournamentMatch.getWinnerId(), tournamentMatch.getId());
        }
        return true;
    }

    @Override
    public List<TournamentMatch> getAllMatchesByTournamentRound(Long tournamentId, Long round) {
        List<TournamentMatch> roundsMatches = new ArrayList<>();
        String sql = "SELECT match_id, tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id "
                + "FROM tournamentmatch WHERE tourney_id = ? AND round_number = ? ORDER BY match_id asc";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, tournamentId, round);
        while (results.next()) {
            TournamentMatch matchup = mapRowSetTournamentMatch(results);
            roundsMatches.add(matchup);
        }
        return roundsMatches;
    }

    @Override
    public List<Integer> getAllRoundsByTournamentId(long tournamentId) {
        List<Integer> allRounds = new ArrayList<>();
        String sql = "SELECT DISTINCT round_number " + "FROM tournamentmatch WHERE tourney_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, tournamentId);
        while (results.next()) {
            int round = results.getInt("round_number");
            allRounds.add(round);
        }
        return allRounds;
    }

    @Override
    public boolean updateMatchScores(TournamentMatch tournamentMatch) {
        String sql = "UPDATE tournamentmatch SET team_1_score = ?, team_2_score = ?, winner_id = ? WHERE match_id = ?;";
        jdbcTemplate.update(sql, tournamentMatch.getHomeScore(), tournamentMatch.getAwayScore(),
                tournamentMatch.getWinnerId(), tournamentMatch.getId());
        return true;
    }

}