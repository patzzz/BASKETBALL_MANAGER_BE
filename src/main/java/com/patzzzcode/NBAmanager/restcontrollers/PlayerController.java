package com.patzzzcode.NBAmanager.restcontrollers;

import java.util.List;

import com.patzzzcode.NBAmanager.bo.Player;
import com.patzzzcode.NBAmanager.repositories.PlayerRepository;

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
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;

    @ApiOperation(value = "createPlayer")
    @RequestMapping(value = "/api/players/createPlayer", method = RequestMethod.POST)
    public ResponseEntity<Object> createPlayer(@RequestBody Player player) {
        try {
            Player existingPlayer = playerRepository.findByIdentityNumber(player.getIdentityNumber()).orElse(null);
            if (existingPlayer != null) {
                return new ResponseEntity<Object>("PLAYER ALREADY EXIST", HttpStatus.ALREADY_REPORTED);
            } else {
                Player p = new Player(null, player.getFirstName(), player.getLastName(), "", 0,
                        player.getIdentityNumber());
                playerRepository.save(p);
                return new ResponseEntity<Object>(p, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "getPlayer")
    @RequestMapping(value = "/api/players/getPlayer", method = RequestMethod.GET)
    public ResponseEntity<Object> getPlayer(@RequestParam Long playerID) {
        try {
            Player existingPlayer = playerRepository.findById(playerID).orElse(null);
            if (existingPlayer != null) {
                return new ResponseEntity<Object>(existingPlayer, HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>("PLAYER NOT FOUND", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "deletePlayer")
    @RequestMapping(value = "/api/players/deletePlayer", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deletePlayer(@RequestParam Long playerID) {
        try {
            Player existingPlayer = playerRepository.findById(playerID).orElse(null);
            if (existingPlayer != null) {
                playerRepository.delete(existingPlayer);
                return new ResponseEntity<Object>(HttpStatus.GONE);
            } else {
                return new ResponseEntity<Object>("PLAYER NOT FOUND", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "getAllPlayers")
    @RequestMapping(value = "/api/players/getAllPlayers", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllPlayers() {
        try {
            List<Player> players = playerRepository.findAll();
            return new ResponseEntity<Object>(players, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "deleteAllPlayers")
    @RequestMapping(value = "/api/players/deleteAllPlayers", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteAllPlayers() {
        try {
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
