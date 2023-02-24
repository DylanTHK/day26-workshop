package com.workshop.day26.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.workshop.day26.repo.GameRepo;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@Service
public class GameService {
    
    @Autowired
    private GameRepo gameRepo;

    public String getGames(Integer offset, Integer limit) {

        // call Game Repo for List of Document (Games)
        List<Document> listGames = gameRepo.getGamesPage(offset, limit);

        // call Game Repo for total count
        Integer count = gameRepo.getGameCount();

        // get current timestamp
        Timestamp ts = Timestamp.from(Instant.now());

        // 1. build json array from list 
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Document d : listGames) {
            // create objectbuilder, add game_id and name to OBJECT builder
            JsonObjectBuilder gameJson = Json.createObjectBuilder()
                .add("game_id", d.getInteger("gid"))
                .add("name", d.getString("name"));
            
            // add objeect builder to array builder
            arrayBuilder.add(gameJson);
            
        }

        JsonObjectBuilder mainResult = Json.createObjectBuilder();
        // add games (json array), offset, limit, total, timestamp
        mainResult.add("games", arrayBuilder);
        mainResult.add("offset", offset);
        mainResult.add("limit", limit);
        mainResult.add("total", count);
        mainResult.add("timestamp", ts.toString());
        
        String resultObject = mainResult.build().toString();

        System.out.println("\nGameSvc >>> mainResult object Json: " + resultObject); //REMOVE
        System.out.println("\nGameSvc >>> mainResult object Json(String): " + resultObject.toString()); //REMOVE

        // return a jsonobject string
        return resultObject;
        // return mainResult.build().toString();
    }

}
