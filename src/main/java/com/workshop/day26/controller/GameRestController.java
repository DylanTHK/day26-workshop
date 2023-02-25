package com.workshop.day26.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.workshop.day26.service.GameService;

@RestController
@RequestMapping
public class GameRestController {
    
    @Value("${mongo.url}")
    private String connectionString;

    @Autowired
    private GameService gameSvc;

    // a. get all games
    @GetMapping(path="/games", produces = MediaType.APPLICATION_JSON_VALUE) // optional parametes limit and offset
    public ResponseEntity<String> getGamesPage(@RequestParam(defaultValue = "25") Integer limit,
        @RequestParam(defaultValue = "0") Integer offset) {
        // call getGames method from gameSvc
        String jsonGames = gameSvc.getGames(offset, limit);
        // Build a response entity with string
        return new ResponseEntity<>(jsonGames, HttpStatus.OK);
    }

    // b. get all games sorted by rank
    @GetMapping(path="/games/rank", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getGamesByRank(@RequestParam Integer offset, @RequestParam Integer limit) {
        // call getGamesRank method from gameSvc
        String jsonGames = gameSvc.getGamesByRank(offset, limit);
        return new ResponseEntity<>(jsonGames, HttpStatus.OK);
    }

    // c. get game by id
    @GetMapping(path="/game/{gid}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getGamesById(@PathVariable Integer gid) {

        System.out.println("Game ID Detected: " + gid);
        // query results from MongoDB
        String response = gameSvc.getGameById(gid);
        if (null != response) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No content found", HttpStatus.NOT_FOUND);
        }
    }


}
