package com.patzzzcode.NBAmanager.restcontrollers;

import java.util.List;

import com.patzzzcode.NBAmanager.bo.Player;
import com.patzzzcode.NBAmanager.bo.Team;
import com.patzzzcode.NBAmanager.bo.TeamPlayer;
import com.patzzzcode.NBAmanager.repositories.PlayerRepository;
import com.patzzzcode.NBAmanager.repositories.TeamPlayerRepository;
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
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamPlayerRepository teamPlayerRepository;

    @ApiOperation(value = "createTeam")
    @RequestMapping(value = "/api/teams/createTeam", method = RequestMethod.POST)
    public ResponseEntity<Object> createTeam(@RequestBody Team team) {
        try {
            Team existingTeam = teamRepository.findByName(team.getName()).orElse(null);
            if (existingTeam != null) {
                return new ResponseEntity<Object>("TEAM ALREADY EXIST", HttpStatus.ALREADY_REPORTED);
            } else {
                Team t = new Team(null, team.getName(), team.getCountry(), 0, 0, 0, 0);
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
    public ResponseEntity<Object> getAllTeams() {
        try {
            List<Team> teams = teamRepository.findAll();
            return new ResponseEntity<Object>(teams, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "getTeamPlayers")
    @RequestMapping(value = "/api/teams/getTeamPlayers", method = RequestMethod.GET)
    public ResponseEntity<Object> getTeamPlayers(@RequestParam Long teamID) {
        try {
            Team existingTeam = teamRepository.findById(teamID).orElse(null);
            if (existingTeam != null) {
                List<TeamPlayer> players = teamPlayerRepository.findByTeam(existingTeam);
                return new ResponseEntity<Object>(players, HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>("TEAM NOT FOUND", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "assignPlayerToTeam")
    @RequestMapping(value = "/api/teams/assignPlayerToTeam", method = RequestMethod.POST)
    public ResponseEntity<Object> assignPlayerToTeam(@RequestParam Long playerID, @RequestParam Long teamID) {
        try {
            Team existingTeam = teamRepository.findById(teamID).orElse(null);
            if (existingTeam != null) {
                Player existingPlayer = playerRepository.findById(playerID).orElse(null);
                if (existingPlayer != null) {
                    TeamPlayer teamPlayer = new TeamPlayer(null, existingTeam, existingPlayer);
                    teamPlayerRepository.save(teamPlayer);
                    return new ResponseEntity<Object>(teamPlayer, HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<Object>("PLAYER NOT FOUND", HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<Object>("TEAM NOT FOUND", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "unAssignPlayerToTeam")
    @RequestMapping(value = "/api/teams/unAssignPlayerToTeam", method = RequestMethod.POST)
    public ResponseEntity<Object> unAssignPlayerToTeam(@RequestParam Long playerID, @RequestParam Long teamID) {
        try {
            Team existingTeam = teamRepository.findById(teamID).orElse(null);
            if (existingTeam != null) {
                Player existingPlayer = playerRepository.findById(playerID).orElse(null);
                if (existingPlayer != null) {
                    TeamPlayer player = teamPlayerRepository.findByPlayerAndTeam(existingPlayer, existingTeam)
                            .orElse(null);
                    teamPlayerRepository.delete(player);
                    return new ResponseEntity<Object>("PLAYER WAS UNASSIGNED FROM TEAM", HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<Object>("PLAYER NOT FOUND", HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<Object>("TEAM NOT FOUND", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
        }
    }
}
