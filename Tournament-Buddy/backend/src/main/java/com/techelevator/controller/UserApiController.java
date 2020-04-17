package com.techelevator.controller;

import java.util.List;

import javax.validation.Valid;

import com.techelevator.authentication.AuthProvider;
import com.techelevator.authentication.UnauthorizedException;
import com.techelevator.model.User;
import com.techelevator.model.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * ApiController
 */
@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserApiController {

    @Autowired
    private AuthProvider authProvider;

    @Autowired
    private UserDao userDao;

    

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String authorizedOnly() throws UnauthorizedException {
        /*
         * You can lock down which roles are allowed by checking if the current user has
         * a role.
         * 
         * In this example, if the user does not have the admin or user role we send
         * back an unauthorized error.
         */
        if (!authProvider.userHasRole(new String[] { "admin", "user" })) {
            throw new UnauthorizedException();
        }
        return "Success";
    }

    @GetMapping
    public List<User> getUsersByTeam(@RequestParam Long teamId) {
        return userDao.getUsersByTeam(teamId);
    }

    @GetMapping(path = "/{username}")
    public User getUser(@PathVariable String username) {
        return userDao.getUserByUsername(username);
    }

    @PutMapping
    public boolean updateUser(@Valid @RequestBody User user, BindingResult result) throws UnauthorizedException {
        if(authProvider.getCurrentUser().getId() == user.getId()){
            return userDao.updateUser(user);
        } else {
            throw new UnauthorizedException();
        }
        
    }

    @GetMapping("/captain")
    public List<Long> captainedTeams(@RequestParam long id) throws UnauthorizedException {
        if(id == authProvider.getCurrentUser().getId()){
            return userDao.getUsersCaptainedTeams(id);
        } else {
            throw new UnauthorizedException();
        }
        
    }

    @GetMapping("/tournamentOwner/{tournamentId}")
    public User getTournamentOwnerUsername(@PathVariable long tournamentId) {
        return userDao.getTournamentOwnerUsername(tournamentId);
    }

}