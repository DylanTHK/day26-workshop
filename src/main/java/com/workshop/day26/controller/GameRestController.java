package com.workshop.day26.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.workshop.day26.repo.GameRepo;
import com.workshop.day26.service.GameService;

@RestController
@RequestMapping
public class GameRestController {
    
    @Value("${mongo.url}")
    private String connectionString;

    @Autowired
    private GameService gameSvc;

    @GetMapping(path="/games", produces = MediaType.APPLICATION_JSON_VALUE) // optional parametes limit and offset
    public ResponseEntity<String> getGamePage(@RequestParam(defaultValue = "25") Integer limit,
        @RequestParam(defaultValue = "0") Integer offset) {
        // call Service
        String jsonGames = gameSvc.getGames(offset, limit);
        // Build a response entity with string
        return new ResponseEntity<>(jsonGames, HttpStatus.OK);
    }

}
