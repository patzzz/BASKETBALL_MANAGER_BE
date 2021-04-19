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
    public ResponseEntity<Object> getAllTeams() {
        try {
            List<Team> teams = teamRepository.findAll();
            return new ResponseEntity<Object>(teams, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "deleteTeam")
    @RequestMapping(value = "/api/teams/deleteTeam", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteTeam(@RequestParam Long teamID) {
        try {
            Team existingTeam = teamRepository.findById(teamID).orElse(null);
            if (existingTeam != null) {
                teamRepository.delete(existingTeam);
                return new ResponseEntity<Object>(HttpStatus.GONE);
            } else {
                return new ResponseEntity<Object>("TEAM NOT FOUND", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "deleteAllTeams")
    @RequestMapping(value = "/api/teams/deleteAllTeams", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteAllTeams() {
        try {
            List<Team> teams = teamRepository.findAll();
            for (Team t : teams) {
                teamRepository.delete(t);
            }
            return new ResponseEntity<Object>(HttpStatus.OK);
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
                    TeamPlayer player = teamPlayerRepository.findByPlayer(existingPlayer).orElse(null);
                    if (player == null) {
                        TeamPlayer teamPlayer = new TeamPlayer(null, existingTeam, existingPlayer);
                        teamPlayerRepository.save(teamPlayer);
                        return new ResponseEntity<Object>(teamPlayer, HttpStatus.CREATED);
                    } else {
                        return new ResponseEntity<Object>("PLAYER IS ALREADY ASSIGNED TO A TEAM",
                                HttpStatus.NOT_ACCEPTABLE);
                    }
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
                    TeamPlayer player = teamPlayerRepository.findByPlayer(existingPlayer).orElse(null);
                    if (player != null) {
                        teamPlayerRepository.delete(player);
                        return new ResponseEntity<Object>("PLAYER WAS UNASSIGNED FROM TEAM", HttpStatus.CREATED);
                    } else {
                        return new ResponseEntity<Object>("PLAYER IS NOT ASSIGNED TO TEAM", HttpStatus.CREATED);
                    }
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

    @ApiOperation(value = "unassignAllPlayers")
    @RequestMapping(value = "/api/teams/unassignAllPlayers", method = RequestMethod.DELETE)
    public ResponseEntity<Object> unassignAllPlayers() {
        try {
            List<TeamPlayer> players = teamPlayerRepository.findAll();
            for (TeamPlayer p : players) {
                teamPlayerRepository.delete(p);
            }
            return new ResponseEntity<Object>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "flushDatabase")
    @RequestMapping(value = "/api/flushDatabase", method = RequestMethod.DELETE)
    public ResponseEntity<Object> flushDatabase() {
        try {
            List<TeamPlayer> playersT = teamPlayerRepository.findAll();
            for (TeamPlayer p : playersT) {
                teamPlayerRepository.delete(p);
            }
            List<Team> teams = teamRepository.findAll();
            for (Team t : teams) {
                teamRepository.delete(t);
            }
            List<Player> players = playerRepository.findAll();
            for (Player p : players) {
                playerRepository.delete(p);
            }
            return new ResponseEntity<Object>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
        }
    }
}
