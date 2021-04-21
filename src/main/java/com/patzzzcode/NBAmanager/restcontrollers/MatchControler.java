package com.patzzzcode.NBAmanager.restcontrollers;

import java.util.Date;
import java.util.List;

import com.patzzzcode.NBAmanager.bo.Match;
import com.patzzzcode.NBAmanager.bo.Team;
import com.patzzzcode.NBAmanager.repositories.MatchRepository;
import com.patzzzcode.NBAmanager.repositories.TeamRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class MatchControler {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MatchRepository matchRepository;

    @ApiOperation(value = "createMatch")
    @RequestMapping(value = "/api/matches/createMatch", method = RequestMethod.POST)
    public ResponseEntity<Object> createTeam(@RequestParam Long homeTeamID, @RequestParam Long awayTeamID,
            @RequestBody Date matchDate) {
        try {
            Team homeTeam = teamRepository.findById(homeTeamID).orElse(null);
            Team awayTeam = teamRepository.findById(awayTeamID).orElse(null);
            if (homeTeam != null && awayTeam != null) {
                Match match = new Match(null, matchDate, homeTeam, awayTeam);
                matchRepository.save(match);
                return new ResponseEntity<Object>(match, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<Object>("ONE OF THE TEAMS OR BOTH DOES NOT EXIST", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "getAllMatches")
    @RequestMapping(value = "/api/matches/getAllMatches", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllMatches() {
        try {
            List<Match> matches = matchRepository.findAll();
            return new ResponseEntity<Object>(matches, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
        }
    }

}
