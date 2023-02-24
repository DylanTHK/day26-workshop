package com.workshop.day26.repo;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import static com.workshop.day26.Constants.*;

@Repository
public class GameRepo {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    // db.game.find({
    //     $and: [
    //         {gid: {$exists: true}},
    //         {name: {$exists: true}}
    //     ] 
    // }).skip(5).limit(30);
    public List<Document> getGamesPage(Integer offset, Integer limit) {
        
        Query query = Query.query(
            new Criteria().andOperator(
                Criteria.where("gid").exists(true),
                Criteria.where("name").exists(true)
            )
        ).limit(limit).skip(offset); 

        List<Document> docs = mongoTemplate.find(query, 
            Document.class, COLLECTION_GAME);

        // System.out.println("\nGameRepo >>> Games extracted: " + docs);
        return docs;
    }

    // db.game.find({}).count();
    public Integer getGameCount() {
        Integer count = mongoTemplate.findAll(Document.class, COLLECTION_GAME).size();
        // System.out.println("\nGameRepo >>> Number of Documents in game: " + count);
        return count;
    }

    // db.game.find({
    //     ranking: {$exists: true}
    // }).sort({ ranking: 1});
    public List<Document> getGamesSortedByRank(Integer offset, Integer limit) {
        Criteria c = Criteria.where("ranking").exists(true);
        Query query = Query.query(c)
            .with(Sort.by(Sort.Direction.ASC, "ranking"))
            .skip(offset).limit(limit);

        List<Document> result = mongoTemplate.find(query, Document.class, COLLECTION_GAME);
        
        System.out.println("Parameters: " + offset + ", " + limit);
        System.out.println("\nGameRepo >>> Result: " + result);

        return result;
    }
}
