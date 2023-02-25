package com.workshop.day26.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.OptionalDouble;

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

        // return a jsonobject string
        return toJson(listGames, offset, limit);
    }

    // manipulate data to return Json string
    public String getGamesByRank(Integer offset, Integer limit) {
        List<Document> gamesDoc = gameRepo.getGamesSortedByRank(offset, limit);

        // convert List of Docs to array of jsonobject
        return toJson(gamesDoc, offset, limit);
    }

    // format json to following format
    // {
    //     game_id: <ID field>,
    //     name: <Name field>,
    //     year: <Year field>,
    //     ranking: <Rank field>,
    //     average: <Average field>,
    //     users_rated: <Users rated field>,
    //     url: <URL field>,
    //     thumbnail: <Thumbnail field>,
    //     timestamp: <result timestamp>
    // }
    public String getGameById(Integer id) {
        Document doc = gameRepo.getGameById(id);
        Timestamp ts = Timestamp.from(Instant.now());
        
        List<Document> comments = gameRepo.getCommentsById(id);
        List<Integer> ratings = new LinkedList<>();
        
        for (Document d : comments) {
            ratings.add(d.getInteger("rating"));
        }
        
        OptionalDouble averageRating = ratings
            .stream()
            .mapToDouble(r -> r)
            .average();

        Float average = averageRating.isPresent() ? (float) averageRating.getAsDouble() : 0f;
        Integer usersRated = ratings.size();

        JsonObject json = Json.createObjectBuilder()
            .add("game_id", doc.getInteger("gid"))
            .add("name", doc.getString("name"))
            .add("ranking", doc.getInteger("ranking"))
            .add("average", average)
            .add("users_rated", usersRated)
            .add("url", doc.getString("url"))
            .add("thumbnail", doc.getString("image"))
            .add("timestamp", ts.toString())
            .build();

        System.out.println("\ngetGameById: " + json);
        return json.toString();
    }

    // method to convert Doc to Json
    private String toJson(List<Document> docs, Integer offset, Integer limit) {
        // call Game Repo for total count & timestamp
        Integer count = gameRepo.getGameCount();
        Timestamp ts = Timestamp.from(Instant.now());

        // 1. build json array from list 
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Document d : docs) {
            // create objectbuilder, add game_id and name to OBJECT builder
            JsonObjectBuilder gameJson = Json.createObjectBuilder()
                .add("game_id", d.getInteger("gid"))
                .add("name", d.getString("name"));
            // add objeect builder to array builder
            arrayBuilder.add(gameJson);
        }

        // add games (json array), offset, limit, total, timestamp
        JsonObjectBuilder mainResult = Json.createObjectBuilder()
            .add("games", arrayBuilder)
            .add("offset", offset)
            .add("limit", limit)
            .add("total", count)
            .add("timestamp", ts.toString());
        
        String resultObject = mainResult.build().toString();
        System.out.println("\nGameSvc >>> toJson Result: " + resultObject); //REMOVE
        return resultObject;
    }

}
