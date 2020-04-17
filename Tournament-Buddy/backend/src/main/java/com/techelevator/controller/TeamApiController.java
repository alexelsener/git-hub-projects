package com.techelevator.controller;

import java.util.List;

import javax.validation.Valid;

import com.techelevator.authentication.AuthProvider;
import com.techelevator.authentication.UnauthorizedException;
import com.techelevator.model.JdbcRequestDAO;
import com.techelevator.model.JdbcTeamDao;
import com.techelevator.model.JdbcTournamentTeamDao;
import com.techelevator.model.JdbcUserDao;
import com.techelevator.model.Request;
import com.techelevator.model.Team;
import com.techelevator.model.TournamentTeam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/team")
public class TeamApiController {

    @Autowired
    private JdbcTeamDao teamDao;

    @Autowired
    private JdbcTournamentTeamDao tournamentTeamDao;

    @Autowired
    private JdbcRequestDAO requestDAO;

    @Autowired
    private JdbcUserDao userDao;

    @Autowired
    private AuthProvider auth;

    @Autowired
    public TeamApiController(JdbcTeamDao teamDao) {
        this.teamDao = teamDao;
    }

    @GetMapping
    public List<Team> getAllTeams(@RequestParam(required = false) Long userId) throws UnauthorizedException {
        if (userId != null) {
            if (userId == auth.getCurrentUser().getId()) {
                return teamDao.getTeamsByUser(userId);
            } else {
                throw new UnauthorizedException();
            }
        }
        return teamDao.getAllTeams();
    }

    @PutMapping
    public void updateTeam(@Valid @RequestBody Team team, BindingResult result) throws UnauthorizedException {
        teamDao.updateTeam(team);
    }

    @GetMapping("/{teamId}")
    public Team getTeamById(@PathVariable long teamId) {
        Team team = teamDao.getTeamById(teamId);

        return team;
    }

    @GetMapping("/tournament/{tournamentId}")
    public List<TournamentTeam> getTeamsByTournamentId(@PathVariable long tournamentId) {
        List<TournamentTeam> tourneyTeams = tournamentTeamDao.getTournamentRosterById(tournamentId);

        return tourneyTeams;
    }

    @GetMapping("/round")
    public TournamentTeam getTournamentTeamByTeamId(@RequestParam long teamId) {
        return tournamentTeamDao.getTournamentTeamByTeamId(teamId);
    }

    @DeleteMapping("/tournament/{tournamentId}")
    public void deleteTournamentTeam(@PathVariable long tournamentId, @RequestParam long teamId) {

        tournamentTeamDao.deleteTeam(tournamentId, teamId);
    }

    @PostMapping
    public Team createTeam(@Valid @RequestBody Team team, BindingResult result, @RequestParam Long userId) {

        if ((result.hasErrors() == false) && (userId == auth.getCurrentUser().getId())) {
            return teamDao.createTeam(team, userId);

        }
        return null;
    }

    @PostMapping("/join-request")
    public void joinTeamRequest(@RequestBody Request request) throws UnauthorizedException {
        if (request.getSenderId() == auth.getCurrentUser().getId()) {
            requestDAO.createTeamRequest(request);
        } else {
            throw new UnauthorizedException();
        }

    }

    @GetMapping("/request")
    public List<Request> getAllRequestsByTeam(@RequestParam long teamId) {
        return requestDAO.getRequestsByTeamId(teamId);
    }

    @DeleteMapping("/request")
    public boolean deleteTeamRequest(@RequestBody Request request, BindingResult result) throws UnauthorizedException {
        List<Long> userTeams = userDao.getUsersCaptainedTeams(auth.getCurrentUser().getId());
        boolean isAuthorized = false;
        for (Long t : userTeams) {
            if (t == request.getRecipientId()) {
                requestDAO.deleteTeamRequest(request);
                isAuthorized = true;
            } else {
                throw new UnauthorizedException();
            }
        }
        return isAuthorized;
    }

    @PostMapping("/roster")
    public void addTeamMember(@Valid @RequestParam boolean captainStatus, @RequestBody Request request)
            throws UnauthorizedException {
        List<Long> userTeams = userDao.getUsersCaptainedTeams(auth.getCurrentUser().getId());
        for (Long t : userTeams) {
            if (t == request.getRecipientId()) {
                teamDao.addMember(request, captainStatus);
            } else {
                throw new UnauthorizedException();
            }
        }

    }

    @DeleteMapping("/roster")
    public void deleteTeamMember(@RequestParam long userId, @RequestParam long teamId) throws UnauthorizedException {
        List<Long> userTeams = userDao.getUsersCaptainedTeams(auth.getCurrentUser().getId());
        for (Long t : userTeams) {
            if (t == teamId) {
                teamDao.deleteMember(userId, teamId);
            } else {
                throw new UnauthorizedException();
            }
        }

    }

    @GetMapping("/captain-teams")
    public List<Team> getTeamsByGameandCaptain(@RequestParam String game, @RequestParam Long userId) {
        return teamDao.getTeamsForCaptain(game, userId);
    }

}