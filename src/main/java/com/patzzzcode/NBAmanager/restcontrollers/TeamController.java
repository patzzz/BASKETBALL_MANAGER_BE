package com.patzzzcode.NBAmanager.restcontrollers;

import java.util.List;

import com.patzzzcode.NBAmanager.bo.Player;
import com.patzzzcode.NBAmanager.bo.Team;
import com.patzzzcode.NBAmanager.repositories.TeamRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class TeamController {
    
    @Autowired
    private TeamRepository teamRepository;

    @ApiOperation(value = "createTeam")
    @RequestMapping(value = "/api/teams/createTeam", method = RequestMethod.POST)
    public ResponseEntity<Object> createTeam(@RequestBody Team team) {
        try {
            Team existingTeam = teamRepository.findByName(team.getName()).orElse(null);
            if (existingTeam != null) {
                return new ResponseEntity<Object>("TEAM ALREADY EXIST", HttpStatus.ALREADY_REPORTED);
            } else {
                Team t = new Team(null, team.getName(), team.getCountry(), 0, 0, 0, 0);
                teamRepository.save(t);
                return new ResponseEntity<Object>(t, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "getTeam")
    @RequestMapping(value = "/api/teams/getTeam", method = RequestMethod.GET)
    public ResponseEntity<Object> getTeam(@RequestParam Long teamID) {
        try {
            Team existingTeam = teamRepository.findById(teamID).orElse(null);
            if (existingTeam != null) {
                return new ResponseEntity<Object>(existingTeam, HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>("TEAM NOT FOUND", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "getAllTeams")
    @RequestMapping(value = "/api/teams/getAllTeams", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllPlayers() {
        try {
            List<Team> teams = teamRepository.findAll();
            return new ResponseEntity<Object>(teams, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
        }
    }
}
