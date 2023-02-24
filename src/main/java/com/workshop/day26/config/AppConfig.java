package com.workshop.day26.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import static com.workshop.day26.Constants.*;


@Configuration
public class AppConfig {
    
    // import mongo url
    @Value("${mongo.url}")
    private String connectionString;

    @Bean
    public MongoTemplate mongoTemplate() {
        
        MongoClient client = MongoClients.create(connectionString);
        return new MongoTemplate(client, DB_BGG);
    }

}
