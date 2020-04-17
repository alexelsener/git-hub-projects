package com.techelevator.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.techelevator.authentication.AuthProvider;
import com.techelevator.authentication.UnauthorizedException;
import com.techelevator.model.JdbcRequestDAO;
import com.techelevator.model.JdbcTournamentDao;
import com.techelevator.model.JdbcTournamentMatchDao;
import com.techelevator.model.JdbcUserDao;
import com.techelevator.model.Request;
import com.techelevator.model.Tournament;
import com.techelevator.model.TournamentMatch;

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
@RequestMapping("/api/tournament")
public class TournamentApiController {

    @Autowired
    private JdbcTournamentDao tournamentDao;

    @Autowired
    private JdbcRequestDAO requestDao;

    @Autowired
    private JdbcTournamentMatchDao tournamentMatchDao;

    @Autowired
    private JdbcUserDao userDao;

    @Autowired
    private AuthProvider auth;

    @Autowired
    public TournamentApiController(JdbcTournamentDao tournamentDao) {
        this.tournamentDao = tournamentDao;
    }

    @GetMapping("/public")
    public List<Tournament> getAllTournamentsPublic() {
        return tournamentDao.getAllTournaments();
    }

    @GetMapping
    public List<Tournament> getAllTournaments(@RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long teamId) throws UnauthorizedException {
        if (userId != null) {
            if (userId == auth.getCurrentUser().getId()) {
                return tournamentDao.getTournamentsByUser(userId);
            } else {
                throw new UnauthorizedException();
            }
        } else if (teamId != null) {
            // Gets all teams that the userToken ID is captain of and checks if it is the
            // same as the one passed to the API
            List<Long> myTeams = userDao.getUsersCaptainedTeams(auth.getCurrentUser().getId());
            boolean isAuthorized = false;
            for (Long team : myTeams) {
                if (team == teamId) {
                    isAuthorized = true;
                }
            }
            if (isAuthorized) {
                return tournamentDao.getTournamentsByTeam(teamId);
            } else {
                throw new UnauthorizedException();
            }
        }
        return tournamentDao.getAllTournaments();
    }

    @GetMapping("/{tournamentId}")
    public Tournament getTournamentById(@PathVariable long tournamentId) {
        Tournament tournament = tournamentDao.getTournamentById(tournamentId);
        return tournament;
    }

    @PostMapping
    public void createTournament(@Valid @RequestBody Tournament tournament, BindingResult result,
            @RequestParam Long userId) {
        if (result.hasErrors() == false) {
            tournamentDao.createTournament(tournament, userId);
        }
        
    }

    @PutMapping
    public boolean updateTournament(@Valid @RequestBody Tournament tournament) throws UnauthorizedException {
        // Making sure that the ID passed to the update belongs to a tournament the user
        // is owner of
        Tournament idTourney = tournamentDao.getTournamentById(tournament.getTournamentId());
        if (idTourney.getTournamentOwner() == auth.getCurrentUser().getId()) {
            return tournamentDao.updateTournament(tournament);
        } else {
            throw new UnauthorizedException();
        }

    }

    @GetMapping("/request")
    public List<Request> getTournamentRequests(@RequestParam long tournamentId) throws UnauthorizedException {
        Tournament idTourney = tournamentDao.getTournamentById(tournamentId);
        if (idTourney.getTournamentOwner() == auth.getCurrentUser().getId()) {
            return requestDao.getRequestsByTournamentId(tournamentId);
        } else {
            throw new UnauthorizedException();
        }

    }

    @DeleteMapping("/request")
    public void deleteTournamentRequest(@Valid @RequestBody Request tourneyRequest, BindingResult result)
            throws UnauthorizedException {
        if (result.hasErrors()) {

        }

        Tournament idTourney = tournamentDao.getTournamentById(tourneyRequest.getRecipientId());
        if (idTourney.getTournamentOwner() == auth.getCurrentUser().getId()) {
            requestDao.deleteTourneyRequest(tourneyRequest);
        } else {
            throw new UnauthorizedException();
        }

    }

    @PostMapping("/request")
    public void acceptTournamentRequest(@Valid @RequestBody Request tourneyRequest, BindingResult result)
            throws UnauthorizedException {
        if (result.hasErrors()) {

        }

        Tournament idTourney = tournamentDao.getTournamentById(tourneyRequest.getRecipientId());
        if (idTourney.getTournamentOwner() == auth.getCurrentUser().getId()) {
            requestDao.acceptTourneyRequest(tourneyRequest);
        } else {
            throw new UnauthorizedException();
        }

    }

    @PostMapping("/join-request")
    public void joinTournamentRequest(@Valid @RequestBody Request request, BindingResult result)
            throws UnauthorizedException {
        if (result.hasErrors()) {

        }
        boolean isAuthorized = false;
        List<Long> myTeams = userDao.getUsersCaptainedTeams(auth.getCurrentUser().getId());
        for (Long team : myTeams) {
            if (team == request.getSenderId()) {
                isAuthorized = true;
            }
        }
        if (isAuthorized == true) {
            requestDao.createTournamentRequest(request);
        } else {
            throw new UnauthorizedException();
        }
    }

    @PostMapping("/matchups")
    public void submitTournamentMatchups(@Valid @RequestBody List<TournamentMatch> matches, BindingResult result)
            throws UnauthorizedException {
        if (result.hasErrors()) {

        }
        long initialTourneyId = matches.get(0).getTournamentId();
        boolean sameTourney = true;
        // makes sure that all the matchups are set to be a part of the same tourney to
        // ensure fake data doesn't slip through
        for (TournamentMatch match : matches) {
            if (match.getTournamentId() != initialTourneyId) {
                sameTourney = false;
            }
        }
        // ensures the token is the owner of the tourney that matchups are being set for
        Tournament idTourney = tournamentDao.getTournamentById(initialTourneyId);
        if (idTourney.getTournamentOwner() == auth.getCurrentUser().getId() && sameTourney) {
            tournamentMatchDao.createMatches(matches);
        } else {
            throw new UnauthorizedException();
        }

    }

    @GetMapping("/matchups")
    public List<TournamentMatch> getTourneyMatchupsByRound(@RequestParam Long tournamentId, @RequestParam Long round) {
        return tournamentMatchDao.getAllMatchesByTournamentRound(tournamentId, round);
    }

    @PostMapping("/scores")
    public boolean updateMatchScores(@RequestBody TournamentMatch tournamentMatch) {
        return tournamentMatchDao.updateMatchScores(tournamentMatch);
    }

    @GetMapping("/tournamentRounds/{tournamentId}")
    public List<Integer> getAllRoundsByTournamentId(@PathVariable long tournamentId) {
        return tournamentMatchDao.getAllRoundsByTournamentId(tournamentId);
    }

    @GetMapping("/wins")
    public Map<Integer, String> getAllTournamentRounds(@RequestParam Long tourneyId) {
        return tournamentDao.getTourneyWins(tourneyId);
    }

    @PutMapping("/end")
    public void endTournament(@RequestParam Long tourneyId) throws UnauthorizedException {
        Tournament idTourney = tournamentDao.getTournamentById(tourneyId);
        if (idTourney.getTournamentOwner() == auth.getCurrentUser().getId()) {
            tournamentDao.endTournament(tourneyId);
        } else {
            throw new UnauthorizedException();
        }
    }

}